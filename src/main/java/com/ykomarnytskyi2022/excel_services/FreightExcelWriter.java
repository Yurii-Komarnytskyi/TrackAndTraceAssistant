package com.ykomarnytskyi2022.excel_services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ykomarnytskyi2022.freight.ShipmentImpl;
import com.ykomarnytskyi2022.freight.ShipmentStatus;
import com.ykomarnytskyi2022.freight.TimeFrameRequirements;

class FreightExcelWriter implements ExcelWriter {

	@SuppressWarnings("unused")
	private String sheetName = LocalMachinePaths.DEFAULT_SHEET_NAME;
	private Workbook workbook;
	private Path path;
	private static final LocalDateTime TODAY = LocalDateTime.now();

	public FreightExcelWriter(Path path) {
		this.path = path;
		cleanUpTheSheetsInAnExcelFile();
	}

	public FreightExcelWriter() {

	}

	@Override
	public <T extends ShipmentImpl> void writePickupsAndDeliveriesOnSeparateSheets(
			List<List<ShipmentImpl>> shipmentsFromDifferentCustomers) {
		ProgressOfSheetPopulation progressOfSheetPopulationPU = new ProgressOfSheetPopulation("Shipping");
		ProgressOfSheetPopulation progressOfSheetPopulationDEL = new ProgressOfSheetPopulation("Delivering");

		writeToExcelFromMultFiles(shipmentsFromDifferentCustomers, SortingStrategies::chooseFreightThatShipsToday,
				progressOfSheetPopulationPU);
		writeToExcelFromMultFiles(shipmentsFromDifferentCustomers, SortingStrategies::chooseFreightThatDeliversToday,
				progressOfSheetPopulationDEL);
	}

	private <T extends ShipmentImpl> void writeToExcelFromMultFiles(List<List<ShipmentImpl>> shipmentsFromDifferentCustomers,
			Predicate<T> tester, ProgressOfSheetPopulation progressOfSheetPopulation) {
		shipmentsFromDifferentCustomers.stream().forEach(shipment -> {
			this.writeToExcel(selectFreightComplyingWithPredicate(shipment, tester), progressOfSheetPopulation);
		});
	}

	@Override
	public <T extends ShipmentImpl> void writeToExcel(List<ShipmentImpl> parsedFreight, ProgressOfSheetPopulation sheetInfo) {
		SortingStrategies.sortUrgentFreightFirstAndSameCarrierAdjacent(parsedFreight);

		try (InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ);
				Workbook workbook = WorkbookFactory.create(inputStream);
				OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.WRITE)) {

			this.workbook = workbook;
			populateRowsWithParsedFreight(parsedFreight, sheetInfo);
			workbook.write(outputStream);
			sheetInfo.setLatestWrittenRow(parsedFreight.size() + 2);
			sheetInfo.resetWriteIntoRowToZero();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}

	}

	private void populateRowsWithParsedFreight(List<ShipmentImpl> parsedFreight, ProgressOfSheetPopulation sheetInfo) {
		Sheet sheet = (workbook.getSheet(sheetInfo.getSheetName()) != null)
				? workbook.getSheet(sheetInfo.getSheetName())
				: workbook.createSheet(sheetInfo.getSheetName());

		// Runs through ROWS
		IntStream.range(sheetInfo.getLatestWrittenRow(), sheetInfo.getLatestWrittenRow() + parsedFreight.size())
				.forEach(n -> {
					sheet.createRow(n);
					Row currentRow = sheet.getRow(n);
					List<String> shipmentFields = parsedFreight.get(sheetInfo.getWriteIntoRow())
							.provideFieldsForExcelCells();
					sheetInfo.incrementWriteIntoRowByOne();

					// Runs through CELLS
					IntStream.range(0, shipmentFields.size()).forEach(i -> {
						currentRow.createCell(i);
						Cell currentCell = currentRow.getCell(i);
						currentCell.setCellValue(shipmentFields.get(i));
					});
				});
	}

	private void cleanUpTheSheetsInAnExcelFile() {
		try (InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ);
				Workbook workbook = WorkbookFactory.create(inputStream);
				OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.WRITE)) {
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				workbook.removeSheetAt(i);
			}
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}
	
	void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	@SuppressWarnings("unchecked")
	private static <T extends ShipmentImpl> List<ShipmentImpl> selectFreightComplyingWithPredicate(List<ShipmentImpl> parsedFreight,
			Predicate<T> predicate) {
		return parsedFreight.stream().filter(shipment -> predicate.test((T) shipment)).collect(Collectors.toList());
	}

	static class SortingStrategies {

		static <T extends ShipmentImpl> boolean chooseShipmentsRelevantToday(T shipm) {
			return (shipm.getTimeFrameRequirements().get(TimeFrameRequirements.DELIVER_NOT_LATER_THAN)
					.getDayOfMonth() == TODAY.getDayOfMonth()
					&& shipm.getTimeFrameRequirements().get(TimeFrameRequirements.DELIVER_NOT_LATER_THAN)
							.getMonth() == TODAY.getMonth())
					|| (shipm.getTimeFrameRequirements().get(TimeFrameRequirements.PICKUP_NOT_EARLIER_THAN)
							.getDayOfMonth() == TODAY.getDayOfMonth()
							&& shipm.getTimeFrameRequirements().get(TimeFrameRequirements.PICKUP_NOT_EARLIER_THAN)
									.getMonth() == TODAY.getMonth());
		}

		static <T extends ShipmentImpl> boolean chooseFreightThatShipsToday(T shipm) {
			return ((shipm.getTimeFrameRequirements().get(TimeFrameRequirements.PICKUP_NOT_EARLIER_THAN)
					.getDayOfMonth() == TODAY.getDayOfMonth()
					&& shipm.getTimeFrameRequirements().get(TimeFrameRequirements.PICKUP_NOT_EARLIER_THAN)
							.getMonth() == TODAY.getMonth())
					|| (shipm.getTimeFrameRequirements().get(TimeFrameRequirements.PICKUP_NOT_LATER_THAN)
							.getDayOfMonth() == TODAY.getDayOfMonth()
							&& shipm.getTimeFrameRequirements().get(TimeFrameRequirements.PICKUP_NOT_LATER_THAN)
									.getMonth() == TODAY.getMonth()))
					&& shipm.getStatus().ordinal() < ShipmentStatus.CONFIRMED_PU.ordinal();
		}

		static <T extends ShipmentImpl> boolean chooseFreightThatDeliversToday(T shipm) {
			return (shipm.getTimeFrameRequirements().get(TimeFrameRequirements.DELIVER_NOT_EARLIER_THAN)
					.getDayOfMonth() == TODAY.getDayOfMonth()
					&& shipm.getTimeFrameRequirements().get(TimeFrameRequirements.DELIVER_NOT_EARLIER_THAN)
							.getMonth() == TODAY.getMonth())
					|| (shipm.getTimeFrameRequirements().get(TimeFrameRequirements.DELIVER_NOT_LATER_THAN)
							.getDayOfMonth() == TODAY.getDayOfMonth()
							&& shipm.getTimeFrameRequirements().get(TimeFrameRequirements.DELIVER_NOT_LATER_THAN)
									.getMonth() == TODAY.getMonth());
		}

		static <T extends ShipmentImpl> void sortUrgentFreightFirstAndSameCarrierAdjacent(List<ShipmentImpl> parsedFreight) {
			Collections.sort(parsedFreight, (shipmentA, shipmentB) -> {
				boolean sameCarrier = shipmentA.getScac().equals(shipmentB.getScac());
				if (sameCarrier) {
					return 0;
				}
				return shipmentA.getNextStopNLT() - shipmentB.getNextStopNLT();
			});
		}

	}




}

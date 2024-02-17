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

import com.ykomarnytskyi2022.freight.Shipment;
import com.ykomarnytskyi2022.freight.ShipmentStatus;
import com.ykomarnytskyi2022.freight.TimeFrameRequirements;
import com.ykomarnytskyi2022.freight.Trackable;

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
	public <T extends Shipment> void writePickupsAndDeliveriesOnSeparateSheets(
			List<List<Shipment>> ShipmentsFromDifferentCustomers) {
		ProgressOfSheetPopulation progressOfSheetPopulationPU = new ProgressOfSheetPopulation("Shipping");
		ProgressOfSheetPopulation progressOfSheetPopulationDEL = new ProgressOfSheetPopulation("Delivering");

		writeToExcelFromMultFiles(ShipmentsFromDifferentCustomers, SortingStrategies::chooseFreightThatShipsToday,
				progressOfSheetPopulationPU);
		writeToExcelFromMultFiles(ShipmentsFromDifferentCustomers, SortingStrategies::chooseFreightThatDeliversToday,
				progressOfSheetPopulationDEL);
	}

	private <T extends Shipment> void writeToExcelFromMultFiles(List<List<Shipment>> ShipmentsFromDifferentCustomers,
			Predicate<T> tester, ProgressOfSheetPopulation progressOfSheetPopulation) {
		ShipmentsFromDifferentCustomers.stream().forEach(Shipment -> {
			this.writeToExcel(selectFreightComplyingWithPredicate(Shipment, tester), progressOfSheetPopulation);
		});
	}

	@Override
	public <T extends Shipment> void writeToExcel(List<Shipment> parsedFreight, ProgressOfSheetPopulation sheetInfo) {
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

	private void populateRowsWithParsedFreight(List<Shipment> parsedFreight, ProgressOfSheetPopulation sheetInfo) {
		Sheet sheet = (workbook.getSheet(sheetInfo.getSheetName()) != null)
				? workbook.getSheet(sheetInfo.getSheetName())
				: workbook.createSheet(sheetInfo.getSheetName());

		// Runs through ROWS
		IntStream.range(sheetInfo.getLatestWrittenRow(), sheetInfo.getLatestWrittenRow() + parsedFreight.size())
				.forEach(n -> {
					sheet.createRow(n);
					Row currentRow = sheet.getRow(n);
					List<String> ShipmentFields = parsedFreight.get(sheetInfo.getWriteIntoRow())
							.provideFieldsForExcelCells();
					sheetInfo.incrementWriteIntoRowByOne();

					// Runs through CELLS
					IntStream.range(0, ShipmentFields.size()).forEach(i -> {
						currentRow.createCell(i);
						Cell currentCell = currentRow.getCell(i);
						currentCell.setCellValue(ShipmentFields.get(i));
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
	private static <T extends Shipment> List<Shipment> selectFreightComplyingWithPredicate(List<Shipment> parsedFreight,
			Predicate<T> predicate) {
		return parsedFreight.stream().filter(Shipment -> predicate.test((T) Shipment)).collect(Collectors.toList());
	}

	static class SortingStrategies {

		static <T extends Shipment> boolean chooseShipmentsRelevantToday(T shipm) {
			return (shipm.getTimeFrameRequirements().get(TimeFrameRequirements.DELIVER_NOT_LATER_THAN)
					.getDayOfMonth() == TODAY.getDayOfMonth()
					&& shipm.getTimeFrameRequirements().get(TimeFrameRequirements.DELIVER_NOT_LATER_THAN)
							.getMonth() == TODAY.getMonth())
					|| (shipm.getTimeFrameRequirements().get(TimeFrameRequirements.PICKUP_NOT_EARLIER_THAN)
							.getDayOfMonth() == TODAY.getDayOfMonth()
							&& shipm.getTimeFrameRequirements().get(TimeFrameRequirements.PICKUP_NOT_EARLIER_THAN)
									.getMonth() == TODAY.getMonth());
		}

		static <T extends Shipment> boolean chooseFreightThatShipsToday(T shipm) {
			return ((shipm.getTimeFrameRequirements().get(TimeFrameRequirements.PICKUP_NOT_EARLIER_THAN)
					.getDayOfMonth() == TODAY.getDayOfMonth()
					&& shipm.getTimeFrameRequirements().get(TimeFrameRequirements.PICKUP_NOT_EARLIER_THAN)
							.getMonth() == TODAY.getMonth())
					|| (shipm.getTimeFrameRequirements().get(TimeFrameRequirements.PICKUP_NOT_LATER_THAN)
							.getDayOfMonth() == TODAY.getDayOfMonth()
							&& shipm.getTimeFrameRequirements().get(TimeFrameRequirements.PICKUP_NOT_LATER_THAN)
									.getMonth() == TODAY.getMonth()))
					&& ((Trackable)shipm).getStatus().ordinal() < ShipmentStatus.CONFIRMED_PU.ordinal();
		}

		static <T extends Shipment> boolean chooseFreightThatDeliversToday(T shipm) {
			return (shipm.getTimeFrameRequirements().get(TimeFrameRequirements.DELIVER_NOT_EARLIER_THAN)
					.getDayOfMonth() == TODAY.getDayOfMonth()
					&& shipm.getTimeFrameRequirements().get(TimeFrameRequirements.DELIVER_NOT_EARLIER_THAN)
							.getMonth() == TODAY.getMonth())
					|| (shipm.getTimeFrameRequirements().get(TimeFrameRequirements.DELIVER_NOT_LATER_THAN)
							.getDayOfMonth() == TODAY.getDayOfMonth()
							&& shipm.getTimeFrameRequirements().get(TimeFrameRequirements.DELIVER_NOT_LATER_THAN)
									.getMonth() == TODAY.getMonth());
		}

		static <T extends Shipment> void sortUrgentFreightFirstAndSameCarrierAdjacent(List<Shipment> parsedFreight) {
			Collections.sort(parsedFreight, (ShipmentA, ShipmentB) -> {
				boolean sameCarrier = ((Trackable)ShipmentA).getScacCode().equals(((Trackable) ShipmentB).getScacCode());
				if (sameCarrier) {
					return 0;
				}
				return ((Trackable)ShipmentA).getNextStopNLT() - ((Trackable)ShipmentB).getNextStopNLT();
			});
		}

	}




}

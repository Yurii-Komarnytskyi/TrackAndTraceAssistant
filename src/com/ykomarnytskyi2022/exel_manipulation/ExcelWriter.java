package com.ykomarnytskyi2022.exel_manipulation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

class ExcelWriter {

	@SuppressWarnings("unused")
	private String blankFilePath;
	@SuppressWarnings("unused")
	private String sheetName;
	private Workbook workbook;
	private Path path;
	private static final LocalDateTime TODAY = LocalDateTime.now();

	public ExcelWriter(String blankFilePath, String sheetName) {
		this.blankFilePath = blankFilePath;
		this.sheetName = sheetName;
		this.path = Paths.get(blankFilePath);
		cleanUpTheSheetsInAnExcelFile();
	}

	public ExcelWriter() {

	}

	<T extends Shipment> void writePickupsAndDeliveriesOnSeparateSheets(
			List<List<Shipment>> shipmentsFromDifferentCustomers) {
		ProgressOfSheetPopulation progressOfSheetPopulationPU = new ProgressOfSheetPopulation("Shipping");
		ProgressOfSheetPopulation progressOfSheetPopulationDEL = new ProgressOfSheetPopulation("Delivering");

		writeToExcelFromMultFiles(shipmentsFromDifferentCustomers, SortingStrategies::chooseFreightThatShipsToday,
				progressOfSheetPopulationPU);
		writeToExcelFromMultFiles(shipmentsFromDifferentCustomers, SortingStrategies::chooseFreightThatDeliversToday,
				progressOfSheetPopulationDEL);
	}

	<T extends Shipment> void writeToExcelFromMultFiles(List<List<Shipment>> shipmentsFromDifferentCustomers,
			Predicate<T> tester, ProgressOfSheetPopulation progressOfSheetPopulation) {
		shipmentsFromDifferentCustomers.stream().forEach(shipment -> {
			this.writeToExcel(selectFreightComplyingWithPredicate(shipment, tester), progressOfSheetPopulation);
		});
	}

	<T extends Shipment> void writeToExcel(List<Shipment> parsedFreight, ProgressOfSheetPopulation sheetInfo) {
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

	void populateRowsWithParsedFreight(List<Shipment> parsedFreight, ProgressOfSheetPopulation sheetInfo) {
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

	@SuppressWarnings("unchecked")
	static <T extends Shipment> List<Shipment> selectFreightComplyingWithPredicate(List<Shipment> parsedFreight,
			Predicate<T> predicate) {
		return parsedFreight.stream().filter(shipment -> predicate.test((T) shipment)).collect(Collectors.toList());
	}

	static class SortingStrategies {

		static <T extends Shipment> boolean chooseShipmentsRelevantToday(T shipm) {
			return (shipm.getDNLT().getDayOfMonth() == TODAY.getDayOfMonth()
					&& shipm.getDNLT().getMonth() == TODAY.getMonth())
					|| (shipm.getPNET().getDayOfMonth() == TODAY.getDayOfMonth()
							&& shipm.getPNET().getMonth() == TODAY.getMonth());
		}

		static <T extends Shipment> boolean chooseFreightThatShipsToday(T shipm) {
			return ((shipm.getPNET().getDayOfMonth() == TODAY.getDayOfMonth()
					&& shipm.getPNET().getMonth() == TODAY.getMonth())
					|| (shipm.getPNLT().getDayOfMonth() == TODAY.getDayOfMonth()
							&& shipm.getPNLT().getMonth() == TODAY.getMonth()))
					&& shipm.getStatus().ordinal() < ShipmentStatus.CONFIRMED_PU.ordinal();
		}

		static <T extends Shipment> boolean chooseFreightThatDeliversToday(T shipm) {
			return (shipm.getDNET().getDayOfMonth() == TODAY.getDayOfMonth()
					&& shipm.getDNET().getMonth() == TODAY.getMonth())
					|| (shipm.getDNLT().getDayOfMonth() == TODAY.getDayOfMonth()
							&& shipm.getDNLT().getMonth() == TODAY.getMonth());
		}

		static <T extends Shipment> void sortUrgentFreightFirstAndSameCarrierAdjacent(List<Shipment> parsedFreight) {
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

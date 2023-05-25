package com.ykomarnytskyi2022.exel_manipulation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ykomarnytskyi2022.freight.Shipment;

class ExcelWriter extends PathSharer_BNN {

	private String pathToCleanFile;
	private String sheetName;
	private int latestWrittenRowIndex = 0;
	private int witeShipmentIntoRowNumber = 0;

	public ExcelWriter(String filePath, String sheetName) {
		pathToCleanFile = filePath;
		this.sheetName = sheetName;
	}

	<T extends Shipment> void writeToExcel(List<Shipment> parsedFreight) {

		try {
			FileInputStream fis = new FileInputStream(pathToCleanFile);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheet(sheetName);

			// Runs through ROWS
			IntStream.range(latestWrittenRowIndex, latestWrittenRowIndex + parsedFreight.size()).forEach(n -> {
				Row currentRow = sheet.getRow(n);
				String[] arr = parsedFreight.get(witeShipmentIntoRowNumber).presentFdsToWriter();
				witeShipmentIntoRowNumber++;
				
//				Runs through CELLS 
				IntStream.range(0, arr.length).forEach(i -> {
					Cell currentCell = currentRow.createCell(i);
					currentCell.setCellValue(arr[i]);
				});
			});

			FileOutputStream fos = new FileOutputStream(pathToCleanFile);
			wb.write(fos);
			latestWrittenRowIndex += parsedFreight.size() + 2;
			witeShipmentIntoRowNumber = 0;

		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	<T extends Shipment> void writeToExcelFromMultFiles(List<Set<FieldsTransmitter>> listOfParsedFiles,
			ExcelWriter writer) {

		listOfParsedFiles.stream()
			.map(fieldsTransm -> writer.mapFromFieldsTransToShipment(fieldsTransm))	
			.forEach(shipm -> {
				writer.writeToExcel(shipm);
			});
	}

	List<Shipment> mapFromFieldsTransToShipment(Set<FieldsTransmitter> ftSet) {
		return ftSet.stream()
				.map(ft -> new Shipment(ft))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public static void main(String[] args) {
		ExcelWriter write = new ExcelWriter(ExcelWriter.WRITE_TO, ExcelWriter.SHEET_NAME);
		List<ExcelParser> testList = new ArrayList<>();
		testList.add(new ExcelParser(write.MHS_PATH, write.SEARCH_RESULTS));
		testList.add(new ExcelParser(write.CENTRIA_PATH, write.SEARCH_RESULTS));
		testList.add(new ExcelParser(write.STEEL_PATH, write.SEARCH_RESULTS));

		write.writeToExcelFromMultFiles(ExcelParser.readFromMultipleFiles(testList), write);


	}

}

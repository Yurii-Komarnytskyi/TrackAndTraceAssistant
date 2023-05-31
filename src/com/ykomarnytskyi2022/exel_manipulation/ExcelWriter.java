package com.ykomarnytskyi2022.exel_manipulation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
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


	static <T extends Shipment> List<T> pickRelevantByPredicate(List<T> parsedFreight, Predicate<T> tester) {
		return parsedFreight.stream()
				.filter(sh -> tester.test(sh))
				.collect(Collectors.toList());
	}
	
	static <T extends Shipment> boolean pickRelevantToday(T shipm) {
		LocalDateTime today = LocalDateTime.now();
		return (shipm.getDNLT().getDayOfMonth() == today.getDayOfMonth()
				&& shipm.getDNLT().getMonth() == today.getMonth())
				|| (shipm.getPNET().getDayOfMonth() == today.getDayOfMonth()
						&& shipm.getPNET().getMonth() == today.getMonth());
	}

	<T extends Shipment> void writeToExcel(List<Shipment> parsedFreight) {
				
		Collections.sort(parsedFreight, (sh1, sh2) -> {return sh1.getNextStopNLT() - sh2.getNextStopNLT();}) ;
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

	<T extends Shipment> void writeToExcelFromMultFiles(List<Set<FieldsTransmitter>> listOfParsedFiles) {

		listOfParsedFiles.stream()
			.map(fieldsTransm -> mapFromFieldsTransToShipment(fieldsTransm))	
			.forEach(shipm -> {
				this.writeToExcel(pickRelevantByPredicate(shipm, ExcelWriter::pickRelevantToday));
			});
	}

	static List<Shipment> mapFromFieldsTransToShipment(Set<FieldsTransmitter> ftSet) {
		return ftSet.stream()
				.map(ft -> new Shipment(ft))
				.collect(Collectors.toCollection(ArrayList::new));
	}

}

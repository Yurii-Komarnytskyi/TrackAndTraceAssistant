package com.ykomarnytskyi2022.exel_manipulation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

// TO DO 
// add error handeling, this is a MUST
// add dynamic paths to the file itself and the sheet

public class ExcelParser extends PathSharer_BNN {
	
	private FileInputStream fis;
	private Workbook wb;
	private Sheet sheet;
	private Row headerRow;
	
	
	private int countFilledColumns() {
		return sheet.getRow(0).getLastCellNum();
	}
	private int countFilledRows() {
		return sheet.getLastRowNum();
	}
	
	public ExcelParser(String filePath, String sheetName) {
		try {
			fis = new FileInputStream(filePath);
			wb = WorkbookFactory.create(fis);
			sheet = wb.getSheet(sheetName);
			headerRow = sheet.getRow(0);
			
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	FieldsTransmitter readRowHorizontally(int rowNum, int columnsTotal) {
		FieldsTransmitter ft = new FieldsTransmitter();
		try {
			Row r = sheet.getRow(rowNum);
			IntStream.range(0, columnsTotal).
				forEach((n) -> {
					Cell c = r.getCell(n);
					ft.absorb(headerRow.getCell(n).getStringCellValue(), c.getStringCellValue());
				});
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return  ft;
	}
	Set<FieldsTransmitter> parseFreightDataInSingleFile() {
		Set<FieldsTransmitter> parsedFreightData = new LinkedHashSet<>();
		IntStream.range(1, this.countFilledRows())
			.forEach((n) -> {
				parsedFreightData.add(readRowHorizontally(n, this.countFilledColumns()));
			});

		return parsedFreightData;
	}

	static List<Set<FieldsTransmitter>> readFromMultipleFiles (List<ExcelParser> pList) {
		List<Set<FieldsTransmitter>> tList = new ArrayList<>();
		pList.stream()
			.forEach(parsing -> {
				tList.add(parsing.parseFreightDataInSingleFile());
			});
		return tList;
	}
}

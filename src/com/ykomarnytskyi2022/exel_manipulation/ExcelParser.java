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
// add dynamic pathes to the file itself and the sheet

public class ExcelParser {
	private static final String HARD_PATH = "C:\\Users\\Home\\Documents\\Search Results05152023_54926.xls";
	private static final String THE_SHEET = "Search Results";
	
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
	public ExcelParser(String pathToAnExelFile, String sheetName) {
		try {
			fis = new FileInputStream(pathToAnExelFile);
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
	public FieldsTransmitter readRowHorizontally(String sheetName, int rowNum, int columnsTotal) {
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
	public Set<FieldsTransmitter> parseAllLoads__Bnn() {
		Set<FieldsTransmitter> parsedFreightData = new LinkedHashSet<>();
		IntStream.range(1, this.countFilledRows())
			.forEach((n) -> {
				parsedFreightData.add(readRowHorizontally(HARD_PATH, n, this.countFilledColumns()));
			});

		return parsedFreightData;
	}

	public static void main(String[] args) {
		ExcelParser parsedExelFile = new ExcelParser(HARD_PATH, THE_SHEET);
//		Set<FieldsTransmitter> t = parsedExelFile.parseAllLoads__Bnn();
		List<FieldsTransmitter> t2 = new ArrayList<>(parsedExelFile.parseAllLoads__Bnn()); 
		System.out.println(t2.get(2));
	}

}

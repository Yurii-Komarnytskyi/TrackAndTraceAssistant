package com.ykomarnytskyi2022.exel_manipulation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelParser extends PathSharer_BNN {
	
	private FileInputStream fileInputStream;
	private Workbook workbook;
	private Sheet sheet;
	private Row headerRow;
	private String excelFilePath;
	
	
	public ExcelParser(String filePath, String sheetName) {
		this.excelFilePath = filePath;
		try {
			fileInputStream = new FileInputStream(filePath);
			workbook = WorkbookFactory.create(fileInputStream);
			sheet = workbook.getSheet(sheetName);
			headerRow = sheet.getRow(0);
			
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ExcelParser() {
		
	}
		
	FieldsTransmitter readRowHorizontally(int rowIndex, int columnsTotal) {
		FieldsTransmitter fieldsTransmitter = new FieldsTransmitter();
		try {
			Row row = sheet.getRow(rowIndex);
			IntStream.range(0, columnsTotal).
				forEach((n) -> {
					Cell cell = row.getCell(n);
					fieldsTransmitter.absorb(headerRow.getCell(n).getStringCellValue(), cell.getStringCellValue());
				});
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return  fieldsTransmitter;
	}
	
	Set<FieldsTransmitter> parseFreightDataInSingleFile() {
		Set<FieldsTransmitter> parsedFreightData = new LinkedHashSet<>();
		IntStream.range(1, this.countFilledRows())
			.forEach((n) -> {
				parsedFreightData.add(readRowHorizontally(n, this.countFilledColumns()));
			});

		return parsedFreightData;
	}
	
	private int countFilledColumns() {
		return sheet.getRow(0).getLastCellNum();
	}
	
	private int countFilledRows() {
		return sheet.getLastRowNum();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.excelFilePath.equals(obj.toString());
	}
	@Override
	public int hashCode() {
		return Objects.hashCode(excelFilePath);
	}
	@Override
	public String toString() {
		return this.excelFilePath;
	}
}

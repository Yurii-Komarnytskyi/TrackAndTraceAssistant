package com.ykomarnytskyi2022.exel_manipulation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

class ExcelWriter {

	private static final String DA_PATH = "C:\\Users\\Home\\Documents\\FreightDemo.xls";
	private static final String SHEET_NAME = "Sheet1";
	
	public void writeToExcel(String pathToAnExelFile, String sheetName) {
		try {
			FileInputStream fis = new FileInputStream(pathToAnExelFile);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheet(sheetName);
			Row headerRow = sheet.getRow(0);
			Cell mySpecialCell = headerRow.createCell(0);
			mySpecialCell.setCellValue("Some BS");
			
			FileOutputStream fos = new FileOutputStream(DA_PATH);
			wb.write(fos);
			
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ExcelWriter ew = new ExcelWriter();
		ew.writeToExcel(DA_PATH, SHEET_NAME);
		
	}
	
	

}

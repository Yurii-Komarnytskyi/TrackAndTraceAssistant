package com.ykomarnytskyi2022.exel_manipulation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ykomarnytskyi2022.freight.Shipment;

class ExcelWriter extends PathSharer_BNN  {

	
	public <T extends Shipment> void writeToExcel(String pathToCleanFile, String sheetName, List<Shipment> parsedFreight) {
		try {
			FileInputStream fis = new FileInputStream(pathToCleanFile);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheet(sheetName);
//			Row currentRow;
//			Cell currentCell;
			IntStream.range(0, parsedFreight.size())
				.forEach(n -> {
					Row currentRow = sheet.getRow(n);
					Cell currentCell;
					System.out.println(currentRow);
					
					String[] arr = parsedFreight.get(n).presentFdsToWriter();
					for(int i = 0; i < arr.length; i++) {
					currentCell = currentRow.createCell(i);
					currentCell.setCellValue(arr[i]);				
				}
				});
			
			
			
			FileOutputStream fos = new FileOutputStream(pathToCleanFile);
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
		ExcelParser parsedExelFile = new ExcelParser(ew.CENTRIA_PATH, ew.SEARCH_RESULTS);
		List<FieldsTransmitter> t2 = new ArrayList<>(parsedExelFile.parseAllLoads__Bnn(parsedExelFile.CENTRIA_PATH)); 
		
		List<Shipment> shList = t2.stream()
				.map(ft -> new Shipment(ft))
//				.peek(sh -> System.out.println(sh.getSatusUpd(sh)))
				.collect(Collectors.toCollection(ArrayList::new));
		
		ew.writeToExcel(ew.WRITE_TO, ew.SHEET_NAME, shList);
	}

	
	

}

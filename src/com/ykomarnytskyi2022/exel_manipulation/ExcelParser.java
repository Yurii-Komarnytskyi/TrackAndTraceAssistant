package com.ykomarnytskyi2022.exel_manipulation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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

// TO DO 
// add error handeling, this is a MUST
// add dynamic pathes to the file itself and the sheet

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
	public Set<FieldsTransmitter> parseAllLoads__Bnn(String path) {
		Set<FieldsTransmitter> parsedFreightData = new LinkedHashSet<>();
		IntStream.range(1, this.countFilledRows())
			.forEach((n) -> {
				parsedFreightData.add(readRowHorizontally(path, n, this.countFilledColumns()));
			});

		return parsedFreightData;
	}

//	public static void main(String[] args) {
//		ExcelWriter ew = new ExcelWriter();
//		ExcelParser parsedExelFile = new ExcelParser(ew.STEEL_PATH, ew.SEARCH_RESULTS);
//		List<FieldsTransmitter> t2 = new ArrayList<>(parsedExelFile.parseAllLoads__Bnn(parsedExelFile.MHS_PATH)); 
//		
//		List<Shipment> shList = t2.stream()
//				.map(ft -> new Shipment(ft))
////				.peek(sh -> System.out.println(sh.getSatusUpd(sh)))
//				.collect(Collectors.toCollection(ArrayList::new));
//		
//		ew.writeToExcel(ew.WRITE_TO, ew.SHEET_NAME, shList);
//	}

}

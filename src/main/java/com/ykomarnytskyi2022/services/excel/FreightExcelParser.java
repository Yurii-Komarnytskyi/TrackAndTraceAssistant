package com.ykomarnytskyi2022.services.excel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ykomarnytskyi2022.freight.Shipment;
import com.ykomarnytskyi2022.freight.ShipmentFactory;

public class FreightExcelParser implements ExcelParser {

	@SuppressWarnings("unused")
	private InputStream inputStream;
	private Workbook workbook;
	private Path path;
	private String sheetName;
	private Sheet sheet;
	private Row headerRow;
	private ShipmentFactory shipmentFactory;
	
	public FreightExcelParser(Path path, String sheetName, ShipmentFactory shipmentFactory) {
		this.path = path;
		this.sheetName = sheetName;
		this.shipmentFactory = shipmentFactory;
	}

	public FreightExcelParser() {

	}

	@Override
	public List<Shipment> parseFreightDataFromFile() {
		initInputStreamAndWorkbook();
		sheet = workbook.getSheet(sheetName);
		headerRow = sheet.getRow(0);

		List<Shipment> parsedFreightData = new ArrayList<>();
		IntStream.rangeClosed(1, this.countFilledRows()).forEach((n) -> {
			Shipment parsedRow = parseRowHorizontally(n, this.countFilledColumns());
			parsedFreightData.add(parsedRow);
		});
		
		return parsedFreightData;
	}

	private void initInputStreamAndWorkbook() {

		try (InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ);
				Workbook workbook = WorkbookFactory.create(inputStream)) {
			this.inputStream = inputStream;
			this.workbook = workbook;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}


	private Shipment parseRowHorizontally(int rowIndex, int columnsTotal) {
		Map<String, String> fields = new HashMap<>();
		try {
			Row row = sheet.getRow(rowIndex);
			IntStream.range(0, columnsTotal).forEach((n) -> {
				Cell cell = row.getCell(n);
				fields.put(headerRow.getCell(n).getStringCellValue(), cell.getStringCellValue());
			});
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return shipmentFactory.create(fields);
	}
	
	private int countFilledColumns() {
		return sheet.getRow(0).getLastCellNum();
	}

	private int countFilledRows() {
		return sheet.getLastRowNum();
	}

	@Override
	public boolean equals(Object obj) {
		return this.path.equals(obj);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(path);
	}

	@Override
	public String toString() {
		return this.path.toString();
	}
}

package com.ykomarnytskyi2022.exel_manipulation;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ykomarnytskyi2022.freight.Shipment;

public class ExcelParser {

	@SuppressWarnings("unused")
	private InputStream inputStream;
	private Workbook workbook;
	private Path path;
	private String sheetName;
	private Sheet sheet;
	private Row headerRow;

	public ExcelParser(String filePath, String sheetName) {
		path = Paths.get(filePath);
		this.sheetName = sheetName;
	}

	public ExcelParser() {

	}

	List<Shipment> parseFreightDataFromFile() {
		initInputStreamAndWorkbook();
		sheet = workbook.getSheet(sheetName);
		headerRow = sheet.getRow(0);

		List<Shipment> parsedFreightData = new ArrayList<>();
		IntStream.range(1, this.countFilledRows()).forEach((n) -> {
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

	Shipment parseRowHorizontally(int rowIndex, int columnsTotal) {
		FieldsTransmitter fieldsTransmitter = new FieldsTransmitter();
		try {
			Row row = sheet.getRow(rowIndex);
			IntStream.range(0, columnsTotal).forEach((n) -> {
				Cell cell = row.getCell(n);
				fieldsTransmitter.absorb(headerRow.getCell(n).getStringCellValue(), cell.getStringCellValue());
			});
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return new Shipment(fieldsTransmitter);
	}

	private int countFilledColumns() {
		return sheet.getRow(0).getLastCellNum();
	}

	private int countFilledRows() {
		return sheet.getLastRowNum() + 1;
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

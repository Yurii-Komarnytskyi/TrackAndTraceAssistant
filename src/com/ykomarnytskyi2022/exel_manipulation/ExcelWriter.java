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
import com.ykomarnytskyi2022.freight.ShipmentStatus;

class ExcelWriter extends PathSharer_BNN { 

	private String pathToCleanFile;
	private String sheetName;
	private int latestWrittenRowIndex = 0;
	private int witeShipmentIntoRowNumber = 0;
	private static final LocalDateTime TODAY = LocalDateTime.now();  

	public ExcelWriter(String filePath, String sheetName) {
		pathToCleanFile = filePath;
		this.sheetName = sheetName;
	}


	@SuppressWarnings("unchecked")
	static <T extends Shipment> List<Shipment> pickRelevantByPredicate(List<Shipment> parsedFreight, Predicate<T> tester) {
		return parsedFreight.stream()
				.filter(sh -> tester.test((T) sh))
				.collect(Collectors.toList());
	}
	
	static <T extends Shipment> boolean pickRelevantToday(T shipm) {
		return (shipm.getDNLT().getDayOfMonth() == TODAY.getDayOfMonth()
				&& shipm.getDNLT().getMonth() == TODAY.getMonth())
				|| (shipm.getPNET().getDayOfMonth() == TODAY.getDayOfMonth()
						&& shipm.getPNET().getMonth() == TODAY.getMonth());
	}

	static <T extends Shipment> boolean pickThoseShippingToday(T shipm) {
		return ((shipm.getPNET().getDayOfMonth() == TODAY.getDayOfMonth()
				&& shipm.getPNET().getMonth() == TODAY.getMonth())
				|| (shipm.getPNLT().getDayOfMonth() == TODAY.getDayOfMonth()
						&& shipm.getPNLT().getMonth() == TODAY.getMonth()))
				&& shipm.getStatus().ordinal() < ShipmentStatus.CONFIRMED_PU.ordinal();
	}

	static <T extends Shipment> boolean pickThoseDeliveringToday(T shipm) {
		return (shipm.getDNET().getDayOfMonth() == TODAY.getDayOfMonth()
				&& shipm.getDNET().getMonth() == TODAY.getMonth())
				|| (shipm.getDNLT().getDayOfMonth() == TODAY.getDayOfMonth()
						&& shipm.getDNLT().getMonth() == TODAY.getMonth());
	}

	<T extends Shipment> void writeToExcel(List<Shipment> parsedFreight) {
				
		Collections.sort(parsedFreight, (sh1, sh2) -> {
			if (sh1.getScac().equals(sh2.getScac()))
				return 0;
			return sh1.getNextStopNLT() - sh2.getNextStopNLT();
		});
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

	<T extends Shipment> void writeToExcelFromMultFiles(
			List<Set<FieldsTransmitter>> listOfParsedFiles,
			Predicate<T> tester) {

		listOfParsedFiles.stream()
			.map(fieldsTransm -> mapFromFieldsTransToShipment(fieldsTransm))	
			.forEach(shipm -> {
				this.writeToExcel(pickRelevantByPredicate(shipm, tester));
			});
	}

	static List<Shipment> mapFromFieldsTransToShipment(Set<FieldsTransmitter> ftSet) {
		return ftSet.stream()
				.map(ft -> new Shipment(ft))
				.collect(Collectors.toCollection(ArrayList::new));
	}

}

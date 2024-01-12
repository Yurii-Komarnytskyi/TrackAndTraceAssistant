package com.ykomarnytskyi2022.excel_manipulation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.ykomarnytskyi2022.freight.Shipment;

public class ParsingAndWritingDelegator {

	private ExcelWriter fileBeingWritten;
	private List<Path> pathsToSourceExcelFiles = new ArrayList<>();;
	private List<List<Shipment>> shipmentsFromDifferentCustomers = new ArrayList<>();

	public ParsingAndWritingDelegator(ExcelWriter fileBeingWritten, Path... pathsToSourceExcelFiles) {
		this.fileBeingWritten = fileBeingWritten;
		for (Path path : pathsToSourceExcelFiles) {
			this.pathsToSourceExcelFiles.add(path);
		}
	}

	public ParsingAndWritingDelegator() {

	}

	public <T extends Shipment> void readAndWrite() {
		if (shipmentsFromDifferentCustomers.size() == 0) {
			pathsToSourceExcelFiles.stream().forEach(path -> {
				List<Shipment> shipments = (new ExcelParser(path, LocalMachinePaths.SEARCH_RESULTS))
						.parseFreightDataFromFile();
				shipmentsFromDifferentCustomers.add(shipments);
			});
		}
		fileBeingWritten.writePickupsAndDeliveriesOnSeparateSheets(shipmentsFromDifferentCustomers);
	}

	boolean offerPathToSourceExcelFile(Path path) {
		return pathsToSourceExcelFiles.add(path);
	}

	public static void main(String[] args) {

		ExcelWriter excelWriter = new ExcelWriter(LocalMachinePaths.blankExelFile, LocalMachinePaths.SHEET_NAME);
		ParsingAndWritingDelegator delegator = new ParsingAndWritingDelegator(excelWriter, LocalMachinePaths.customerC, LocalMachinePaths.customerM, LocalMachinePaths.customerS );

		delegator.readAndWrite();
		System.out.println("THE END");
	}

}

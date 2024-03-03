package com.ykomarnytskyi2022.excel_services;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.ykomarnytskyi2022.freight.Shipment;

public class ParsingAndWritingDelegator {

	private ExcelWriter excelWriter;
	private List<Path> pathsToSourceExcelFiles = new ArrayList<>();
	private List<List<Shipment>> shipmentsFromDifferentCustomers = new ArrayList<>();
	private ExcelParserFactory excelParserFactory;

	public ParsingAndWritingDelegator(ExcelWriter fileBeingWritten, ExcelParserFactory excelParserFactory) {
		this.excelWriter = fileBeingWritten;
		this.excelParserFactory = excelParserFactory;
	}
	
	public ParsingAndWritingDelegator() {

	}

	public void readAndWrite() {
		if (gotAvailablePathsToSourceExcelFiles()) {
			initShipmentsFromDifferentCustomers();
			excelWriter.writePickupsAndDeliveriesOnSeparateSheets(shipmentsFromDifferentCustomers);
		} else {
			System.err.println("No paths to source excel files were provided");
		}
	}

	
	private void initShipmentsFromDifferentCustomers() {
		if (gotAvailablePathsToSourceExcelFiles()) {
			pathsToSourceExcelFiles.stream().forEach(path -> {
				List<Shipment> shipmentImpls = excelParserFactory
						.create(path, LocalMachinePaths.SEARCH_RESULTS)
						.parseFreightDataFromFile();
				shipmentsFromDifferentCustomers.add(shipmentImpls);
			});
		}
	}
	
	private boolean gotAvailablePathsToSourceExcelFiles() {
		return this.pathsToSourceExcelFiles.size() > 0;
	}

	public boolean offerPathToSourceExcelFile(Path path) {
		return pathsToSourceExcelFiles.add(path);
	}
	
}

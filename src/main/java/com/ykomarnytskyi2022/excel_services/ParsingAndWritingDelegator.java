package com.ykomarnytskyi2022.excel_services;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.ykomarnytskyi2022.freight.Shipment;

public class ParsingAndWritingDelegator {

	private ExcelWriter fileBeingWritten;
	private List<Path> pathsToSourceExcelFiles = new ArrayList<>();
	private List<List<Shipment>> shipmentsFromDifferentCustomers = new ArrayList<>();

	public ParsingAndWritingDelegator(ExcelWriter fileBeingWritten, Path... pathsToSourceExcelFiles) {
		this(fileBeingWritten);
		for (Path path : pathsToSourceExcelFiles) {
			this.pathsToSourceExcelFiles.add(path);
		}
	}

	public ParsingAndWritingDelegator(ExcelWriter fileBeingWritten) {
		this.fileBeingWritten = fileBeingWritten;
	}

	public ParsingAndWritingDelegator() {

	}

	public <T extends Shipment> void readAndWrite() {
		if (gotAvailablePathsToSourceExcelFiles()) {
			initShipmentsFromDifferentCustomers();
			fileBeingWritten.writePickupsAndDeliveriesOnSeparateSheets(shipmentsFromDifferentCustomers);
		} else {
			System.err.println("No paths to source excel files were provided");
		}
	}

	public boolean offerPathToSourceExcelFile(Path path) {
		return pathsToSourceExcelFiles.add(path);
	}
	
	private void initShipmentsFromDifferentCustomers() {
		if (shipmentsFromDifferentCustomers.size() == 0 && gotAvailablePathsToSourceExcelFiles()) {
			pathsToSourceExcelFiles.stream().forEach(path -> {
				List<Shipment> shipments = (new FreightExcelParser(path, LocalMachinePaths.SEARCH_RESULTS))
						.parseFreightDataFromFile();
				shipmentsFromDifferentCustomers.add(shipments);
			});
		}
	}
	
	private boolean gotAvailablePathsToSourceExcelFiles() {
		return this.pathsToSourceExcelFiles.size() > 0;
	}

}

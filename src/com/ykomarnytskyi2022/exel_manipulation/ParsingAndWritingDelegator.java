package com.ykomarnytskyi2022.exel_manipulation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.ykomarnytskyi2022.freight.Shipment;

class ParsingAndWritingDelegator { 
	
	private List<List<Shipment>> shipmentsFromDifferentCustomers = new ArrayList<>(); 
	private ExcelWriter fileBeingWritten;
	
	public ParsingAndWritingDelegator(ExcelWriter fileBeingWritten, List<List<Shipment>> shipments) {
		this.fileBeingWritten = fileBeingWritten;
		this.shipmentsFromDifferentCustomers = shipments;
	} 
	
	public ParsingAndWritingDelegator() {

	}
	
	public <T extends Shipment> void readAndWrite(Predicate<T> predicate) {
		if (shipmentsFromDifferentCustomers.size() > 0) {
			fileBeingWritten.writePickupsAndDeliveriesOnSeparateSheets(shipmentsFromDifferentCustomers);
		}
	}
	
	boolean offerShipments(List<Shipment> shipments) {
		return shipmentsFromDifferentCustomers.add(shipments);
	}
	
	public static void main(String[] args) {
		ExcelWriter excelWriter = new ExcelWriter(LocalMachinePaths.blankExelFilePath, LocalMachinePaths.SHEET_NAME);
		List<List<Shipment>> freightFromCustomers = new ArrayList<>();
		
		List<Shipment> c = (new ExcelParser(LocalMachinePaths.customerC, LocalMachinePaths.SEARCH_RESULTS)).parseFreightDataFromFile();
		List<Shipment> m = (new ExcelParser(LocalMachinePaths.customerM, LocalMachinePaths.SEARCH_RESULTS)).parseFreightDataFromFile();
		List<Shipment> s = (new ExcelParser(LocalMachinePaths.customerS, LocalMachinePaths.SEARCH_RESULTS)).parseFreightDataFromFile();
		
		freightFromCustomers.add(c);
		freightFromCustomers.add(m);
		freightFromCustomers.add(s);
		
		ParsingAndWritingDelegator delegator = new ParsingAndWritingDelegator(excelWriter, freightFromCustomers);
			
		delegator.readAndWrite(ExcelWriter.SortingStrategies::chooseFreightThatDeliversToday);
		System.out.println("THE END");

	}
	
}

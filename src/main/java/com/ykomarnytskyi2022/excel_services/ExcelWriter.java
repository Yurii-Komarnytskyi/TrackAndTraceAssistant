package com.ykomarnytskyi2022.excel_services;

import java.util.List;

import com.ykomarnytskyi2022.freight.Shipment;

public interface ExcelWriter {
	public <T extends Shipment> void writePickupsAndDeliveriesOnSeparateSheets(
			List<List<Shipment>> shipmentsFromDifferentCustomers);

	public <T extends Shipment> void writeToExcel(List<Shipment> parsedFreight, ProgressOfSheetPopulation sheetInfo);
}

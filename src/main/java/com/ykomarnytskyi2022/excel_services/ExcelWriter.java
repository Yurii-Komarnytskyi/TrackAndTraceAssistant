package com.ykomarnytskyi2022.excel_services;

import java.util.List;

import com.ykomarnytskyi2022.freight.ShipmentImpl;

public interface ExcelWriter {
	public <T extends ShipmentImpl> void writePickupsAndDeliveriesOnSeparateSheets(
			List<List<ShipmentImpl>> shipmentsFromDifferentCustomers);

	public <T extends ShipmentImpl> void writeToExcel(List<ShipmentImpl> parsedFreight, ProgressOfSheetPopulation sheetInfo);
}

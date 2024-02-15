package com.ykomarnytskyi2022.excel_services;

import java.util.List;

import com.ykomarnytskyi2022.freight.ShipmentImpl;

public interface ExcelParser {
	
	public List<ShipmentImpl> parseFreightDataFromFile();
}

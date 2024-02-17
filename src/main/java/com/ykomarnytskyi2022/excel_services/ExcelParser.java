package com.ykomarnytskyi2022.excel_services;

import java.util.List;

import com.ykomarnytskyi2022.freight.Shipment;

public interface ExcelParser {
	
	public List<Shipment> parseFreightDataFromFile();
}

package com.ykomarnytskyi2022.excel_services;

import java.nio.file.Path;

import com.ykomarnytskyi2022.freight.ShipmentFactory;

public class ExcelParserFactoryImpl implements ExcelParserFactory {

	private ShipmentFactory shipmentFactory;

	public ExcelParserFactoryImpl(ShipmentFactory shipmentFactory) {
		this.shipmentFactory = shipmentFactory;
	}

	@Override
	public ExcelParser create(Path path, String sheetName) {
		return new FreightExcelParser(path, sheetName, shipmentFactory);
	}

}

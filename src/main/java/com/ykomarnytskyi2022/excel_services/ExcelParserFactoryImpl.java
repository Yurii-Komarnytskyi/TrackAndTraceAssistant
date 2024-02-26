package com.ykomarnytskyi2022.excel_services;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.ykomarnytskyi2022.freight.ShipmentFactory;

@Service
@Profile({"nocTeam" , "default"})
public class ExcelParserFactoryImpl implements ExcelParserFactory {

	private ShipmentFactory shipmentFactory;

	@Autowired
	public ExcelParserFactoryImpl(ShipmentFactory shipmentFactory) {
		this.shipmentFactory = shipmentFactory;
	}

	@Override
	public ExcelParser create(Path path, String sheetName) {
		return new FreightExcelParser(path, sheetName, shipmentFactory);
	}

}

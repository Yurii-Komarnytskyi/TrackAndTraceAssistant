package com.ykomarnytskyi2022.exel_parser;

import java.util.HashMap;
import java.util.Map;

import com.ykomarnytskyi2022.freight.BasicShipmentFields;

public class FieldsTransmitter {

	// TO DO
	// Error handeling for absorb(String stringCellValue, String key)

	private Map<BasicShipmentFields, String> absorbedFeilds = new HashMap<>();

	public void absorb(String stringCellValue, String key) {
		absorbedFeilds.put(BasicShipmentFields.fromString(key), stringCellValue);
	}

	public Map<BasicShipmentFields, String> getMapOfAbsorbedFields() {
		return absorbedFeilds;
	}

	@Override
	public String toString() {
		return absorbedFeilds.getOrDefault("Load ID", "this is a default val, sth. went wrong!");
	}

}

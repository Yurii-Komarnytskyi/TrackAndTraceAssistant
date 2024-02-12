	package com.ykomarnytskyi2022.excel_services;

import java.util.HashMap;
import java.util.Map;
import com.ykomarnytskyi2022.freight.BasicShipmentFields;

public class FieldsTransmitter {

	private Map<BasicShipmentFields, String> absorbedFeilds = new HashMap<>();

	void absorb(String key, String stringCellValue) {
		StringBuilder invalidFields = new StringBuilder("Failed to add this field ");
		try {
			if (BasicShipmentFields.fromString(key) == null) {
				throw new IllegalArgumentException(key);				
			}
			absorbedFeilds.put(BasicShipmentFields.fromString(key), stringCellValue);
			
		} catch (IllegalArgumentException e) {
			invalidFields.append(key + " : " + e.getMessage());
		}
	}

	public Map<BasicShipmentFields, String> getMapOfAbsorbedFields() {
		return absorbedFeilds;
	}
	
	public void clearMapOfAbsorbedFields() {
		absorbedFeilds.clear();
	}

	@Override
	public String toString() {
		return absorbedFeilds.getOrDefault(BasicShipmentFields.SHIPMENT_ID, "this is a default value something went wrong!");
	}

}	

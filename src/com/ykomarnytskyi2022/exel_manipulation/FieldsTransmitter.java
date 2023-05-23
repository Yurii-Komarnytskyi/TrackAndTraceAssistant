package com.ykomarnytskyi2022.exel_manipulation;

import java.util.HashMap;
import java.util.Map;
import com.ykomarnytskyi2022.freight.BasicShipmentFields;

public class FieldsTransmitter {

	private Map<BasicShipmentFields, String> absorbedFeilds = new HashMap<>();

	public void absorb(String key, String stringCellValue) {
		StringBuilder invalidFields = new StringBuilder("Failed to add these fields:");
		try {
			if (BasicShipmentFields.fromString(key) == null) 
				throw new NullPointerException(key);
			absorbedFeilds.put(BasicShipmentFields.fromString(key), stringCellValue);
			
		} catch (NullPointerException e) {
			invalidFields.append(", " + e.getMessage());
		}
	}

	public Map<BasicShipmentFields, String> getMapOfAbsorbedFields() {
		return absorbedFeilds;
	}

	@Override
	public String toString() {
		return absorbedFeilds.getOrDefault(BasicShipmentFields.SHIPMENT_ID, "this is a default val, sth. went wrong!");
	}

}	

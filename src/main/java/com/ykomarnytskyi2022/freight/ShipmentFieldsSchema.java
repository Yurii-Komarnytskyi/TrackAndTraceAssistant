package com.ykomarnytskyi2022.freight;

public enum ShipmentFieldsSchema {
	
	ORGANIZATION_NAME("Organization Name"),
	SHIPMENT_NUMBER("Shipment #"),
	SHIPMENT_ID("Load ID"),
	TRIGGER("Trigger"),
	STATUS("Status"),
	SCAC_CODE("SCAC"),
	ORIGIN("Orig City"),
	DESTINATION("Dest City"),
	ORIGIN_STATE("O St"),
	DESTINATION_STATE("D St"),  
	SCHEDULED_AT("Scheduled At"),
	PNET("PNET"),
	PNLT("PNLT"),
	DNET("DNET"),
	DNLT("DNLT");
	
	private String feild;
	
	private ShipmentFieldsSchema(String str) {
		feild = str;
	}
	
	public static ShipmentFieldsSchema fromString(String str) throws NullPointerException {
		if(str == null || str.trim().equals("")) throw new NullPointerException("Key argument is equal to: \"" + str + "\"");
        for (ShipmentFieldsSchema fields : ShipmentFieldsSchema.values()) {
            if (fields.feild.equals(str.trim().toLowerCase())) {
                return fields;
            }
        }
        return null;
    }
	
	@Override
	public String toString() {
		return feild;
	}
}

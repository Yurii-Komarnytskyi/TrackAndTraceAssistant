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
	
	private String columnHeader;
	
	private ShipmentFieldsSchema(String str) {
		columnHeader = str;
	}
	
	@Override
	public String toString() {
		return columnHeader;
	}
}

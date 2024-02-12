package com.ykomarnytskyi2022.freight;

public enum BasicShipmentFields {
	ORGANIZATION_NAME("organization name"),
	SHIPMENT_NUMBER("shipment #"),
	SHIPMENT_ID("load id"),
	TRIGGER("trigger"),
	STATUS("status"),
	SCAC_CODE("scac"),
	ORIGIN("orig city"),
	DESTINATION("dest city"),
	ORIGIN_STATE("o st"),
	DESTINATION_STATE("d st"),  
	SCHEDULED_AT("scheduled at"),
	PNET("pnet"),
	PNLT("pnlt"),
	DNET("dnet"),
	DNLT("dnlt");
	
	private String feild;
	
	private BasicShipmentFields(String str) {
		feild = str;
	}
	@Override
	public String toString() {
		return feild;
	}
	
	public static BasicShipmentFields fromString(String str) throws NullPointerException {
		if(str == null || str.trim().equals("")) throw new NullPointerException("Key argument is equal to: \"" + str + "\"");
        for (BasicShipmentFields bsf : BasicShipmentFields.values()) {
            if (bsf.feild.equals(str.trim().toLowerCase())) {
                return bsf;
            }
        }
        return null;
    }
}

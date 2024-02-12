package com.ykomarnytskyi2022.freight;

public enum ShipmentStatus {
	SHIPMENT_PLANNING("shipment planning"),
	TENDERED("tendered"),
	PENDING_PU("pending pick up"),
	GATE_ARRIVAL("gate arrival"),
	CONFIRMED_PU("confirmed pick up"),
	DESTINATION_ARRIVED("destination arrived"),
	CONFIRMED_DEL("confirmed delivery");
	
	private String status;
	
	private ShipmentStatus (String str) {
		status = str;
	}
	
	@Override
	public String toString() {
		return status;
	}
	
	public static ShipmentStatus fromString(String str) throws NullPointerException {
		if(str == null || str.trim().equals("")) throw new NullPointerException("str argument is equal to: \"" + str + "\"");
        for (ShipmentStatus ss : ShipmentStatus.values()) {
            if (ss.status.equals(str.trim().toLowerCase())) {
                return ss;
            }
        }
        return ShipmentStatus.PENDING_PU;
    }
}

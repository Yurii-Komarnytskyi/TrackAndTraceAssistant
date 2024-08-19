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
	
	
	public static ShipmentStatus fromString(String str) {
		for (ShipmentStatus shipmentStatus : ShipmentStatus.values()) {
			if (shipmentStatus.status.equals(str.trim().toLowerCase())) {
				return shipmentStatus;
			}
		}
		return ShipmentStatus.PENDING_PU;
	}

	@Override
	public String toString() {
		return status;
	}
}

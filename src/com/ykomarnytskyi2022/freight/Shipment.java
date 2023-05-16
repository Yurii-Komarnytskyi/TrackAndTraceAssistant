package com.ykomarnytskyi2022.freight;

import java.util.Map;

import com.ykomarnytskyi2022.exel_parser.FieldsTransmitter;

class Shipment {
	private BasicShipmentFields shipmentNumber;
	private BasicShipmentFields shipmentID;
	private ShipmentStatus status;
	private BasicShipmentFields scac;
	private BasicShipmentFields originCity;
	private BasicShipmentFields destinationCity;
	private BasicShipmentFields originState; 
	private BasicShipmentFields destinationState;  
	private BasicShipmentFields PNET; 
	private BasicShipmentFields PNLT; 
	private BasicShipmentFields DNET; 
	private BasicShipmentFields DNLT; 
	
	@SuppressWarnings("unlikely-arg-type")
	public Shipment(FieldsTransmitter ft) {
		Map<BasicShipmentFields, String > mapOFields = ft.getMapOfAbsorbedFields();
		shipmentNumber =  BasicShipmentFields.fromString(mapOFields.get(shipmentNumber));
		shipmentID = BasicShipmentFields.fromString(mapOFields.get(shipmentID));
		status = ShipmentStatus.fromString(mapOFields.get(status));
		scac = BasicShipmentFields.fromString(mapOFields.get(scac));
		originCity = BasicShipmentFields.fromString(mapOFields.get(originCity));
		destinationCity = BasicShipmentFields.fromString(mapOFields.get(destinationCity));
		originState = BasicShipmentFields.fromString(mapOFields.get(originState));
		destinationState = BasicShipmentFields.fromString(mapOFields.get(destinationState));
		PNET = BasicShipmentFields.fromString(mapOFields.get(PNET));
		PNLT = BasicShipmentFields.fromString(mapOFields.get(PNLT));
		DNET = BasicShipmentFields.fromString(mapOFields.get(DNET));
		DNLT = BasicShipmentFields.fromString(mapOFields.get(DNLT));
	}
}

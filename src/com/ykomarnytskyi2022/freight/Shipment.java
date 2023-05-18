package com.ykomarnytskyi2022.freight;

import java.util.Map;
import java.util.Objects;

import com.ykomarnytskyi2022.exel_parser.FieldsTransmitter;


public class Shipment implements Comparable<Shipment> {
	private BasicShipmentFields organizationName;
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
		organizationName = BasicShipmentFields.fromString(mapOFields.get(organizationName));
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
	
	@Override
	public int hashCode() {	
		return Objects.hash(originCity, destinationCity, originState, destinationState, PNET, DNLT);
	}
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Shipment && 
			Objects.equals(shipmentID, ((Shipment) obj).shipmentID) &&
			Objects.equals(organizationName, ((Shipment) obj).organizationName) && 
			Objects.equals(originCity, ((Shipment) obj).originCity) && 
			Objects.equals(originState, ((Shipment) obj).originState) && 
			Objects.equals(destinationCity, ((Shipment) obj).destinationCity) && 
			Objects.equals(destinationState, ((Shipment) obj).destinationState);
	}

	@Override
	public int compareTo(Shipment o) {
		// TODO Auto-generated method stub
		return 0;
	}

}

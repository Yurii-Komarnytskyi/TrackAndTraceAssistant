package com.ykomarnytskyi2022.freight;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Objects;

import com.ykomarnytskyi2022.exel_manipulation.FieldsTransmitter;

public class Shipment extends Trackable implements Comparable<Shipment> {
	
	private String organizationName;
	@SuppressWarnings("unused")
	private String shipmentNumber;
	private String shipmentID;
	private ShipmentStatus status;
	@SuppressWarnings("unused")
	private String scac;
	private String originCity;
	private String destinationCity;
	private String originState; 
	private String destinationState;  
	private LocalDateTime PNET; 
	@SuppressWarnings("unused")
	private LocalDateTime PNLT; 
	@SuppressWarnings("unused")
	private LocalDateTime DNET; 
	private LocalDateTime DNLT; 
	
	public String getOriginCity() {
		return originCity;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public String getOriginState() {
		return originState;
	}

	public String getDestinationState() {
		return destinationState;
	}

	@Override
	public String getOriginPlaceAndState() {
		return originCity + ", " + originState;
	}

	@Override
	public String getDestinationPlaceAndState() {
		return destinationCity + ", " + destinationState;
	}
	public Shipment(FieldsTransmitter ft) {
		Map<BasicShipmentFields, String > mapOFields = ft.getMapOfAbsorbedFields();
		try {
			organizationName = mapOFields.get(BasicShipmentFields.ORGANIZATION_NAME);
			shipmentNumber =  mapOFields.get(BasicShipmentFields.SHIPMENT_NUMBER);
			shipmentID = mapOFields.get(BasicShipmentFields.SHIPMENT_ID);
			status = ShipmentStatus.fromString(mapOFields.get(BasicShipmentFields.STATUS));
			scac = mapOFields.get(BasicShipmentFields.SCAC_CODE);
			originCity = this.prettifyLocationName(mapOFields.get(BasicShipmentFields.ORIGIN));
			destinationCity = this.prettifyLocationName(mapOFields.get(BasicShipmentFields.DESTINATION));
			originState = mapOFields.get(BasicShipmentFields.ORIGIN_STATE);
			destinationState = mapOFields.get(BasicShipmentFields.DESTINATION_STATE);
			PNET = Trackable.convertToLocalDateTime(mapOFields.get(BasicShipmentFields.PNET));
			PNLT = Trackable.convertToLocalDateTime(mapOFields.get(BasicShipmentFields.PNLT));
			DNET = Trackable.convertToLocalDateTime(mapOFields.get(BasicShipmentFields.DNET));
			DNLT = Trackable.convertToLocalDateTime(mapOFields.get(BasicShipmentFields.DNLT));	
			
		} catch (NullPointerException e) {
			System.err.println(e.getMessage());
		} catch (DateTimeParseException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public ShipmentStatus getStatus() {
		return status;
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

	
	public String[] presentFdsToWriter() {
		String[] r = {shipmentID, originCity, destinationCity ,this.getSatusUpd(this)};
		return r;
	}
	
	@Override
	public String toString() {
		return shipmentID;
	}
	

}

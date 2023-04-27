package com.ykomarnytskyi2022.freight;

class Shipment {
	private String shipmentNumber;
	private String shipmentID;
	private ShipmentStatus status;
	private String scac;
	private String originCity;
	private String destinationCity;
	private String originState; // make an Enum
	private String destinationState;  // make an Enum
	private String PNET; // make Date
	private String PNLT; // make Date
	private String DNET; // make Date
	private String DNLT; // make Date
	public String getShipmentNumber() {
		return shipmentNumber;
	}
	public void setShipmentNumber(String shipmentNumber) {
		this.shipmentNumber = shipmentNumber;
	}
	public String getShipmentID() {
		return shipmentID;
	}
	public void setShipmentID(String shipmentID) {
		this.shipmentID = shipmentID;
	}
	public ShipmentStatus getStatus() {
		return status;
	}
	public void setStatus(ShipmentStatus status) {
		this.status = status;
	}
	public String getScac() {
		return scac;
	}
	public void setScac(String scac) {
		this.scac = scac;
	}
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	public String getOriginState() {
		return originState;
	}
	public void setOriginState(String originState) {
		this.originState = originState;
	}
	public String getDestinationState() {
		return destinationState;
	}
	public void setDestinationState(String destinationState) {
		this.destinationState = destinationState;
	}
	public String getPNET() {
		return PNET;
	}
	public void setPNET(String pNET) {
		PNET = pNET;
	}
	public String getPNLT() {
		return PNLT;
	}
	public void setPNLT(String pNLT) {
		PNLT = pNLT;
	}
	public String getDNET() {
		return DNET;
	}
	public void setDNET(String dNET) {
		DNET = dNET;
	}
	public String getDNLT() {
		return DNLT;
	}
	public void setDNLT(String dNLT) {
		DNLT = dNLT;
	}

}


package com.ykomarnytskyi2022.commands;

import java.time.LocalDateTime;
import java.util.Map;

import com.ykomarnytskyi2022.freight.Shipment;
import com.ykomarnytskyi2022.freight.ShipmentImpl;
import com.ykomarnytskyi2022.freight.ShipmentStatus;
import com.ykomarnytskyi2022.freight.TimeFrameRequirements;
import com.ykomarnytskyi2022.freight.Trackable;

public class ShipmentImplCommand {

	public Long id;
	public String organizationName;
	public String shipmentID;
	public ShipmentStatus status;
	public String scac;
	public String originCity;
	public String originState;
	public String originCityAndState;
	public String destinationCity;
	public String destinationState;
	public String destinationCityAndState;
	public LocalDateTime PNET;
	public LocalDateTime PNLT;
	public LocalDateTime DNET;
	public LocalDateTime DNLT;

	public ShipmentImplCommand(Shipment shipment) {
		Trackable trackable = (Trackable) shipment;
		ShipmentImpl shipmentImpl = (ShipmentImpl) shipment;
		Map<TimeFrameRequirements, LocalDateTime> map = shipment.getTimeFrameRequirements();

		id = trackable.getIdForCommandObj();
		organizationName = trackable.getOrganizationName();
		shipmentID = shipment.provideFieldsForExcelCells().get(0);
		status = trackable.getStatus();
		scac = trackable.getScacCode();
		originCity = shipmentImpl.getOriginCity();
		originState = shipmentImpl.getOriginState();
		originCityAndState = shipment.getOriginPlaceAndState();
		destinationCity = shipmentImpl.getDestinationCity();
		destinationState = shipmentImpl.getDestinationState();
		destinationCityAndState = shipment.getDestinationPlaceAndState();
		PNET = map.get(TimeFrameRequirements.PICKUP_NOT_EARLIER_THAN);
		PNLT = map.get(TimeFrameRequirements.PICKUP_NOT_LATER_THAN);
		DNET = map.get(TimeFrameRequirements.DELIVER_NOT_EARLIER_THAN);
		PNET = map.get(TimeFrameRequirements.DELIVER_NOT_LATER_THAN);
	}

	public ShipmentImplCommand() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
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

	public String getOriginState() {
		return originState;
	}

	public void setOriginState(String originState) {
		this.originState = originState;
	}

	public String getOriginCityAndState() {
		return originCityAndState;
	}

	public void setOriginCityAndState(String originCityAndState) {
		this.originCityAndState = originCityAndState;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getDestinationState() {
		return destinationState;
	}

	public void setDestinationState(String destinationState) {
		this.destinationState = destinationState;
	}

	public String getDestinationCityAndState() {
		return destinationCityAndState;
	}

	public void setDestinationCityAndState(String destinationCityAndState) {
		this.destinationCityAndState = destinationCityAndState;
	}

	public LocalDateTime getPNET() {
		return PNET;
	}

	public void setPNET(LocalDateTime pNET) {
		PNET = pNET;
	}

	public LocalDateTime getPNLT() {
		return PNLT;
	}

	public void setPNLT(LocalDateTime pNLT) {
		PNLT = pNLT;
	}

	public LocalDateTime getDNET() {
		return DNET;
	}

	public void setDNET(LocalDateTime dNET) {
		DNET = dNET;
	}

	public LocalDateTime getDNLT() {
		return DNLT;
	}

	public void setDNLT(LocalDateTime dNLT) {
		DNLT = dNLT;
	}

	@Override
	public String toString() {
		return "ShipmentImplCommand [organizationName=" + organizationName + ", shipmentID=" + shipmentID + ", status="
				+ status + ", scac=" + scac + ", originCity=" + originCityAndState + ", destinationCity=" + destinationCityAndState
				+ "]";
	}

}

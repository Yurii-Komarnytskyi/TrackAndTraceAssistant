package com.ykomarnytskyi2022.commands;

import java.time.LocalDateTime;
import java.util.Map;

import com.ykomarnytskyi2022.freight.ShipmentImpl;
import com.ykomarnytskyi2022.freight.ShipmentStatus;
import com.ykomarnytskyi2022.freight.TimeFrameRequirements;

public class ShipmentImplCommand {

	public String organizationName; 
	public String shipmentID; 
	public ShipmentStatus status; 
	public String scac;
	public String originCity; 
	public String destinationCity;
	public LocalDateTime PNET;
	public LocalDateTime PNLT;
	public LocalDateTime DNET;
	public LocalDateTime DNLT;
	
	public ShipmentImplCommand(ShipmentImpl shipment) {
		organizationName = shipment.getOrganizationName();
		shipmentID = shipment.provideFieldsForExcelCells().get(0);
		status = shipment.getStatus();
		scac = shipment.getScacCode();
		originCity = shipment.getOriginPlaceAndState();
		destinationCity = shipment.getDestinationPlaceAndState();
		Map<TimeFrameRequirements, LocalDateTime> map = shipment.getTimeFrameRequirements();
		PNET = map.get(TimeFrameRequirements.PICKUP_NOT_EARLIER_THAN);
		PNLT = map.get(TimeFrameRequirements.PICKUP_NOT_LATER_THAN);
		DNET = map.get(TimeFrameRequirements.DELIVER_NOT_EARLIER_THAN);
		PNET = map.get(TimeFrameRequirements.DELIVER_NOT_LATER_THAN);
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public String getShipmentID() {
		return shipmentID;
	}

	public ShipmentStatus getStatus() {
		return status;
	}

	public String getScac() {
		return scac;
	}

	public String getOriginCity() {
		return originCity;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public LocalDateTime getPNET() {
		return PNET;
	}

	public LocalDateTime getPNLT() {
		return PNLT;
	}

	public LocalDateTime getDNET() {
		return DNET;
	}

	public LocalDateTime getDNLT() {
		return DNLT;
	}

}


package com.ykomarnytskyi2022.commands;

import java.time.LocalDateTime;
import java.util.Map;

import com.ykomarnytskyi2022.freight.Shipment;
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
	public String destinationCity;
	public LocalDateTime PNET;
	public LocalDateTime PNLT;
	public LocalDateTime DNET;
	public LocalDateTime DNLT;

	public ShipmentImplCommand(Shipment shipment) {
		Trackable trackable = (Trackable) shipment;
		Map<TimeFrameRequirements, LocalDateTime> map = shipment.getTimeFrameRequirements();

		id = trackable.getIdForCommandObj();
		organizationName = trackable.getOrganizationName();
		shipmentID = shipment.provideFieldsForExcelCells().get(0);
		status = trackable.getStatus();
		scac = trackable.getScacCode();
		originCity = shipment.getOriginPlaceAndState();
		destinationCity = shipment.getDestinationPlaceAndState();
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

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	@Override
	public String toString() {
		return "ShipmentImplCommand [organizationName=" + organizationName + ", shipmentID=" + shipmentID + ", status="
				+ status + ", scac=" + scac + ", originCity=" + originCity + ", destinationCity=" + destinationCity
				+ "]";
	}

}

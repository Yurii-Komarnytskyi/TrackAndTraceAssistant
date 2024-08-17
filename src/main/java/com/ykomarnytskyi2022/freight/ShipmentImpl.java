package com.ykomarnytskyi2022.freight;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.Entity;

@Entity
public class ShipmentImpl extends Trackable implements Shipment {

	private String shipmentID;
	private String scac;

	public ShipmentImpl(Map<String, String> fields) {
		try {
			organizationName = fields.getOrDefault(ShipmentFieldsSchema.ORGANIZATION_NAME.toString(),
					"no organizationName");
			shipmentNumber = fields.getOrDefault(ShipmentFieldsSchema.SHIPMENT_NUMBER.toString(), "no shipmentNumber");
			shipmentID = fields.getOrDefault(ShipmentFieldsSchema.SHIPMENT_ID.toString(), "no shipmentID");
			status = ShipmentStatus
					.fromString(fields.getOrDefault(ShipmentFieldsSchema.STATUS.toString(), "shipment planning"));
			scac = fields.getOrDefault(ShipmentFieldsSchema.SCAC_CODE.toString(), "no scac code");
			originCity = this
					.prettifyLocationName(fields.getOrDefault(ShipmentFieldsSchema.ORIGIN.toString(), "no originCity"));
			destinationCity = this.prettifyLocationName(
					fields.getOrDefault(ShipmentFieldsSchema.DESTINATION.toString(), "no destinationCity"));
			originState = fields.getOrDefault(ShipmentFieldsSchema.ORIGIN_STATE.toString(), "no originState");
			destinationState = fields.getOrDefault(ShipmentFieldsSchema.DESTINATION_STATE.toString(),
					"no destinationState");
			PNET = Trackable.convertToLocalDateTime(
					fields.getOrDefault(ShipmentFieldsSchema.PNET.toString(), LocalDateTime.now().toString()));
			PNLT = Trackable.convertToLocalDateTime(
					fields.getOrDefault(ShipmentFieldsSchema.PNLT.toString(), LocalDateTime.now().toString()));
			DNET = Trackable.convertToLocalDateTime(
					fields.getOrDefault(ShipmentFieldsSchema.DNET.toString(), LocalDateTime.now().toString()));
			DNLT = Trackable.convertToLocalDateTime(
					fields.getOrDefault(ShipmentFieldsSchema.DNLT.toString(), LocalDateTime.now().toString()));

		} catch (DateTimeParseException e) {
			System.err.println(e.getMessage());
		}
	}

	public ShipmentImpl() {
	}

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
	public ShipmentStatus getStatus() {
		return status;
	}

	@Override
	public String getScacCode() {
		return scac;
	}

	@Override
	public int getNextStopNLT() {
		LocalDateTime nextStop = (this.status.ordinal() <= 3) ? PNLT : DNLT;
		return nextStop.getHour();
	}

	@Override
	public List<String> provideFieldsForExcelCells() {
		return Arrays.asList(shipmentID, originCity + " - " + destinationCity, getSatusUpdate(), status.toString(),
				scac);
	}

	public String getSatusUpdate() {
		StringBuilder body = new StringBuilder();
		int loadStatusCode = getStatus().ordinal();

		if (loadStatusCode == 2) {
			body.append(shipperETA).append(getOriginPlaceAndState()).append("?");
		} else if (loadStatusCode == 3) {
			body.append(gotLoaded).append(originCity).append("?");
		} else if (loadStatusCode == 4) {
			body.append(receiverETA).append(getDestinationPlaceAndState()).append("?");
		} else if (loadStatusCode == 5) {
			body.append(gotOffloaed).append(destinationCity).append("?");
		} else {
			return body.append(shipperETA).append(getOriginPlaceAndState()).append("?").toString();
		}
		return body.insert(0, getFormalGreeting()).toString();
	}

	@Override
	public String getOriginPlaceAndState() {
		return Shipment.concatCityAndState(originCity, originState);
	}

	@Override
	public String getDestinationPlaceAndState() {
		return Shipment.concatCityAndState(destinationCity, destinationState);
	}

	@Override
	public Map<TimeFrameRequirements, LocalDateTime> getTimeFrameRequirements() {
		return Map.of(TimeFrameRequirements.PICKUP_NOT_EARLIER_THAN, PNET, TimeFrameRequirements.PICKUP_NOT_LATER_THAN,
				PNLT, TimeFrameRequirements.DELIVER_NOT_EARLIER_THAN, DNET,
				TimeFrameRequirements.DELIVER_NOT_LATER_THAN, DNLT);
	}

	@Override
	public String getOrganizationName() {
		return organizationName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(originCity, destinationCity, originState, destinationState, PNET, DNLT);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof ShipmentImpl && Objects.equals(shipmentID, ((ShipmentImpl) obj).shipmentID)
				&& Objects.equals(organizationName, ((ShipmentImpl) obj).organizationName);
	}

	@Override
	public String toString() {
		return "ShipmentImpl [shipmentID=" + shipmentID + ", scac=" + scac + ", organizationName=" + organizationName
				+ ", shipmentNumber=" + shipmentNumber + ", status=" + status + ", originCity=" + originCity
				+ ", destinationCity=" + destinationCity + ", originState=" + originState + ", destinationState="
				+ destinationState + ", id=" + id + "]";
	}

}

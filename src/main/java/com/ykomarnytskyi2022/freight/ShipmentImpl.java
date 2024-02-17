package com.ykomarnytskyi2022.freight;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShipmentImpl extends Trackable implements Shipment {

	private String organizationName;
	private String shipmentNumber;
	private String shipmentID;
	private ShipmentStatus status;
	private String scac;
	private String originCity;
	private String destinationCity;
	private String originState;
	private String destinationState;
	private LocalDateTime PNET;
	private LocalDateTime PNLT;
	private LocalDateTime DNET;
	private LocalDateTime DNLT;

	public ShipmentImpl(Map<String, String> fields) {
		// CATCH NONE EXISTING FIELDZ !S
		try {
			organizationName = fields.get(ShipmentFieldsSchema.ORGANIZATION_NAME.toString());
			shipmentNumber = fields.get(ShipmentFieldsSchema.SHIPMENT_NUMBER.toString());
			shipmentID = fields.get(ShipmentFieldsSchema.SHIPMENT_ID.toString());
			status = ShipmentStatus.fromString(fields.get(ShipmentFieldsSchema.STATUS.toString()));
			scac = fields.get(ShipmentFieldsSchema.SCAC_CODE.toString());
			originCity = this.prettifyLocationName(fields.get(ShipmentFieldsSchema.ORIGIN.toString()));
			destinationCity = this.prettifyLocationName(fields.get(ShipmentFieldsSchema.DESTINATION.toString()));
			originState = fields.get(ShipmentFieldsSchema.ORIGIN_STATE.toString());
			destinationState = fields.get(ShipmentFieldsSchema.DESTINATION_STATE.toString());
			PNET = Trackable.convertToLocalDateTime(fields.get(ShipmentFieldsSchema.PNET.toString()));
			PNLT = Trackable.convertToLocalDateTime(fields.get(ShipmentFieldsSchema.PNLT.toString()));
			DNET = Trackable.convertToLocalDateTime(fields.get(ShipmentFieldsSchema.DNET.toString()));
			DNLT = Trackable.convertToLocalDateTime(fields.get(ShipmentFieldsSchema.DNLT.toString()));

		} catch (NullPointerException e) {
			System.err.println(e.getMessage());
		} catch (DateTimeParseException e) {
			System.err.println(e.getMessage());
		}
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
		return originCity + ", " + originState;
	}

	@Override
	public String getDestinationPlaceAndState() {
		return destinationCity + ", " + destinationState;
	}

	@Override
	public Map<TimeFrameRequirements, LocalDateTime> getTimeFrameRequirements() {
		return Map.of(TimeFrameRequirements.PICKUP_NOT_EARLIER_THAN, PNET, TimeFrameRequirements.PICKUP_NOT_LATER_THAN,
				PNLT, TimeFrameRequirements.DELIVER_NOT_EARLIER_THAN, DNET,
				TimeFrameRequirements.DELIVER_NOT_LATER_THAN, DNLT);
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
		return "ShipmentImpl [shipmentNumber=" + shipmentNumber + ", shipmentID=" + shipmentID + "]";
	}

	
}

package com.ykomarnytskyi2022.freight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Trackable {

	static String shipperETA = "Could you advise on an ETA to the shipper in ";
	static String gotLoaded = "Could you advise if this load has been picked up in ";
	static String receiverETA = "Could you advise on an ETA to the receiver in ";
	static String gotOffloaed = "Could you advise if this load has been delivered in ";

	static LocalDateTime convertToLocalDateTime(String str) {
		DateTimeFormatter formattedStr = DateTimeFormatter
				.ofPattern(String.format("MM/dd/uu HH:mm '%s'", str.substring(str.length() - 2)));
		LocalDateTime ldt = LocalDateTime.parse(str, formattedStr);
		return ldt;
	}

	public String getFormalGreeting() {
		int currentHour = LocalDateTime.now().getHour();
		if (currentHour >= 18 && currentHour <= 21)
			return "Good evening, \n";
		return "Good " + ((currentHour >= 6 && currentHour <= 12) ? "morning, \n" : "afternoon, \n");
	}

	public String getSatusUpdate(Shipment shipment) {
		StringBuilder body = new StringBuilder();
		int loadStatusCode = shipment.getStatus().ordinal();
		
		if(loadStatusCode == 2) {
			body.append(shipperETA).append(shipment.getOriginPlaceAndState()).append("?");
		} else if (loadStatusCode == 3) {
			body.append(gotLoaded).append(shipment.getOriginCity()).append("?");
		} else if (loadStatusCode == 4 ) {
			body.append(receiverETA).append(shipment.getDestinationPlaceAndState()).append("?");
		} else if (loadStatusCode == 5) {
			body.append(gotOffloaed).append(shipment.getDestinationCity()).append("?");
		}  else {
			return body.append(shipperETA).append(shipment.getOriginPlaceAndState()).append("?").toString();
		}
		return body.insert(0, getFormalGreeting()).toString();
	}

	String prettifyLocationName(String str) {
		return Stream.of(str.trim().toLowerCase().split(" "))
				.map(s -> (String.valueOf(s.charAt(0)).toUpperCase()).concat(s.substring(1)))
				.collect(Collectors.joining(" "));

	}

	public abstract String getOriginPlaceAndState();

	public abstract String getDestinationPlaceAndState();

}

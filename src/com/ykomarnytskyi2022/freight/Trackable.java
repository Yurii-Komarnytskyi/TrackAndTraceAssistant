package com.ykomarnytskyi2022.freight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Trackable {

	static String es = "Could you advise on an ETA to the shipper in ";
	static String ifp = "Could you advise if this load has been picked up in ";
	static String er = "Could you advise on an ETA to the receiver in ";
	static String ifd = "Could you advise if this load has been delivered in ";

	static LocalDateTime convertToLocalDateTime(String str) {
		DateTimeFormatter formattedStr = DateTimeFormatter
				.ofPattern(String.format("MM/dd/uu HH:mm '%s'", str.substring(str.length() - 2)));
		LocalDateTime ldt = LocalDateTime.parse(str, formattedStr);
		return ldt;
	}

	public String formalGreeting() {
		int currentHour = LocalDateTime.now().getHour();
		if (currentHour >= 18 && currentHour <= 21)
			return "Good evening, \n";
		return "Good " + ((currentHour >= 6 && currentHour <= 12) ? "morning, \n" : "afternoon, \n");
	}

	public String getSatusUpd(Shipment s) {
		String body;

		switch (s.getStatus().ordinal()) {
		case 2:
			body = es + s.getOriginPlaceAndState() + "?";
			break;
		case 3:
			body = ifp + s.getOriginCity() + "?";
			break;
		case 4:
			body = er + s.getDestinationPlaceAndState() + "?";
			break;
		case 5:
			body = ifd + s.getDestinationCity() + "?";
			break;
		default:
			body = "No Carrier Yet";
			break;
		}

		return formalGreeting() + body;
	}

	String prettifyLocationName(String str) {
		return Stream.of(str.trim().toLowerCase().split(" "))
				.map(s -> (String.valueOf(s.charAt(0)).toUpperCase()).concat(s.substring(1)))
				.collect(Collectors.joining(" "));

	}

	public abstract String getOriginPlaceAndState();

	public abstract String getDestinationPlaceAndState();

}

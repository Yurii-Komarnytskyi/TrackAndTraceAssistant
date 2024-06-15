package com.ykomarnytskyi2022.freight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ykomarnytskyi2022.domain.BaseEntity;

import jakarta.persistence.Entity;

@Entity
public abstract class Trackable extends BaseEntity {

	static String shipperETA = "Could you advise on an ETA to the shipper in ";
	static String gotLoaded = "Could you advise if this load has been picked up in ";
	static String receiverETA = "Could you advise on an ETA to the receiver in ";
	static String gotOffloaed = "Could you advise if this load has been delivered in ";
	
	static LocalDateTime convertToLocalDateTime(String str) {
		DateTimeFormatter formattedStr = DateTimeFormatter
				.ofPattern(String.format("MM/dd/uu HH:mm '%s'", str.substring(str.length() - 2)));
		LocalDateTime localDateTime = LocalDateTime.parse(str, formattedStr);
		return localDateTime;
	}

	public String getFormalGreeting() {
		int currentHour = LocalDateTime.now().getHour();
		if (currentHour >= 18 && currentHour <= 21)
			return "Good evening, \n";
		return "Good " + ((currentHour >= 6 && currentHour <= 12) ? "morning, \n" : "afternoon, \n");
	}


	String prettifyLocationName(String str) {
		return Stream.of(str.trim().toLowerCase().split(" "))
				.map(s -> (String.valueOf(s.charAt(0)).toUpperCase()).concat(s.substring(1)))
				.collect(Collectors.joining(" "));
	}
	
	public abstract String getSatusUpdate();
	
	public abstract String getScacCode();
	
	public abstract int getNextStopNLT();

	public abstract ShipmentStatus getStatus();
}

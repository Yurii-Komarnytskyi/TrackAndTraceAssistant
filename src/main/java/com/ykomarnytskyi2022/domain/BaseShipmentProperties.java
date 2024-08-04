package com.ykomarnytskyi2022.domain;

import java.time.LocalDateTime;

import com.ykomarnytskyi2022.freight.ShipmentStatus;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseShipmentProperties extends BaseEntity {
	
	protected String organizationName; 
	protected String shipmentNumber; 
	protected ShipmentStatus status; 
	protected String originCity; 
	protected String destinationCity;
	protected String originState;
	protected String destinationState;
	protected LocalDateTime PNET;
	protected LocalDateTime PNLT;
	protected LocalDateTime DNET;
	protected LocalDateTime DNLT;
	
	@Override
	public String toString() {
		return "BaseShipmentProperties [organizationName=" + organizationName + ", shipmentNumber=" + shipmentNumber
				+ ", status=" + status + ", originCity=" + originCity + ", destinationCity=" + destinationCity
				+ ", originState=" + originState + ", destinationState=" + destinationState + "]";
	}

}

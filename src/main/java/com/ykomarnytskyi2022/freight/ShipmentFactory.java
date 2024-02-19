package com.ykomarnytskyi2022.freight;

import java.util.Map;

public interface ShipmentFactory {
	
	Shipment create(Map<String, String> fields);
	
}

package com.ykomarnytskyi2022.freight;

import java.util.Map;

public class ShipmentFactoryImpl implements ShipmentFactory {

	@Override
	public Shipment create(Map<String, String> fields) {
		return new ShipmentImpl(fields);
	}

}

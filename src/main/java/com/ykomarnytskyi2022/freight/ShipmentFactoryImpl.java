package com.ykomarnytskyi2022.freight;

import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"nocTeam" , "default"})
public class ShipmentFactoryImpl implements ShipmentFactory {

	@Override
	public Shipment create(Map<String, String> fields) {
		return new ShipmentImpl(fields);
	}

}

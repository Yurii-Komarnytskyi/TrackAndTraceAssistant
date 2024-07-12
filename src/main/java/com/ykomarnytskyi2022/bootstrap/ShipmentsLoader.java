package com.ykomarnytskyi2022.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ykomarnytskyi2022.freight.Trackable;
import com.ykomarnytskyi2022.repositories.ShipmentRepository;
import com.ykomarnytskyi2022.services.excel.ExcelOperations;

@Component
public class ShipmentsLoader implements CommandLineRunner {
	
	final ExcelOperations excelOperations;
	final ShipmentRepository shipmentRepository;
	
	@Autowired
	public ShipmentsLoader(ExcelOperations excelOperations, ShipmentRepository shipmentRepository) {
		this.excelOperations = excelOperations;
		this.shipmentRepository = shipmentRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		excelOperations.write();
		excelOperations.expose().stream().forEach(customerList -> {
			customerList.stream().forEach(shipment -> {
				shipmentRepository.save((Trackable)shipment);
			});
		});
	}

}

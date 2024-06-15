package com.ykomarnytskyi2022.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ykomarnytskyi2022.repositories.ShipmentRepository;

@Controller
public class ShipmentsController {

	private final ShipmentRepository shipmentRepository;
	private final String SHIPMENTS = "shipments";
	
	@Autowired
	public ShipmentsController(ShipmentRepository shipmentRepository) {
		this.shipmentRepository = shipmentRepository;
	}


	@GetMapping("/shipments")
	public String getShipmentsList(Model model) {
		model.addAttribute(SHIPMENTS, shipmentRepository.findAll());
		return SHIPMENTS.concat("/shipmentsList");
	}
}

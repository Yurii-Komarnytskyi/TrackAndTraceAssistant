package com.ykomarnytskyi2022.controllers;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ykomarnytskyi2022.commands.ShipmentImplCommand;
import com.ykomarnytskyi2022.freight.Shipment;
import com.ykomarnytskyi2022.repositories.TrackableRepository;

@Controller
public class ShipmentsController {

	private final TrackableRepository trackableRepository;
	private final String SHIPMENTS = "shipments";

	@Autowired
	public ShipmentsController(TrackableRepository trackableRepository) {
		this.trackableRepository = trackableRepository;
	}

	@GetMapping("/shipments")
	public String getShipmentsList(Model model) {
		List<ShipmentImplCommand> shipments = StreamSupport.stream(trackableRepository.findAll().spliterator(), false)
				.map(trackable -> new ShipmentImplCommand((Shipment) trackable)).toList();
		model.addAttribute(SHIPMENTS, shipments);
		return SHIPMENTS.concat("/shipmentList");
	}
}

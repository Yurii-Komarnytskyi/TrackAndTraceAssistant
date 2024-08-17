package com.ykomarnytskyi2022.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ykomarnytskyi2022.commands.ShipmentImplCommand;
import com.ykomarnytskyi2022.freight.Shipment;
import com.ykomarnytskyi2022.freight.Trackable;
import com.ykomarnytskyi2022.repositories.TrackableRepository;

@Controller
public class ShipmentsController {

	private final TrackableRepository trackableRepository;

	private final String SHIPMENTS = "shipments";
	private final String SHIPMENTS_SHIPMENT_LIST = "shipments/shipmentList";
	private final String SHIPMENTS_SHIPMENT_EDIT_FORM = "shipments/shipmentEditForm";

	@Autowired
	public ShipmentsController(TrackableRepository trackableRepository) {
		this.trackableRepository = trackableRepository;
	}

	@GetMapping("/shipments")
	public String getShipmentsList(Model model) {
		List<ShipmentImplCommand> shipments = trackableRepository.findAll().stream()
				.map(trackable -> new ShipmentImplCommand((Shipment) trackable)).toList();
		model.addAttribute(SHIPMENTS, shipments);
		return SHIPMENTS_SHIPMENT_LIST;
	}

	@GetMapping("shipments/{id}/delete")
	public String deleteShipment(@PathVariable Long id) {
		trackableRepository.deleteById(id);
		return "redirect:/".concat(SHIPMENTS);
	}

	@GetMapping("shipments/{id}/edit")
	public String editShipment(@PathVariable Long id, Model model) {
		Trackable t = trackableRepository.findById(id).get();
		ShipmentImplCommand sic = new ShipmentImplCommand((Shipment) t);
		model.addAttribute("shipment", sic);
		return SHIPMENTS_SHIPMENT_EDIT_FORM;
	}

	@PostMapping("shipments/{id}/save")
	public String saveShipment(@PathVariable Long id, @ModelAttribute ShipmentImplCommand command) {
		Long commandId = command.getId();
		trackableRepository.setOriginCityById(command.getOriginCity(), commandId);
		trackableRepository.setOriginStateById(command.getOriginState(), commandId);
		trackableRepository.setDestinationCityById(command.getDestinationCity(), commandId);
		trackableRepository.setDestinationStateById(command.getDestinationState(), commandId);
		trackableRepository.setScacById(command.getScac(),commandId);
		trackableRepository.setShipmentIDById(command.getShipmentID(), commandId);
		return "redirect:/".concat(SHIPMENTS);
	}

}

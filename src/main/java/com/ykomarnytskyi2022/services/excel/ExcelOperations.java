package com.ykomarnytskyi2022.services.excel;

import java.util.List;

import com.ykomarnytskyi2022.freight.Shipment;

public interface ExcelOperations {

	void write();
	List<List<Shipment>> expose();
}

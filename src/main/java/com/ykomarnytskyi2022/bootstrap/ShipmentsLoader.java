package com.ykomarnytskyi2022.web.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ykomarnytskyi2022.excel_services.ExcelOperations;
import com.ykomarnytskyi2022.excel_services.ExcelOperationsImpl;

@Component
public class ShipmentsLoader implements CommandLineRunner {
	
	final ExcelOperations delegator;
	
	@Autowired
	public ShipmentsLoader(ExcelOperations delegator) {
		this.delegator = delegator;
	}

	@Override
	public void run(String... args) throws Exception {
		delegator.write(); 
	}

}

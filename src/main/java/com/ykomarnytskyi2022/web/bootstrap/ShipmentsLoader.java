package com.ykomarnytskyi2022.web.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ykomarnytskyi2022.excel_services.ParsingAndWritingDelegator;

@Component
public class ShipmentsLoader implements CommandLineRunner {
	
	final ParsingAndWritingDelegator delegator;
	
	@Autowired
	public ShipmentsLoader(ParsingAndWritingDelegator delegator) {
		this.delegator = delegator;
	}

	@Override
	public void run(String... args) throws Exception {
		delegator.readAndWrite();
		delegator.readAndWrite();
	}

}

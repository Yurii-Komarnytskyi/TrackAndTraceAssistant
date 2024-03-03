package com.ykomarnytskyi2022.config;

import java.nio.file.Path;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.ykomarnytskyi2022.excel_services.ExcelParserFactory;
import com.ykomarnytskyi2022.excel_services.ExcelParserFactoryImpl;
import com.ykomarnytskyi2022.excel_services.ExcelWriter;
import com.ykomarnytskyi2022.excel_services.FreightExcelWriter;
import com.ykomarnytskyi2022.excel_services.ParsingAndWritingDelegator;
import com.ykomarnytskyi2022.freight.ShipmentFactory;
import com.ykomarnytskyi2022.freight.ShipmentFactoryImpl;

@Configuration
public class ExcelManipulationConfig {

	@Bean
	@Scope(scopeName = "prototype")
	public ParsingAndWritingDelegator parsingAndWritingDelegator(Path excelFileBeingWritten) {
		return new ParsingAndWritingDelegator(excelWriter(excelFileBeingWritten), excelParserFactory());
	}
		
	@Bean
	@Scope(scopeName = "prototype")
	public ExcelWriter excelWriter(Path path) {
		return new FreightExcelWriter(path);
	}
	
	@Bean
	ExcelParserFactory excelParserFactory() {
		return new ExcelParserFactoryImpl(shipmentFactory());
	}

	@Bean
	ShipmentFactory shipmentFactory() {
		return new ShipmentFactoryImpl();
	}
	
}

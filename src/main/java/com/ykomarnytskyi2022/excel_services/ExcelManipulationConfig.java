package com.ykomarnytskyi2022.excel_services;

import java.nio.file.Path;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ExcelManipulationConfig {

	@Bean
	@Scope(scopeName = "prototype")
	public ParsingAndWritingDelegator getParsingAndWritingDelegator(Path excelFileBeingWritten) {
		return new ParsingAndWritingDelegator(getFreightExcelWriter(excelFileBeingWritten));
	}
		
	@Bean
	@Scope(scopeName = "prototype")
	public ExcelWriter getFreightExcelWriter(Path path) {
		return new FreightExcelWriter(path);
	}
	
}

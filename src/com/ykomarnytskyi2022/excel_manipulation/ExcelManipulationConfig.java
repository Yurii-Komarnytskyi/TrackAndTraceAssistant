package com.ykomarnytskyi2022.excel_manipulation;

import java.nio.file.Path;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ExcelManipulationConfig {

	@Bean
	@Scope(scopeName = "prototype")
	public ParsingAndWritingDelegator getParsingAndWritingDelegator(Path excelFileBeingWritten) {
		return new ParsingAndWritingDelegator(getExcelWriter(excelFileBeingWritten));
	}
		
	@Bean
	@Scope(scopeName = "prototype")
	public ExcelWriter getExcelWriter(Path path) {
		return new ExcelWriter(path);
	}
	
}

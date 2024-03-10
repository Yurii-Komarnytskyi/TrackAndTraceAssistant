package com.ykomarnytskyi2022.config;

import java.nio.file.Path;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import com.ykomarnytskyi2022.excel_services.ExcelParserFactory;
import com.ykomarnytskyi2022.excel_services.ExcelParserFactoryImpl;
import com.ykomarnytskyi2022.excel_services.ExcelWriter;
import com.ykomarnytskyi2022.excel_services.FreightExcelWriter;
import com.ykomarnytskyi2022.excel_services.LocalMachinePaths;
import com.ykomarnytskyi2022.excel_services.ParsingAndWritingDelegator;
import com.ykomarnytskyi2022.freight.ShipmentFactory;
import com.ykomarnytskyi2022.freight.ShipmentFactoryImpl;

@Configuration
@EnableConfigurationProperties(LocalMachinePaths.class)
public class ExcelManipulationConfig {

	@Bean
	@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ParsingAndWritingDelegator parsingAndWritingDelegator(LocalMachinePaths localMachinePaths) {
		return new ParsingAndWritingDelegator(excelWriter(localMachinePaths.getBlankFile()), excelParserFactory(), localMachinePaths);
	}

	@Bean
	@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ExcelWriter excelWriter(Path path) {
		return new FreightExcelWriter(path);
	}

	@Profile({ "nocTeam", "default" })
	@Bean
	ExcelParserFactory excelParserFactory() {
		return new ExcelParserFactoryImpl(shipmentFactory());
	}

	@Profile({ "nocTeam", "default" })
	@Bean
	ShipmentFactory shipmentFactory() {
		return new ShipmentFactoryImpl();
	}
	
}

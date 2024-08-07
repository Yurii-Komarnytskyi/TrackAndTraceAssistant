package com.ykomarnytskyi2022.config;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import com.ykomarnytskyi2022.freight.ShipmentFactory;
import com.ykomarnytskyi2022.freight.ShipmentFactoryImpl;
import com.ykomarnytskyi2022.services.excel.ExcelOperations;
import com.ykomarnytskyi2022.services.excel.ExcelOperationsImpl;
import com.ykomarnytskyi2022.services.excel.ExcelParserFactory;
import com.ykomarnytskyi2022.services.excel.ExcelParserFactoryImpl;
import com.ykomarnytskyi2022.services.excel.ExcelWriter;
import com.ykomarnytskyi2022.services.excel.FreightExcelWriter;
import com.ykomarnytskyi2022.services.excel.LocalMachinePaths;

@Configuration
public class ExcelManipulationConfig {

	@Bean
	@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ExcelOperations excelOperationsImpl(LocalMachinePaths localMachinePaths,
			@Value(value = "${sheet-names}") List<String> sheetNames) {

		return new ExcelOperationsImpl(excelWriter(localMachinePaths.getBlankFile(), sheetNames),
				excelParserFactory(), localMachinePaths);
	}

	@Bean
	@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ExcelWriter excelWriter(Path path, List<String> sheetNames) {
		return new FreightExcelWriter(path, sheetNames);
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

	@Bean
	public LocalMachinePaths localMachinePaths(@Value(value = "${source-excel-files}") List<String> paths,
			@Value(value = "${path-to.blankFile}") String blankFile) {
		return new LocalMachinePaths(paths, blankFile);
	}
}

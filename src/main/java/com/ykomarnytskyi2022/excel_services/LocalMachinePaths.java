package com.ykomarnytskyi2022.excel_services;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("path-to")
public class LocalMachinePaths {

	private final Path blankFile;
	
	private final List<Path> pathsToSourceExcelFiles = new ArrayList<>();
	
	public LocalMachinePaths(@Value("${path-to.customerC}") String customerC, @Value("${path-to.customerM}") String customerM,
			@Value("${path-to.customerS}") String customerS, @Value("${path-to.customerP}") String customerP,
			@Value("${path-to.blankFile}") String blankFile) {
		pathsToSourceExcelFiles.addAll(
				List.of(customerC, customerM, customerS, customerP).stream().map(str -> Paths.get(str)).toList());
		this.blankFile = Paths.get(blankFile);
	}
	
	public Collection<Path> getPathsToSourceExcelFiles() {
		return pathsToSourceExcelFiles;
	}

	public Path getBlankFile() {
		return blankFile;
	}

}

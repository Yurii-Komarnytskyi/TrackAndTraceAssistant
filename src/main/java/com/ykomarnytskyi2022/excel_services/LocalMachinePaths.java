package com.ykomarnytskyi2022.excel_services;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@ConfigurationProperties("paths")
public class LocalMachinePaths {

	private final Path blankFile;
	
	private final List<Path> pathsToSourceExcelFiles = new ArrayList<>();
	
	public LocalMachinePaths(List<String> paths, String blankFile) {
		this.pathsToSourceExcelFiles.addAll(paths.stream().map(str -> Paths.get(str)).toList());
		this.blankFile = Paths.get(blankFile);
	}
	
	public Collection<Path> getPathsToSourceExcelFiles() {
		return pathsToSourceExcelFiles;
	}

	public Path getBlankFile() {
		return blankFile;
	}

}

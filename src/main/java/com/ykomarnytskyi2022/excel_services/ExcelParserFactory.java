package com.ykomarnytskyi2022.excel_services;

import java.nio.file.Path;

public interface ExcelParserFactory {
	
	ExcelParser create(Path path, String sheetName);

}

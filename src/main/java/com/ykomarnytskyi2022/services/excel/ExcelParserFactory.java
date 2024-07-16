package com.ykomarnytskyi2022.services.excel;

import java.nio.file.Path;

public interface ExcelParserFactory {
	
	ExcelParser create(Path path, String sheetName);

}

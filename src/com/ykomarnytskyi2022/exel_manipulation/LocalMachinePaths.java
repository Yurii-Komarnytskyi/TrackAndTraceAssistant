package com.ykomarnytskyi2022.exel_manipulation;

import java.nio.file.Path;
import java.nio.file.Paths;

class LocalMachinePaths {
	
	static Path customerC = Paths.get("C:\\Users\\LEMMY\\Desktop\\eclipse-workspace\\TrackAndTraceAssistant\\resources\\C.xls"); 
	static Path customerS = Paths.get("C:\\Users\\LEMMY\\Desktop\\eclipse-workspace\\TrackAndTraceAssistant\\resources\\S.xls"); 
	static Path customerM = Paths.get("C:\\Users\\LEMMY\\Desktop\\eclipse-workspace\\TrackAndTraceAssistant\\resources\\M.xls"); 

	static final String SHEET_NAME = "Sheet1";
	static final String SEARCH_RESULTS = "Search Results";
	static Path blankExelFile = Paths.get("C:\\Users\\LEMMY\\Desktop\\eclipse-workspace\\TrackAndTraceAssistant\\resources\\FreightDemo.xls");
}

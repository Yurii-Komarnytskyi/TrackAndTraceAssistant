package com.ykomarnytskyi2022.excel_services;

import java.nio.file.Path;
import java.nio.file.Paths;

abstract class LocalMachinePaths {
	static Path customerC = Paths
			.get("C:\\Users\\LEMMY\\Desktop\\eclipse-workspace\\TrackAndTraceAssistant\\src\\main\\resources\\C.xls");
	static Path customerS = Paths
			.get("C:\\Users\\LEMMY\\Desktop\\eclipse-workspace\\TrackAndTraceAssistant\\src\\main\\resources\\S.xls");
	static Path customerM = Paths
			.get("C:\\Users\\LEMMY\\Desktop\\eclipse-workspace\\TrackAndTraceAssistant\\src\\main\\resources\\M.xls");

	static Path customerP = Paths
			.get("C:\\Users\\LEMMY\\Desktop\\eclipse-workspace\\TrackAndTraceAssistant\\src\\main\\resources\\P.xls");
	static final String SHEET_NAME = "Sheet1";
	static Path blankExelFile = Paths.get(
			"C:\\Users\\LEMMY\\Desktop\\eclipse-workspace\\TrackAndTraceAssistant\\src\\main\\resources\\FreightDemo.xls");

	static final String DEFAULT_SHEET_NAME = "Sheet1";
	static final String SEARCH_RESULTS = "Search Results";
}

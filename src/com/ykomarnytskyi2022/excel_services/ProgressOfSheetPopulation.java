package com.ykomarnytskyi2022.excel_services;

class ProgressOfSheetPopulation {
	private String sheetName;
	private int latestWrittenRow = 0;
	private int writeIntoRow = 0;

	public ProgressOfSheetPopulation(String sheetName) {
		this.sheetName = sheetName;
	}

	int getLatestWrittenRow() {
		return latestWrittenRow;
	}

	void setLatestWrittenRow(int latestWrittenRow) {
		this.latestWrittenRow += latestWrittenRow;
	}

	String getSheetName() {
		return sheetName;
	}

	int getWriteIntoRow() {
		return writeIntoRow;
	}

	void resetWriteIntoRowToZero() {
		writeIntoRow = 0;
	}

	public void incrementWriteIntoRowByOne() {
		writeIntoRow++;
	}

}

package com.ykomarnytskyi2022.excel_services;

class ProgressOfSheetPopulation {
	private String sheetName;
	private int latestWrittenRow = 0;
	private int writeIntoRow = 0;

	public ProgressOfSheetPopulation(String sheetName) {
		this.sheetName = sheetName;
	}

	protected int getLatestWrittenRow() {
		return latestWrittenRow;
	}

	protected void setLatestWrittenRow(int latestWrittenRow) {
		this.latestWrittenRow += latestWrittenRow;
	}

	protected String getSheetName() {
		return sheetName;
	}

	protected int getWriteIntoRow() {
		return writeIntoRow;
	}

	protected void resetWriteIntoRowToZero() {
		writeIntoRow = 0;
	}

	protected void incrementWriteIntoRowByOne() {
		writeIntoRow++;
	}

}

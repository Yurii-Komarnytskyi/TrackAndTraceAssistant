package com.ykomarnytskyi2022.exel_manipulation;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.ykomarnytskyi2022.freight.Shipment;

class ParsingAndWritingDelegator {
	private Set<List<Shipment>> readyToWriteLists = new LinkedHashSet<>(); 
	private Set<ExcelParser> fromSetOfFiles = new LinkedHashSet<>();
	private ExcelWriter toFile;
	private ExcelParser fromFile;
	
	public ParsingAndWritingDelegator(ExcelWriter ew, Set<ExcelParser> ep) {
		toFile = ew;
		fromSetOfFiles = ep;
	}
	public ParsingAndWritingDelegator(ExcelWriter ew, ExcelParser ep) {
		toFile = ew;
		fromFile = ep;
	}
	
	public <T extends Shipment> void readAndWrite(Predicate<T> predicate) {
		if(fromFile != null) {
			List<Shipment> shipmList = ExcelWriter.mapFromFieldsTransToShipment(fromFile.parseFreightDataInSingleFile());
			toFile.writeToExcel(ExcelWriter.selectFreightComplyingWithPredicate(shipmList, predicate));
		} else if (fromSetOfFiles.size() > 0) {
			toFile.writeToExcelFromMultFiles(ExcelParser.readFromMultipleFiles(fromSetOfFiles), predicate);
		}
	}
	
	boolean offerExcelParser(ExcelParser ep) {
		return fromSetOfFiles.add(ep);
	}
	
	Stream<List<Shipment>> getReadyToWriteLists() {
		return this.readyToWriteLists.stream();
	}
	
	public static void main(String[] args) {
		ExcelWriter write = new ExcelWriter(ExcelWriter.WRITE_TO, ExcelWriter.SHEET_NAME);
		Set<ExcelParser> testSet = new LinkedHashSet<>();
		
		testSet.add(new ExcelParser(write.CENTRIA_PATH, write.SEARCH_RESULTS));
		testSet.add(new ExcelParser(write.MHS_PATH, write.SEARCH_RESULTS));
		testSet.add(new ExcelParser(write.STEEL_PATH, write.SEARCH_RESULTS));
		
		ParsingAndWritingDelegator tool = new ParsingAndWritingDelegator(write, testSet);
//		ReadWriteMiddleman toolCentria= new ReadWriteMiddleman(write, new ExcelParser(write.CENTRIA_PATH, write.SEARCH_RESULTS));
			
		tool.readAndWrite(ExcelWriter.SortingStrategies::chooseFreightThatShipsToday);

	}
	
}

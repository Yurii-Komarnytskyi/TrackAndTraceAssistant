package com.ykomarnytskyi2022.freight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface Shipment {
	
	public List<String> provideFieldsForExcelCells();
	
	public  String getOriginPlaceAndState();
	
	public String getDestinationPlaceAndState();
	
	public Map<TimeFrameRequirements, LocalDateTime> getTimeFrameRequirements();
}

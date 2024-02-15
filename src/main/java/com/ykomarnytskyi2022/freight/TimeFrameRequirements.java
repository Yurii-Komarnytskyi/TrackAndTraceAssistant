package com.ykomarnytskyi2022.freight;

public enum TimeFrameRequirements {
	
	PICKUP_NOT_EARLIER_THAN("PNET"),
	PICKUP_NOT_LATER_THAN("PNLT"),
	DELIVER_NOT_EARLIER_THAN("DNET"),
	DELIVER_NOT_LATER_THAN("DNLT");
	
	String timeFrameRequirement;

	TimeFrameRequirements(String requirement) {
		timeFrameRequirement = requirement;
	}

}

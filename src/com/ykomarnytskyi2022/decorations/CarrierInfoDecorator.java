package com.ykomarnytskyi2022.decorations;

public class CarrierInfoDecorator {
	
	
	
	public static String formatCarriersInfo(String dispPhone, String ext, String drvCell, String ETA ) {
		return String.format(" Disp. phone : %s ext. %s "
				+ "\n Driver's cell: %s "
				+ "\n ETA : %s", 
				dispPhone, ext, drvCell, ETA);
	}
	
	private static String[] parseCarrierDataFromStr(String carrierData) {
		String[] result = new String[4];
		
		return result;
	}
	

	
	public static void main(String[] args) {
		System.out.println(formatCarriersInfo("12345678909", "1234", "12345678909", "12")); 
	}
	
}

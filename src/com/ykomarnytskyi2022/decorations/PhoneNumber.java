package com.ykomarnytskyi2022.decorations;

public class PhoneNumber {

	private static final byte PHONE_NUMB_WITHOUT_CODE = 10;
	private static final String PHONE_CODE_USA = "+1";
	
	
	
	private String currentPhNumb = null;
	private String extention = null;
	
	public PhoneNumber(String phNum) {
		currentPhNumb = phNum;
	}
	
	public static String formatDriversCell(String drvCell) {
		return ((byte)drvCell.trim().length() != PHONE_NUMB_WITHOUT_CODE) ? "Incorrect length of driver's phone # "
				: new StringBuilder(drvCell.trim()).insert(0, "1").toString();
	}
	
	public static final boolean validetePhoneNumb(String phone) {
		return ((phone.startsWith(PHONE_CODE_USA) && phone.length()-1 == 11)
			|| phone.length()-1 == 9)? true : false;
	}
	private static String prettifyPhoneN(String phoneN, char separator) {
		StringBuilder result = new StringBuilder((phoneN.startsWith(PHONE_CODE_USA)? phoneN.substring(2) : phoneN));
		result.insert(3, separator).insert(7, separator);
		return result.toString();
	}
	
	public static void main(String[] args) {

	}

}

package Banking.Input;

import java.util.Scanner;

/**
 * Helper static input class to deal with input and input validity checks
 * @author Amir
 *
 */
public class Input {
	public static Scanner scanner = new Scanner(System.in);
	
	private static String numberRegex = ".*[0-9].*";
	private static String charRegex = ".*[a-zA-Z].*";
	private static String onlyNumbersAndLettersRegex = "[a-zA-Z0-9]*";
	private static String onlyLettersRegex = "[a-zA-Z]*";
	private static String onlyNumbersRegex = "[0-9]*";
	
	public static String inputString;
	
	protected static int validityMinLength = 0;
	protected static int validityMaxLength = 0;
	protected static boolean validityCheckLength = false;
	protected static boolean validityMustContainNumber = false;
	protected static boolean validityMustContainLetter = false;
	protected static boolean validityMustContainOnlyNumbers = false;
	protected static boolean validityMustContainOnlyLetters = false;
	
	protected static String messageEnterInput = null;
	protected static String messageInvalidInput = null;
	
	public static void clearAllFlags() {
		validityMinLength = 0;
		validityMaxLength = 0;
		validityCheckLength = false;
		validityMustContainNumber = false;
		validityMustContainLetter = false;
		validityMustContainOnlyNumbers = false;
		validityMustContainOnlyLetters = false;
	}
	
	public static void clearMessages() {
		messageEnterInput = null;
		messageInvalidInput = null;
	}
	
	// setters 
	public static void setMaxLength(int len) {
		validityMaxLength = len;
	}
	
	public static void setMinLength(int len) {
		validityMinLength = len;
	}
	
	public static void setFlagCheckLength(boolean set) {
		validityCheckLength = set;
	}
	
	public static void setMessageEnterInput(String str) {
		messageEnterInput = str;
	}
	
	public static void setMessageInvalidInput(String str) {
		messageInvalidInput = str;
	}
	
	public static void setFlagOnlyNumbers(boolean set) {
		validityMustContainOnlyNumbers = set;
	}
	
	// validity checks
	public static boolean isValidInputLength(String str) {
		return (str.length() >= validityMinLength) && (str.length() <= validityMaxLength);
	}
	
	public static boolean isValidInputLength(String str, int minLen, int maxLen){
		return (str.length() >= minLen) && (str.length() <= maxLen);
	}
	
	public static boolean isValidInputLength(String str, int maxLen){
		return (str.length() >= 0) && (str.length() <= maxLen);
	}

	public static boolean isValidInputMustContainNumberAndLetter(String str) {
		//if ( (!str.matches(numberRegex)) || (!str.toLowerCase().matches(charRegex)) )
		return str.matches(numberRegex) && str.matches(charRegex);
	}

	public static boolean isValidInputMustContainNumberAndLetter(String str, int minLen, int maxLen){
		return isValidInputLength(str, minLen, maxLen) && isValidInputMustContainNumberAndLetter(str);
	}
	
	public static boolean isValidInputMustContainOnlyNumbersAndLetters(String str) {		
		return str.matches(onlyNumbersAndLettersRegex);
	}
	
	public static boolean isValidInputMustContainOnlyNumbersAndLetters(String str, int minLen, int maxLen){
		return isValidInputLength(str, minLen, maxLen) && str.matches(onlyNumbersAndLettersRegex);
	}
	
	public static boolean isValidInputMustContainOnlyNumbers(String str) {
		return str.matches(onlyNumbersRegex);
	}
	
	public static boolean isValidInputMustContainOnlyNumbers(String str, int minLen, int maxLen) {
		return isValidInputLength(str, minLen, maxLen) && str.matches(onlyNumbersRegex);
	}
	
	public static boolean isValidInputMustContainOnlyLetters(String str) {
		return str.matches(onlyLettersRegex);
	}
	
	public static boolean isValidInputMustContainOnlyLetters(String str, int minLen, int maxLen){
		return isValidInputLength(str, minLen, maxLen) && str.matches(onlyLettersRegex);
	}
	
	/**
	 * Gets string from keyboard and remembers it within 'inputString' inside the class
	 * Also displays message in case messageEnterInput is set
	 * @return new string
	 */
	public static String getInput() {
		String newString = new String();
		if (messageEnterInput!= null) System.out.println(messageEnterInput);
		
		newString = scanner.nextLine();
		
		// remember string in class too
		inputString = newString;
		
		return newString;
	}
	
	public static String getInputUntilValid() {
		String str = null;
		boolean isValid = false;
		boolean isFirstIteration = true;
		
		if (messageEnterInput!= null) System.out.println(messageEnterInput);
				
		while (!isValid) {
			
			if (!isValid && !isFirstIteration && messageInvalidInput!=null) System.out.println(messageInvalidInput);
			
			isFirstIteration = false;
			
			str = scanner.nextLine();
			
			if (validityCheckLength) {
				isValid = isValidInputLength(str);
				if (!isValid) continue;
			}
			
			if (validityMustContainOnlyNumbers) {
				isValid = isValidInputMustContainOnlyNumbers(str);
				if (!isValid) continue;
			}
		}
		
		return str;
	}
	
	
	/**
	 * Gets input until valid, using class validity flags
	 * @return String
	 */
	/*
	public static String getInputUntilValid() {
		String newString = new String();
		if (messageEnterInput!= null) System.out.println(messageEnterInput);
		
		newString = scanner.nextLine();
		
		// remember string in class too
		inputString = newString;
		
		return newString;
		
	}
	
	*/
	/*
	public boolean checkValidityAccordingToFlags(String str) {
		boolean result = true;
		
		if (validityCheckLength) result = isValidInputLength(str, validityMinLength, validityMinLength);
		if (result == false) return false;
		
		if ((validityMustContainNumber)&&(validityMustContainLetter)) result = isValidInputMustContainNumberAndLetter(str); 
		if (result == false) return false;
		
		if (validityMustContainOnlyNumber) result = isValidInput
		validityMustContainLetter = false;
		validityMustContainOnlyNumber = false;
		validityMustContainOnlyLetter = false;
		
		return result;
	}*/
}

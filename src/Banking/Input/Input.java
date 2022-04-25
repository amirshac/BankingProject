package Banking.Input;

import java.time.LocalDate;
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
	
	public static void clear() {
		clearAllFlags();
		clearMessages();
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
	
	public static void setFlagOnlyLetters(boolean set) {
		validityMustContainOnlyLetters = set;
	}
	
	public static void setFlagMustContainNumber(boolean set) {
		validityMustContainNumber = set;
	}
	
	public static void setFlagMustContainLetter(boolean set) {
		validityMustContainLetter = set;
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
	
	/**
	 * Keep getting input over and over again until input is valid according to class flags
	 * @return String
	 */
	public static String getInputUntilValid() {
		String str = null;
		boolean isValid = false;
		boolean isFirstIteration = true;
		
		if (messageEnterInput!= null) System.out.println(messageEnterInput);
				
		while (!isValid) {
			
			if (!isValid && !isFirstIteration && messageInvalidInput!=null) System.out.println(messageInvalidInput);
			
			isFirstIteration = false;
			
			str = scanner.nextLine();
			
			// length check
			if (validityCheckLength) {
				isValid = isValidInputLength(str);
				if (!isValid) continue;
			}
			
			// must contain numbers and must contain letters
			if (validityMustContainOnlyNumbers && validityMustContainOnlyLetters) {
				isValid = isValidInputMustContainOnlyNumbersAndLetters(str);
				if (!isValid) continue;
			}
			
			// must contain numbers but not letters
			if (validityMustContainOnlyNumbers && !validityMustContainOnlyLetters) {
				isValid = isValidInputMustContainOnlyNumbers(str);
				if (!isValid) continue;
			}
			
			// must contain letters but not numbers
			if (validityMustContainOnlyLetters && !validityMustContainOnlyNumbers) {
				isValid = isValidInputMustContainOnlyLetters(str);
				if (!isValid) continue;
			}
			
			// must contain atleast a number and atleast a letter
			if (validityMustContainNumber && validityMustContainLetter) {
				isValid =isValidInputMustContainNumberAndLetter(str);
				if (!isValid) continue;
			}
		}
		
		return str;
	}
	
	// special input case for username
	public static String getUserNameUntilValid() {
		final int USERNAME_MIN_LEN = 4;
		final int USERNAME_MAX_LEN = 20;
		
		String userName;
		clear();
		setMessageEnterInput("Enter Username:");
		setMessageInvalidInput("Invalid username - Must be only letters and numbers, length of 4-20");
		setFlagCheckLength(true);
		setMinLength(USERNAME_MIN_LEN);
		setMaxLength(USERNAME_MAX_LEN);
		setFlagOnlyNumbers(true);
		setFlagOnlyLetters(true);
		userName = getInputUntilValid();
		userName = userName.toLowerCase();
		return userName;
	}
	
	// special input case for password
	public static String getPasswordUntilValid() {
		final int PASSWORD_MIN_LEN = 4;
		final int PASSWORD_MAX_LEN = 8;
		
		String password;
		clear();
		setMessageEnterInput("Enter Password:");
		setMessageInvalidInput("Invalid password - Must have a letter and a number, length of 4-8");
		setFlagCheckLength(true);
		setMinLength(PASSWORD_MIN_LEN);
		setMaxLength(PASSWORD_MAX_LEN);
		setFlagMustContainLetter(true);
		setFlagMustContainNumber(true);
		password = getInputUntilValid();
		return password;
	}
	/**
	 * Asks for numerical inputs to construct a localdate object
	 * @return
	 */
	public static LocalDate getDate() {
		LocalDate date = null;
		int day, month, year;
		String str;
		
		System.out.println("Input date - day:");
		str = scanner.nextLine();
		day = Integer.parseInt(str);
		
		System.out.println("Input date - month:");
		str = scanner.nextLine();
		month = Integer.parseInt(str);

		System.out.println("Input date - year:");
		str = scanner.nextLine();
		year = Integer.parseInt(str);

		date = LocalDate.of(year, month, day);
		
		return date;
	}
	
	/**
	 * Keeps getting input until number >= min 
	 * @param min - minimum value of number
	 * @return double variable >= min
	 */
	public static double getDoubleUntilValidMin(double min) {
		return getDoubleUntilValid(min, Double.MAX_VALUE);
	}
	
	public static double getDoubleUntilValidMax(double max) {
		return getDoubleUntilValid(Double.MIN_VALUE, max);
	}
	
	public static double getDoubleUntilValid(double min, double max) {
		String str;
		boolean isValid = false;
		double number = 0.0;
		
		while (!isValid) {
			if (messageEnterInput!= null) System.out.println(messageEnterInput);
			str = scanner.nextLine();
			try {
				number = Double.parseDouble(str);
			}
			catch (Exception e) {
				continue;
			}
			
			if ((number < min) || (number > max)) System.out.println(messageInvalidInput);
			else isValid = true;
		}
		
		return number;
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

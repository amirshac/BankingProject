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
	private static String onlyNumbersRegex = "[0-9]";
	
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
	
}

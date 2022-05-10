package Banking.Users;

import Banking.Input.Input;

// need to implement validity checks
public class Credentials {
	private String userName;
	private String password;
	
	public Credentials() {
		userName = null;
		password = null;
	}

	public Credentials(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "<Credentials>[userName=" + userName + ", password=" + password + "]</Credentials>";
	}
	
	/**
	 * Checks if a string meets the password requirements
	 * 4-8 characters must contain a number and a letter
	 * @param userName
	 * @return true/false
	 */
	public static boolean isValidPassword(String str) {
		final int MIN_LENGTH = 4;
		final int MAX_LENGTH = 8;
		
		boolean result = Input.isValidInputMustContainNumberAndLetter(str, MIN_LENGTH, MAX_LENGTH);
		
		return result;
	}
	
	/**
	 * Checks if a string meets the username requirements
	 * 4-20 characters must contain ONLY numbers and letters
	 * @param userName
	 * @return true/false
	 */
	public static boolean isValidUsername(String str) {
		final int MIN_LENGTH = 4;
		final int MAX_LENGTH = 20;
		
		boolean result = Input.isValidInputMustContainOnlyNumbersAndLetters(str, MIN_LENGTH, MAX_LENGTH);
		
		return result;
	}
	
}

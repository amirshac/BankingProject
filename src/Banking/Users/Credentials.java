package Banking.Users;
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
	 * 4-8 characters must contain a digit and a letter
	 * @param userName
	 * @return true/false
	 */
	public static boolean isValidPassword(String str) {
		final int MIN_LENGTH = 4;
		final int MAX_LENGTH = 8;
		String numberRegex = ".*[0-9].*";
		String charRegex = ".*[a-z].*";
				
		// required length
		if ( (str.length()<MIN_LENGTH) || (str.length()>MAX_LENGTH) )
			return false;
		
		// if contains no numbers - false
		if (!str.matches(numberRegex))
			return false;
		
		// if contains no letters - false
		if (!str.toLowerCase().matches(charRegex))
			return false;
		
		return true;
	}
	
	/**
	 * Checks if a string meets the username requirements
	 * 4-20 characters must contain ONLY digits and numbers
	 * @param userName
	 * @return true/false
	 */
	public static boolean isValidUsername(String str) {
		final int MIN_LENGTH = 4;
		final int MAX_LENGTH = 20;
		String onlyNumbersAndLettersRegex = "[a-zA-Z0-9]*";
				
		// required length
		if ( (str.length()<MIN_LENGTH) || (str.length()>MAX_LENGTH) )
			return false;
		
		// must contain ONLY digits and letters
		if ( !str.matches(onlyNumbersAndLettersRegex) )
			return false;			
		
		return true;
	}
	
}

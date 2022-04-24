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
	
	
}

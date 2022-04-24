package Banking.Menu;

public class MenuWelcome extends Menu{
	public MenuWelcome() {
		super("Welcome to ADJB banking system:");
		this.optionList.add("Log in");
		this.optionList.add("Create a new account");
		this.optionList.add("Exit");
	}
}

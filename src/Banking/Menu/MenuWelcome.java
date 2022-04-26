package Banking.Menu;


public class MenuWelcome extends Menu{
	public MenuWelcome() {
		super("Welcome to ADJB banking system:");
		addOption("Log in", "L");
		addOption("Create a new account", "C");
		addOption("Quit", "Q");
		addOption("Print DataBase<DEBUG>", "P");
	}
}

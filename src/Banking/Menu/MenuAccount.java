package Banking.Menu;

public class MenuAccount extends Menu {
	public MenuAccount() {
		super("Operations Menu:");
		addOption("Check Balance", "0");
		addOption("Produce Activity Report", "1");
		addOption("Deposit", "2");
		addOption("withdraw", "3");
		addOption("Transfer money to another account", "4");
		addOption("Get a loan", "5");
		addOption("Log out","9");
	}

}

package Banking.Menu;

public class MenuAccount extends Menu {
	public MenuAccount() {
		super("Operations Menu:");
		addOption("Check Balance", "B");
		addOption("Deposit", "D");
		addOption("withdraw", "W");
		addOption("Transfer money to another account", "T");
		addOption("Get a loan", "L");
		addOption("Pay Bills", "P");
		addOption("Activity Report", "A");
		addOption("Log out","Q");
	}
	
}
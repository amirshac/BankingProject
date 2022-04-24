package Banking.Menu;

public class MenuAccount extends Menu {
	public MenuAccount() {
		super("Bank Account Operations Menu:");
		this.optionList.add("Log out");
		this.optionList.add("Check Balance");
		this.optionList.add("Produce Activity Report");
		this.optionList.add("Deposit");
		this.optionList.add("withdraw");
		this.optionList.add("Transfer money to another account");
		this.optionList.add("Get a loan");
	}

}

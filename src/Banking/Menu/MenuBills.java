package Banking.Menu;

public class MenuBills extends Menu {
	public MenuBills() {
		super("Bills Menu:");
		addOption("Water Bill", "W");
		addOption("Electric Bill", "E");
		addOption("Bank (Repay Loan)", "B");
		addOption("Previous menu", "Q");
	}
	
	protected void handleChoice() {
		
	}
}



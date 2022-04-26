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
		switch(choice) {
		case "W":
			currentUser.payWaterBill();
			break;
			
		case "E":
			currentUser.payElectricBill();
			break;
			
		case "B":
			currentUser.payBank();
			break;
		
		case "Q":
			break;
			
		default:
			break;
		}
		
	}
}



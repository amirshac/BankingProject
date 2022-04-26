package Banking.Menu;

import Banking.AppEngine.AppState;

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

	/**
	 * Plays 'account menu' and activates functions according to user input
	 */
	protected void handleChoice(){
		
		switch(choice) {
		case "B":
			currentUser.checkBalance();
			break;
			
		case "D":
			currentUser.makeDeposit();
			break;
			
		case "W":
			currentUser.withdraw();
			break;
		
		case "A":
			currentUser.reportActivity();
			break;
			
		case "T":
			currentUser.transferFunds();
			break;
			
		case "L":
			currentUser.getLoan();
			break;
			
		case "P":
			currentUser.UI.menuBills.play();
			break;
			
		case "Q":
			logOut();
			break;
			
		default:
			break;
		}
	}		

	private void logOut() {
		if (currentUser == null)
			System.out.println("logout() No user to log out");
		
		System.out.println("Logging out...\n");
				
		System.out.println(currentUser.getUserName() + " logged out.\n");
		
		currentUser = null;
	}
}
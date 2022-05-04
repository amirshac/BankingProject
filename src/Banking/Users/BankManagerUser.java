package Banking.Users;

import Banking.Account.Account;
import Banking.Input.Input;

public class BankManagerUser extends AccountUser{
	
	public BankManagerUser(Person person, double monthlyIncome, Account account, Credentials credentials) {
		super(person, monthlyIncome, account, credentials);
	}
	
	@Override
	public void playMainMenu() {
		do {
			ui.menuAccountManager.play();
			handleMainMenu();
		}while (!ui.menuAccountManager.getChoice().equals("Q") );
	}
	
	@Override
	protected void handleMainMenu() {
		switch(ui.menuAccountManager.getChoice()) {
		case "B":
			checkBalance();
			break;
			
		case "D":
			makeDeposit();
			break;
			
		case "W":
			withdraw();
			break;
		
		case "A":
			reportActivity();
			break;
			
		case "T":
			transferFunds();
			break;
			
		case "L":
			getLoan();
			break;
			
		case "P":
			playBillsScreen();
			break;
			
		case "Q":
			logOut();
			break;
			
		case "U":
			unlockAccounts();
			break;
			
		default:
			break;
		}	
	}
	
	@Override
	protected void getLoan() {
		System.out.println("Manager information");
		System.out.println("---------------------");
		System.out.println(account);
		account.printActivityLog();
		Input.pressAnyKeyToContinue();
	}
	
	protected void unlockAccounts() {
		System.out.println("not yet implemented");
		Input.pressAnyKeyToContinue();
	}
	
}

package Banking.Users;

import java.util.LinkedList;

import Banking.Account.Account;
import Banking.Input.Input;

public class BankManagerUser extends AccountUser{
	
	protected LinkedList<AccountUser> lockedAccounts;
	
	public BankManagerUser(Person person, double monthlyIncome, Account account, Credentials credentials) {
		super(person, monthlyIncome, account, credentials);
		lockedAccounts = new LinkedList<AccountUser>();
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
	
	/**
	 * Adds user to manager's locked account list
	 * @param accountUser
	 */
	public void addToLockedAccounts(AccountUser accountUser) {
		accountUser.lockAccount();
		lockedAccounts.add(accountUser);
	}
	
	protected void unlockAccounts() {
		if (lockedAccounts.isEmpty()) {
			System.out.println("No accounts to unlock");
			Input.pressAnyKeyToContinue();
			return;
		}
		
		System.out.println("The following accounts are locked");
		for (AccountUser accountUser : lockedAccounts) {
			System.out.println(accountUser);
		}
		
		System.out.println("Press any key to unlock all accounts");
		Input.pressAnyKeyToContinue();
		
		for (AccountUser accountUser : lockedAccounts) {
			accountUser.unlockAccount();
		}
		
		lockedAccounts.clear();
	}
	
}

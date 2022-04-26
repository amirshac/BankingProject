package Banking.Users;

import Banking.Account.Account;
import Banking.Input.Input;

public class BankManagerUser extends AccountUser{
	
	public BankManagerUser(Person person, double monthlyIncome, Account account, Credentials credentials) {
		super(person, monthlyIncome, account, credentials);
	}
	
	@Override
	public void getLoan() {
		System.out.println("Manager information");
		System.out.println("---------------------");
		System.out.println(account);
		account.printActivityLog();
		Input.pressAnyKeyToContinue();
	}
}

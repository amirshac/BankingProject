package Banking.Users;

import Banking.Account.Account;
import Banking.Input.Input;

public class BankManagerUser extends AccountUser{
	// need to implement
	
	public BankManagerUser(Person person, double monthlyIncome, Account account, Credentials credentials) {
		super(person, monthlyIncome, account, credentials);
	}
	
	//public BankManagerUser(Person person){
	//	super(person);
//	}
	
	@Override
	public void getLoan() {
		System.out.println(account);
		account.printActivityLog();
		Input.pressAnyKeyToContinue();
	}
}

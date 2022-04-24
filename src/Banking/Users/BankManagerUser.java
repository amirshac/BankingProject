package Banking.Users;

import Banking.Account.Account;

public class BankManagerUser extends AccountUser{
	// need to implement
	
	public BankManagerUser(Person person, double monthlyIncome, Account account) {
		super(person);
		this.account = account;
		this.monthlyIncome = monthlyIncome;
	}
	
	public BankManagerUser(Person person){
		super(person);
	}

}

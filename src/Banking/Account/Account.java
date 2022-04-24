package Banking.Account;

public class Account {
	private static final double DEFAULT_BALANCE = 0f;
	private static final AccountProperties DEFAULT_ACCOUNT_PROPERTIES = AccountProperties.BRONZE;
	
	protected double balance;
	protected AccountProperties accountProperties;
	
	// constructors 
	public Account() {
		this(DEFAULT_BALANCE);
	}
	
	public Account(double balance) {
		this(balance, DEFAULT_ACCOUNT_PROPERTIES);
	}

	public Account(double balance, AccountProperties accountProperties) {
		super();
		this.balance = balance;
		this.accountProperties = accountProperties;
	}


	// setters getters
	
	public double getBalance() {
		return balance;
	}

	protected void setBalance(double balance) {
		this.balance = balance;
	}

	public AccountProperties getAccountProperties() {
		return accountProperties;
	}

	public void setAccountProperties(AccountProperties accountProperties) {
		this.accountProperties = accountProperties;
	}
	

	@Override
	public String toString() {
		return "<Account> [balance=" + balance + ", accountProperties=" + accountProperties + "]";
	}
	
	
}

package Banking.Users;
import java.time.LocalDate;

import Banking.Account.Account;
import Banking.Input.Input;

public class AccountUser extends Person {
	private static final double DEFAULT_MONTHLY_INCOME = 0f;
	
	protected double monthlyIncome;
	protected Account account;
	protected Credentials credentials;
	protected boolean isLocked = false;
	
	// constructors 
	public AccountUser(String firstName, String lastName, String phoneNumber, LocalDate birthDate,
			double monthlyIncome, Account account) {
		super(firstName, lastName, phoneNumber, birthDate);
		this.monthlyIncome = monthlyIncome;
		this.account = account;	
	}

	public AccountUser(Person person, double monthlyIncome, Account account, Credentials credentials) {
		super(person);
		this.account = account;
		this.monthlyIncome = monthlyIncome;
		this.credentials = credentials;
	}
	
	public AccountUser(Person person, double monthlyIncome, Account account) {
		super(person);
		this.account = account;
		this.monthlyIncome = monthlyIncome;
	}

	public AccountUser(Person person, double monthlyIncome) {
		super(person);
		this.account = null;
		this.monthlyIncome = monthlyIncome;
	}

	public AccountUser(Person person, Account account) {
		super(person);
		this.account = account;
		this.monthlyIncome = DEFAULT_MONTHLY_INCOME;
	}
	
	public AccountUser(Person person) {
		super(person);
		this.account = null;
		this.monthlyIncome = DEFAULT_MONTHLY_INCOME;
	}
	
	public AccountUser() {
		super();
		this.account = null;
		this.monthlyIncome = DEFAULT_MONTHLY_INCOME;	
	}

	
	// setters getters
	public double getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(double monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setCredentials(Credentials cred) {
		this.credentials = cred;
	}
	
	public void setCredentials(String user, String password) {
		this.credentials = new Credentials(user, password);
	}
	
	public Credentials getCredentials() {
		return this.credentials;
	}
	
	public String getUserName() {
		if (this.credentials == null)
				return null;
		
		return this.credentials.getUserName();
	}
	
	public String getPassword() {
		if (this.credentials == null)
			return null;
		
		return this.credentials.getPassword();
	}
	
	public boolean isLocked() {
		return this.isLocked;
	}
	
	@Override
	public String toString() {
		String msg = super.toString();
		msg += "\n<AccountUser>\n[monthlyIncome = " + monthlyIncome 
				+ "\naccount = " + account  
				+ "\ncredentials = " + credentials + "]\n"
				+ "</AccountUser>";
		return msg;
	}
	
	// METHODS
	public void printAccountActivityLog() {
		this.account.printActivityLog();
	}
	
	/**
	 * displays account balance
	 */
	public void checkBalance() {
		double balance = this.account.getBalance();
		System.out.println("Account balance: " + balance);
	}
	
	/**
	 * Makes a deposit
	 */
	public void makeDeposit() {
		final float MIN_DEPOSIT = 0f;
		double deposit = 0f;
		
		System.out.println("Deposit:");
		
		// TODO: implement 4 digit code for deposit
		
		Input.clear();
		Input.setMessageEnterInput("Enter deposit amount:");
		Input.setMessageInvalidInput("Amount must be alteast 0");
		
		deposit = Input.getDoubleUntilValidMin(MIN_DEPOSIT);
		account.deposit(deposit);
		
		checkBalance();
	}
	
	public void lockAccount() {
		this.isLocked = true;
	}
	
	public void unlockAccount() {
		this.isLocked = false;
	}
	
	/**
	 * withdraw money
	 */
	public void withdraw() {
		double maxWithdrawal = account.getAccountProperties().GetMaxWithdrawalDaily();
		double amount = 0;
		
		// TODO: current daily withdrawal should be addressed and counted
		Input.clear();
		Input.setMessageEnterInput("Enter withdrawal amount:");
		Input.setMessageInvalidInput("Can't withdraw more than your daily amount: " + maxWithdrawal);
		
		amount = Input.getDoubleUntilValid(0, maxWithdrawal);
		
		account.withdraw(amount);
		checkBalance();
	}
}

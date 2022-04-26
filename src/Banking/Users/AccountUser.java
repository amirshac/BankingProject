package Banking.Users;
import java.time.LocalDate;
import java.time.LocalDateTime;

import Banking.Account.Account;
import Banking.ActivityLog.ActivityName;
import Banking.DataBase.DataBase;
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
		double balance = account.getBalance();
		System.out.println(account.getAccountProperties()+ " account balance: " + balance);
		
		Input.pressAnyKeyToContinue();
	}
	
	/**
	 * Makes a deposit
	 */
	public void makeDeposit() {
		final float MIN_DEPOSIT = 0f;
		double amount = 0f;
		
		System.out.println("Deposit:");
		
		// TODO: implement 4 digit code for deposit
		
		Input.clear();
		Input.setMessageEnterInput("Enter deposit amount:");
		Input.setMessageInvalidInput("Amount must be alteast 0");
		
		amount = Input.getDoubleUntilValidMin(MIN_DEPOSIT);
		account.deposit(amount);
		
		account.addActivityLog(ActivityName.DEPOSIT, amount);
		
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
		
		account.addActivityLog(ActivityName.WITHDRAWAL, (-1)*(amount));
		
		checkBalance();
}
	
	public void reportActivity() {
		account.printActivityLog();
		Input.pressAnyKeyToContinue();
	}
	
	public void transferFunds() {
		final double TRANSFER_MAX = 2000;
		double amount = 0;
		String phoneNumber = null;
		int phoneNumberAccountIndex = 0;
		AccountUser receiverUser = null;
		
		phoneNumber = Input.getPhoneNumberUntilValid();
		phoneNumberAccountIndex = DataBase.getIndexOfPhoneNumber(phoneNumber);
		
		if (phoneNumberAccountIndex == -1) {
			System.out.println("Account not found in database, aborting..");
			Input.pressAnyKeyToContinue();
			return;
		}
		
		receiverUser = DataBase.getAccountUserUsingIndex(phoneNumberAccountIndex);
		
		System.out.println(receiverUser.getFullName() + " found!");
		
		Input.clear();
		Input.setMessageEnterInput("Enter transfer amount:");
		Input.setMessageInvalidInput("Can't transfer more than " + TRANSFER_MAX);
		
		amount = Input.getDoubleUntilValid(0, TRANSFER_MAX);
		
		account.withdraw(amount);
		receiverUser.account.deposit(amount);
		
		account.addActivityLog(ActivityName.TRANSFER, -amount, "Transfered to " + phoneNumber);
		account.addActivityLog(ActivityName.RECEIVE, amount, "Received from " + this.getPhoneNumber());
		
		System.out.println("Transfer complete");
		checkBalance();
	}
}

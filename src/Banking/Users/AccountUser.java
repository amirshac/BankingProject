package Banking.Users;
import java.time.LocalDate;

import Banking.Account.Account;
import Banking.ActivityLog.ActivityName;
import Banking.DataBase.DataBase;
import Banking.Input.Input;
import Banking.Loan.Loan;

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
		
		executeDeposit(amount);
		
		checkBalance();
	}
	
	protected void executeDeposit(double amount) {
		account.deposit(amount);
		account.addActivityLog(ActivityName.DEPOSIT, amount);
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
		
		if ( (account.getDailyWithdrawal() + amount) > maxWithdrawal ){
			String msg = "Your current daily withdrawal is " +account.getDailyWithdrawal()+
					" can't withdraw more than your daily amount: " + maxWithdrawal;
			System.out.println(msg);
			Input.pressAnyKeyToContinue();
			return;
		}

		executeWithdraw(amount);
		
		checkBalance();
	}
	
	protected void executeWithdraw(double amount) {
		account.setDailyWithdrawl(account.getDailyWithdrawal() + amount);
		account.withdraw(amount);
		account.addActivityLog(ActivityName.WITHDRAWAL, (-1)*(amount));
	}
	
	
	// TODO: elaborate on reportactivity - sorted list, input time
	public void reportActivity() {
		account.printActivityLog();
		checkBalance();
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
		
		executeTransferFunds(amount, receiverUser);
		
		System.out.println("Transfer complete");
		checkBalance();
	}
	
	protected void executeTransferFunds(double amount, AccountUser receiverUser) {
		account.withdraw(amount);
		receiverUser.account.deposit(amount);
		
		account.addActivityLog(ActivityName.TRANSFER, (-1)*amount, "Transfered to " + phoneNumber);
		receiverUser.account.addActivityLog(ActivityName.RECEIVE, amount, "Received from " + this.getPhoneNumber());
	}
		
	// TODO: implement bank manager account to take loan from
	public void getLoan() {
		final int MAX_PAYMENTS = 60;
		final int MIN_PAYMENTS = 1;
		final double MAX_INPUT = 9999999;
		
		double maxLoan = account.getAccountProperties().getMaxLoan();
		double amount;
		int payments;
		
		if (account.hasLoan()) {
			System.out.println("Account already has a loan. Aborting...");
			Input.pressAnyKeyToContinue();
			return;
		}
		
		Input.clear();
		Input.setMessageEnterInput("Enter loan amount:");
		Input.setMessageInvalidInput("Loan must be positive number");
		
		amount = Input.getPositiveDoubleUntilValidMax(MAX_INPUT);
		
		if (amount == 0) {
			System.out.println("You have chosen 0, aborting loan process");
			Input.pressAnyKeyToContinue();
			return;
		}
		
		if (amount > maxLoan) {
			System.out.println(account.getAccountProperties()+" accounts can't loan more than " + maxLoan + " aborting..");
			Input.pressAnyKeyToContinue();
			return;
		}
		
		
		Input.clear();
		Input.setMessageEnterInput("Enter payments:");
		Input.setMessageInvalidInput("payments must be atleast " + MIN_PAYMENTS);
		
		payments = (int)Input.getDoubleUntilValidMin(MIN_PAYMENTS);
		
		if ( payments < MIN_PAYMENTS || payments > MAX_PAYMENTS) {
			System.out.println("Loan can't be more than " + MAX_PAYMENTS + " payments. Aborting...");
			Input.pressAnyKeyToContinue();
			return;
		}
		
		//monthlyReturn = amount / payments;
		Loan loan = new Loan(amount, account.getAccountProperties().getInterest(), payments);
		
		System.out.println(loan);
		
		executeLoan(loan);
		
		checkBalance();
	}
	
	protected void executeLoan(Loan loan) {
		AccountUser bankManager = DataBase.getBankAccountUser();
		
		account.deposit(loan.getAmount());
		bankManager.account.withdraw(loan.getAmount());
		
		account.addActivityLog(ActivityName.LOAN, loan.getAmount());
		bankManager.account.addActivityLog(ActivityName.LOAN, loan.getAmount(), getPhoneNumber()+" account took a loan");
		
		account.setLoan(loan);
	}
}

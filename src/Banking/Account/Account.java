package Banking.Account;

import java.time.LocalDateTime;

import Banking.ActivityLog.ActivityLog;
import Banking.ActivityLog.ActivityName;
import Banking.Loan.Loan;

public class Account {
	private static final double DEFAULT_BALANCE = 0f;
	private static final AccountProperties DEFAULT_ACCOUNT_PROPERTIES = AccountProperties.BRONZE;
	private static final int ACTIVITY_LOG_SIZE = 100;
	
	protected double balance;
	protected AccountProperties accountProperties;
	protected Loan loan;
	protected double dailyWithdrawal; 
	
	protected ActivityLog[] activityLogs;
	private int activityLogIndex;

	
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
		setAccountProperties(accountProperties);
		activityLogs = new ActivityLog[ACTIVITY_LOG_SIZE];
		activityLogIndex = 0;
		loan = null;
		dailyWithdrawal = 0;
	}

	// setters getters
	
	public double getBalance() {
		return balance;
	}

	protected void setBalance(double balance) {
		this.balance = balance;
	}

	public void setAccountProperties(AccountProperties accountProperties) {
		this.accountProperties = accountProperties;
	}

	public AccountProperties getAccountProperties() {
		return accountProperties;
	}
	
	@Override
	public String toString() {
		return "<Account>[balance=" + balance + ", accountProperties=" + accountProperties + "]</Account>";
	}
	
	// METHODS
	
	public void deposit(double amount) {
		balance += amount;
	}
	
	public void withdraw(double amount) {
		balance -= amount;
	}
	
	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	
	public boolean hasLoan() {
		return (loan!=null);
	}
	
	public void setDailyWithdrawl(double amount) {
		if (amount < 0) return;
		this.dailyWithdrawal = amount;
	}
	
	public double getDailyWithdrawal() {
		return this.dailyWithdrawal;
	}
	
	/**
	 * Adds activitylog to activity history array
	 * @param activityLog
	 */
	public void addActivityLog(ActivityLog activityLog) {	
		if (activityLogIndex >= ACTIVITY_LOG_SIZE) {
			System.out.println("Activity log full, can't add logs");
			return;
		}
		
		activityLogs[activityLogIndex] = activityLog;
		activityLogIndex ++;
	}
	
	public void addActivityLog(ActivityName name, double balanceChange, LocalDateTime timeStamp, String info) {
		ActivityLog log = new ActivityLog(name, balanceChange, timeStamp, info);
		addActivityLog(log);
	}
	
	public void addActivityLog(ActivityName name, double balanceChange) {
		addActivityLog(name, balanceChange, LocalDateTime.now(),"");
	}
	
	public void addActivityLog(ActivityName name, double balanceChange, String info) {
		addActivityLog(name, balanceChange, LocalDateTime.now(),info);
	}

	public void printActivityLog() {
		if (activityLogIndex <= 0) {
			System.out.println("No activity log found");
			return;
		}
		
		System.out.println(activityLogIndex + " activities in log:");
		for (int i=0; i<activityLogIndex; i++)
			System.out.println(activityLogs[i]);
	}
	
}

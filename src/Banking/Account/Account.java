package Banking.Account;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import Banking.ActivityLog.ActivityLog;
import Banking.ActivityLog.ActivityName;
import Banking.Loan.Loan;

public class Account {
	private static final double DEFAULT_BALANCE = 0f;
	private static final AccountProperties DEFAULT_ACCOUNT_PROPERTIES = AccountProperties.BRONZE;
	
	protected double balance;
	protected AccountProperties accountProperties;
	protected Loan loan;
	protected double dailyWithdrawal; 
	
	protected LinkedList<ActivityLog> activityLogList;
	
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
		activityLogList = new LinkedList<ActivityLog>();
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
	
	public void depositWithLog(double amount) {
		deposit(amount);
		addActivityLog(ActivityName.DEPOSIT, amount);
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
	
	public Loan getLoan() {
		return loan;
	}
	
	public void setDailyWithdrawl(double amount) {
		if (amount < 0) return;
		this.dailyWithdrawal = amount;
	}
	
	public double getDailyWithdrawal() {
		return this.dailyWithdrawal;
	}
	
	public List<?> getActivityLog(){
		return this.activityLogList;
	}
	
	public void addActivityLog(ActivityLog activityLog) {	
		activityLogList.addLast(activityLog);
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
	
}

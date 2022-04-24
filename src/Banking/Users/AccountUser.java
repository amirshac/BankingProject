package Banking.Users;
import java.time.LocalDate;

import Banking.Account.Account;

public class AccountUser extends Person {
	private static final double DEFAULT_MONTHLY_INCOME = 0f;
	
	protected double monthlyIncome;
	protected Account account;
	protected Credentials credentials;
	
	// constructors 
	public AccountUser(String firstName, String lastName, String phoneNumber, LocalDate birthDate,
			double monthlyIncome, Account account) {
		super(firstName, lastName, phoneNumber, birthDate);
		this.monthlyIncome = monthlyIncome;
		this.account = account;	
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

	protected void setCredentials(Credentials cred) {
		this.credentials = cred;
	}
	
	@Override
	public String toString() {
		String msg = super.toString();
		msg += "\n<AccountUser>[monthlyIncome=" + monthlyIncome + ", account=" + account + "]";
		return msg;
	}
	
}

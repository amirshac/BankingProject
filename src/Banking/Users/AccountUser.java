package Banking.Users;

import Banking.Account.Account;
import Banking.ActivityLog.ActivityName;
import Banking.DataBase.DataBase;
import Banking.Input.Input;
import Banking.Loan.Loan;
import Banking.Menu.UserInterface;
import Banking.Printer.Printer;

public class AccountUser extends Person {	
	protected double monthlyIncome;
	protected Account account;
	protected Credentials credentials;
	protected boolean isLocked = false;
	protected UserInterface ui;
	
	// constructors 

	public AccountUser(Person person, double monthlyIncome, Account account, Credentials credentials) {
		super(person);
		this.account = account;
		this.monthlyIncome = monthlyIncome;
		this.credentials = credentials;
		ui = new UserInterface();
	}
	
	// SETTERS GETTERS
	public Account getAccount() {
		return account;
	}

	
	public void setCredentials(String user, String password) {
		this.credentials = new Credentials(user, password);
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
	
	public void lockAccount() {
		this.isLocked = true;
	}
	
	public void unlockAccount() {
		this.isLocked = false;
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
	
	public void playMainMenu() {
		do {
			ui.menuAccount.play();
			handleMainMenu();
		}while (!ui.menuAccount.getChoice().equals("Q") );
	}
	
	protected void handleMainMenu() {
		switch(ui.menuAccount.getChoice()) {
		case "B":
			checkBalance();
			break;
			
		case "D":
			makeDeposit();
			break;
			
		case "W":
			withdraw();
			break;
		
		case "A":
			reportActivity();
			break;
			
		case "T":
			transferFunds();
			break;
			
		case "L":
			getLoan();
			break;
			
		case "P":
			playBillsScreen();
			break;
			
		case "Q":
			logOut();
			break;
			
		default:
			break;
		}	
	}
	
	protected void logOut() {
		System.out.println("Logging out...\n");
			
		System.out.println(getUserName() + " logged out.\n");
	}
	
	protected void printAccountActivityLog() {
		Printer.print(this.getAccount().getActivityLog());
	}
	
	/**
	 * displays account balance
	 */
	protected void checkBalance() {
		System.out.println(account.getAccountProperties()+ " account balance: " + account.getBalance());
		Input.pressAnyKeyToContinue();
	}
	
	/**
	 * Pays transaction fee to bank manager and updates logs
	 */
	protected void payTransactionFee() {
		AccountUser bankManager = DataBase.getBankAccountUser();
		float fee = account.getAccountProperties().getOperationFee();
		
		account.withdraw(fee);
		bankManager.getAccount().deposit(fee);
		
		account.addActivityLog(ActivityName.FEE, (-1)*fee);
		bankManager.getAccount().addActivityLog(ActivityName.FEE, fee, "Fee from account " + this.phoneNumber);
	}
	
	/**
	 * Makes a deposit
	 */
	protected void makeDeposit() {
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
		payTransactionFee();
	}
	
	/**
	 * withdraw money
	 * 
	 */
	protected void withdraw() {
		double maxWithdrawal = account.getAccountProperties().GetMaxWithdrawalDaily();
		double amount = 0;
		
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
		payTransactionFee();
	}
	
	// TODO: elaborate on reportactivity - sorted list, input time
	protected void reportActivity() {
		Printer.print(this.getAccount().getActivityLog());
		account.printLoan();
		checkBalance();
	}
	
	protected void transferFunds() {
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
		
		payTransactionFee();
	}
		
	// TODO: implement bank manager account to take loan from
	protected void getLoan() {
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
		bankManager.account.addActivityLog(ActivityName.LOAN, (-1)*(loan.getAmount()), getPhoneNumber()+" account took a loan");
		
		account.setLoan(loan);
		
		payTransactionFee();
	}
	
	
	//-----------BILLS MENU SECTION----------------------------------------------
	
	protected void playBillsScreen() {
		do {
			ui.menuBills.play();
			handleBillsMenu();
		}while (!ui.menuBills.getChoice().equals("Q") );
	}
	
	protected void handleBillsMenu() {
		switch(ui.menuBills.getChoice()) {
		case "W":
			payWaterBill();
			break;
			
		case "E":
			payElectricBill();
			break;
			
		case "B":
			payBank();
			break;
		
		case "Q":
			break;
			
		default:
			break;
		}
		
	}
	
	protected void payWaterBill() {
		payBill();
		System.out.println("Paid water bill");
		checkBalance();
	}
	
	protected void payElectricBill() {
		payBill();
		System.out.println("Paid electric bill");
		checkBalance();
	}
	
	//TODO: pay loan
	protected void payBank() {
		System.out.println("not implemented");
		Input.pressAnyKeyToContinue();
	}
	
	protected void payBill() {
		final double MAX_AMOUNT = 5000;
		double amount;
		
		Input.clear();
		Input.setMessageEnterInput("Enter amount:");
		Input.setMessageInvalidInput("Amout must be between 0 and " + MAX_AMOUNT);
		amount = Input.getDoubleUntilValid(0, MAX_AMOUNT);
		
		account.withdraw(amount);
		account.addActivityLog(ActivityName.BILLS, (-1)*amount);
		
		payTransactionFee();
	}

}

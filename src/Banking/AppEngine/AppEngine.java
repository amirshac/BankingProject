package Banking.AppEngine;

import java.time.LocalDate;
import java.time.LocalDateTime;

import Banking.Account.Account;
import Banking.Account.AccountProperties;
import Banking.DataBase.DataBase;
import Banking.Menu.*;
import Banking.Users.*;
import Banking.Input.*;

public class AppEngine {
	protected AppState state;
//	protected AccountUser[] userArr;
	protected AccountUser currentUser;
	protected Menu currentMenu;
	protected Menu welcomeMenu;
	protected Menu accountMenu;
	protected boolean keepGoing;
	
	public AppEngine() {
		initMenus();
		currentUser = null;
		currentMenu = welcomeMenu;
		state = AppState.WELCOME_SCREEN;
		keepGoing = true;
	}
	
	public void initMenus() {
		welcomeMenu = new MenuWelcome();
		accountMenu = new MenuAccount();
	}
	
	public void play() {
		while (keepGoing){
			executeState();
		}
	}
	
	private void executeState() {
		switch(state) {
		case WELCOME_SCREEN:
			handleWelcomeScreen();
			break;
		case LOGGED_IN:
			handleAccountMenu();
			break;
		default:
			handleWelcomeScreen();
			break;
		}
	}
	
	private void handleWelcomeScreen() {
		currentMenu = welcomeMenu;
		currentMenu.play();
		
		switch(currentMenu.getChoice()) {
		case "L":
			handleLoginScreen();
			break;
			
		case "C":
			createAccount();
			break;
			
		case "Q":
			quit();
			break;
			
		case "P":
			DataBase.print();
			break;
				
		default:
			break;
		}
	}
	
	// TODO: user lock mechanism after 3 tries
	public void handleLoginScreen() {
		final String MSG_USERNAME_DOESNT_EXIST = "Username does not exist\n";
		final String MSG_INVALID_CREDENTIALS = "Invalid credentials. Try again\n";
		final int LOCKOUT_TRIES = 3;
		
		String userName;
		String password;
		AccountUser accountUser = null;
		
		int index = 0;
		int tries = 0;
		
		System.out.println("Login:");
	
		userName = Input.getUserNameUntilValid();
		
		// if username not found - go back to main menu
		index = DataBase.getIndexOfUsername(userName);
		if (index == -1) {
			System.out.println(MSG_USERNAME_DOESNT_EXIST);
			state = AppState.WELCOME_SCREEN;
			return;
		}			
		
		if (DataBase.personArr[index].isLocked()) {
			System.out.println("Account is locked");
			state = AppState.WELCOME_SCREEN;
			return;
		}
		
		while (tries < LOCKOUT_TRIES){
			password = Input.getPasswordUntilValid();
		
			accountUser = DataBase.getAccountUserUsingCredentials(userName,password);
		
			if (accountUser == null) {
				tries ++;
				System.out.println(MSG_INVALID_CREDENTIALS);
				System.out.println("Tries remaining until lockout: " + (LOCKOUT_TRIES-tries));
			}
		}
		
		if (tries >= LOCKOUT_TRIES && accountUser == null) {
			System.out.println("Account locked");
			DataBase.personArr[index].lockAccount();
			return;
		}
				
		System.out.println();
		System.out.println(accountUser.getFirstName()+ " logged in!\n");
		this.currentUser = accountUser;
		
		this.state = AppState.LOGGED_IN;
	}
	
	/**
	 * Get user object with login credentials
	 * @param userName
	 * @param password
	 * @return User object or NULL if wrong credentials
	 */
	public boolean login(String userName, String password) {
		boolean result = false;
		AccountUser user = null;
		
		user = DataBase.getAccountUserUsingCredentials(userName, password);
		
		if (user == null) {
			System.out.println("Invalid user credentials, login denied");
			result = false;
		}else {
			System.out.println(userName + " logged in!\n");
			this.currentUser = user;
			result = true;
		}
		
		this.state = AppState.LOGGED_IN;

		return result;
	}
	
	public void createAccount() {
		final int PHONE_NUMBER_LEN = 10; 
		
		final double MONTHLY_INCOME_MIN = 0f;
	
		boolean doesPhoneNumberExist = false;
		boolean doesUserNameExist = false;
		
		String phoneNumber;
		String firstName;
		String lastName;
		LocalDate birthDate;
		String userName;
		String password;
		double monthlyIncome;
		
		System.out.println("Create new account:");
		System.out.println("===================");
	
		// phone number input
		Input.clear();
		Input.setMessageEnterInput("Enter phone Number:");
		Input.setMessageInvalidInput("Invalid input - Phone Number must be 10 digits");
		Input.setFlagCheckLength(true);
		Input.setMinLength(PHONE_NUMBER_LEN);
		Input.setMaxLength(PHONE_NUMBER_LEN);
		Input.setFlagOnlyNumbers(true);
		
		phoneNumber = Input.getInputUntilValid();
		doesPhoneNumberExist = DataBase.doesPhoneNumberExist(phoneNumber);
			
		if (doesPhoneNumberExist) {
			System.out.println("Phone number already exists in DataBase. Try logging in, aborting.\n");
			return;
		}
		
		// user name input
		do {
			userName = Input.getUserNameUntilValid();
			
			doesUserNameExist = DataBase.doesUsernameExist(userName);
			
			if (doesUserNameExist) System.out.println("UserName Already exists in DataBase, try again.\n");
			
		}while (doesUserNameExist);
		
		// password input
		password = Input.getPasswordUntilValid();
	
		// first name input
		Input.clear();
		Input.setMessageEnterInput("Enter first name:");
		Input.setMessageInvalidInput("Invalid input - Name must be letters only");
		Input.setFlagOnlyLetters(true);
		firstName = Input.getInputUntilValid();
		
		// last name input
		Input.setMessageEnterInput("Enter last name:");
		lastName = Input.getInputUntilValid();
		
		// birthdate input
		System.out.println("Enter birthdate:");
		birthDate = Input.getDate();
		
		// monthly income input
		Input.clear();
		Input.setMessageEnterInput("Enter Monthly Income:");
		Input.setMessageInvalidInput("Monthly income needs to be atleast 0");
		monthlyIncome = Input.GetDoubleUntilValidMin(MONTHLY_INCOME_MIN);
		
		// Create account from variables and add to database
		
		Person person = new Person(firstName, lastName, phoneNumber, birthDate);
		Account account = new Account(0, AccountProperties.BRONZE);
		Credentials credentials = new Credentials(userName, password);
		AccountUser accountUser = new AccountUser(person, monthlyIncome, account, credentials);
		DataBase.addPersonToDB(accountUser);
		
		System.out.println("Account succesfully created. Approval is pending by the bank manager");
		System.out.println();
	}
	
	public void handleAccountMenu() {
		currentMenu = accountMenu;
		currentMenu.play();
		
		switch(currentMenu.getChoice()) {
		case "B":
			currentUser.checkBalance();
			break;
			
		case "D":
			currentUser.makeDeposit();
			break;
			
		case "Q":
			logOut();
			break;
			
		default:
			state = AppState.LOGGED_IN;
			break;
		}
	}
	
	private void quit() {
		System.out.println("Quitting, goodbye!");
		this.state = AppState.QUIT;
		this.keepGoing = false;
	}
	
	private void logOut() {
		if (currentUser == null)
			System.out.println("logout() No user to log out");
		
		System.out.println("Logging out...\n");
				
		System.out.println(currentUser.getUserName() + " logged out.\n");
		
		currentUser = null;
		
		state = AppState.WELCOME_SCREEN;
	}
			
	
}

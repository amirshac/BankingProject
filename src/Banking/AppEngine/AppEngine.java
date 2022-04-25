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
			handleWelcomeScreen();
			break;
		}
	}
	
	// TODO: refactor validation name checks to something more elegant
	// TODO: user lock mechanism after 3 tries
	public void handleLoginScreen() {
		final String MSG_INVALID_USERNAME = "Invalid username, must be 4-20 characters long and contain only numbers or letters.";
		final String MSG_INVALID_PASSWORD = "Invalid password, must be 4-8 characters long and contain a number and a letter.";
		final String MSG_USERNAME_DOESNT_EXIST = "Username does not exist";
		final String MSG_INVALID_CREDENTIALS = "Invalid credentials";
		
		boolean isValidInput = false;
		String input = null;
		String usernameInput;
		String passwordInput;
		
		int index = 0;
		AccountUser accountUser = null;
		
		// username input
		while (!isValidInput) {
			System.out.print("Enter username: ");
			input = Input.scanner.nextLine();
			input = input.toLowerCase();
			
			isValidInput = Credentials.isValidUsername(input);
			
			if (!isValidInput) System.out.println(MSG_INVALID_USERNAME);
		}
		
		// username not found - go back to main menu
		index = DataBase.getIndexOfUsername(input);
		if (index == -1) {
			System.out.println(MSG_USERNAME_DOESNT_EXIST);
			handleWelcomeScreen();
			return;
		}
		
		usernameInput = input;
		
		// password bit
		isValidInput = false;
		//int attempts = 0;
		
		while (!isValidInput) {
			System.out.print("Enter Password: ");
			input = Input.scanner.nextLine();
			
			isValidInput = Credentials.isValidPassword(input);
			
			if (!isValidInput) System.out.println(MSG_INVALID_PASSWORD);
		}
		
		passwordInput = input;
		
		accountUser = DataBase.getAccountUserUsingCredentials(usernameInput,passwordInput);
		
		if (accountUser == null) {
			System.out.println(MSG_INVALID_CREDENTIALS);
			return;
		}
		
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
		
		return result;
	}
	
	public void createAccount() {
		final int PHONE_NUMBER_LEN = 10; 
		
		final int USERNAME_MIN_LEN = 4;
		final int USERNAME_MAX_LEN = 20;
		
		final int PASSWORD_MIN_LEN = 4;
		final int PASSWORD_MAX_LEN = 8;
		
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
		
		// username input
		Input.clear();
		Input.setMessageEnterInput("Enter Username:");
		Input.setMessageInvalidInput("Invalid username - Must be only letters and numbers, length of 4-20");
		Input.setFlagCheckLength(true);
		Input.setMinLength(USERNAME_MIN_LEN);
		Input.setMaxLength(USERNAME_MAX_LEN);
		Input.setFlagOnlyNumbers(true);
		Input.setFlagOnlyLetters(true);
		
		do {
			userName = Input.getInputUntilValid();
			userName = userName.toLowerCase();
		
			doesUserNameExist = DataBase.doesUsernameExist(userName);
			
			if (doesUserNameExist) System.out.println("UserName Already exists in DataBase, try again.\n");
			
		}while (doesUserNameExist);
		
		// password input
		Input.clear();
		Input.setMessageEnterInput("Enter Password:");
		Input.setMessageInvalidInput("Invalid password - Must have a letter and a number, length of 4-8");
		Input.setFlagCheckLength(true);
		Input.setMinLength(PASSWORD_MIN_LEN);
		Input.setMaxLength(PASSWORD_MAX_LEN);
		Input.setFlagMustContainLetter(true);
		Input.setFlagMustContainNumber(true);
		password = Input.getInputUntilValid();
	
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
		case "L":
			handleLoginScreen();
			break;
		
		case "Q":
			logOut();
			break;
			
		default:
			handleWelcomeScreen();
			break;
		}
	}
	
	private void quit() {
		System.out.println("Quitting, goodbye!");
		Input.scanner.close();
		this.keepGoing = false;
	}
	
	private void logOut() {
		if (currentUser == null)
			System.out.println("logout() No user to log out");
		
		System.out.println("Logging out...\n");
		
		currentUser = null;
		
		System.out.println(currentUser.getUserName() + " logged out.\n");
		state = AppState.WELCOME_SCREEN;
	}
			
	
}

package Banking.AppEngine;

import java.time.LocalDate;

import Banking.Account.Account;
import Banking.Account.AccountProperties;
import Banking.DataBase.DataBase;
import Banking.Input.Input;
import Banking.Menu.*;
import Banking.Users.*;

public class AppEngine {
	protected AccountUser currentUser;	
	protected UserInterface userInterface;
	
	protected boolean keepGoing;
	
	public AppEngine() {
		userInterface = new UserInterface();
		keepGoing = true;
	}
	
	public void play() {
		
		while (keepGoing){
			userInterface.menuWelcome.play();
			handleMenuChoice();
			
			if (userInterface.menuWelcome.getChoice().equals("Q")) keepGoing = false;
		}
		
		System.out.println("Application terminated");
	}
	
	protected void handleMenuChoice() {
		switch(userInterface.menuWelcome.getChoice()) {
		case "L":
			loginScreen();
			break;
			
		case "C":
			createAccountScreen();
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
		
		// TODO: user lock mechanism time
		public void loginScreen() {
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
				return;
			}			
			
			if (DataBase.userArr[index].isLocked()) {
				System.out.println("Account is locked");
				return;
			}
			
			// lockout user if given wrong password a few times
			while (tries < LOCKOUT_TRIES){
				password = Input.getPasswordUntilValid();
			
				accountUser = DataBase.getAccountUserUsingCredentials(userName,password);
			
				if (accountUser == null) {
					tries ++;
					System.out.println(MSG_INVALID_CREDENTIALS);
					System.out.println("Tries remaining until lockout: " + (LOCKOUT_TRIES-tries));
				}else {
					break;
				}
			}
			
			// accountUser null means we couldn't get user from database, means wrong password
			if (tries >= LOCKOUT_TRIES && accountUser == null) {
				System.out.println("Account locked");
				// TODO: set time for lockout
				DataBase.userArr[index].lockAccount();
				return;
			}
			 
			System.out.println();
			System.out.println(accountUser.getFullName()+ " logged in!\n");
			
			accountUser.userInterface.menuAccount.play();
		}
		
		/**
		 * Create account Screen
		 */
		public void createAccountScreen() {		
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
			phoneNumber = Input.getPhoneNumberUntilValid();
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
			monthlyIncome = Input.getDoubleUntilValidMin(MONTHLY_INCOME_MIN);
			
			// Create account from variables and add to database
			
			Person person = new Person(firstName, lastName, phoneNumber, birthDate);
			Account account = new Account(0, AccountProperties.BRONZE);
			Credentials credentials = new Credentials(userName, password);
			AccountUser accountUser = new AccountUser(person, monthlyIncome, account, credentials);
			
			// TODO: unlock implementation by manager account
			// lock account until released by manager - marked out until implementation of unlock
			// accountUser.lockAccount(); 
			
			DataBase.addUserToDB(accountUser);
			
			System.out.println("Account succesfully created. Approval is pending by the bank manager");
			System.out.println();
		}
			
		private void quit() {
			System.out.println("Quitting, goodbye!");
	
		}
	
	// Function used to test users without login menu
	public void testUser(String username, String pass) {
		currentUser = DataBase.getAccountUserUsingCredentials(username, pass);
		
		if (currentUser == null) {
			System.out.println("error, no such user");
			return;
		}
		
		currentUser.playMainMenu(); 
	}

			
}
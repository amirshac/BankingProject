package Banking.Menu;

import java.time.LocalDate;

import Banking.Account.Account;
import Banking.Account.AccountProperties;
import Banking.AppEngine.AppState;
import Banking.DataBase.DataBase;
import Banking.Input.Input;
import Banking.Users.AccountUser;
import Banking.Users.Credentials;
import Banking.Users.Person;

public class MenuWelcome extends Menu{
	
	public AccountUser currentUser;
	
	public MenuWelcome() {
		super("Welcome to ADJB banking system:");
		addOption("Log in", "L");
		addOption("Create a new account", "C");
		addOption("Quit", "Q");
		addOption("Print DataBase<DEBUG>", "P");
	}
	
	protected void handleChoice() {
		switch(this.choice) {
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
		
		// TODO: user lock mechanism time
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
				//state = AppState.WELCOME_SCREEN;
				return;
			}			
			
			if (DataBase.userArr[index].isLocked()) {
				System.out.println("Account is locked");
				//state = AppState.WELCOME_SCREEN;
				return;
			}
			
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
			
			if (tries >= LOCKOUT_TRIES && accountUser == null) {
				System.out.println("Account locked");
				// TODO: set time for lockout
				DataBase.userArr[index].lockAccount();
				return;
			}
					
			System.out.println();
			System.out.println(accountUser.getFirstName()+ " logged in!\n");
			
			accountUser.UI.menuAccount.play();
		}
		
		/**
		 * Create account Screen
		 */
		public void createAccount() {		
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
			DataBase.addUserToDB(accountUser);
			
			System.out.println("Account succesfully created. Approval is pending by the bank manager");
			System.out.println();
		}
			
		private void quit() {
			System.out.println("Quitting, goodbye!");
	
		}
		
}

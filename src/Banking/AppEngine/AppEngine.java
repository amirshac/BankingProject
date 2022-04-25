package Banking.AppEngine;

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
		case "Q":
			quit();
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

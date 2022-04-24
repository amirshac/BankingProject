package Banking.AppEngine;

import Banking.DataBase.DataBase;
import Banking.Menu.*;
import Banking.Users.*;

public class AppEngine {
	protected AppState state;
	protected AccountUser currentUser;
	protected Menu currentMenu;
	protected Menu welcomeMenu;
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
	
	private void handleLoginScreen() {
		
	}
	
	public boolean login(String userName, String password) {
		boolean result = false;
		AccountUser user = null;
		
		user = DataBase.getAccountUserUsingCredentials(userName, password);
		
		if (user == null) {
			System.out.println("Invalid user credentials, login denied");
			result = false;
		}else {
			System.out.println(userName + " logged in!");
			this.currentUser = user;
			result = true;
		}
		
		return result;
	}
	
	private void quit() {
		System.out.println("Quitting, goodbye!");
		Menu.scanner.close();
		this.keepGoing = false;
	}
			
	
}

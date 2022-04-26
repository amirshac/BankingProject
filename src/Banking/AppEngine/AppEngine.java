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
	protected AccountUser currentUser;	
	protected UserInterface UI;
	
	protected boolean keepGoing;
	
	public AppEngine() {
		UI = new UserInterface();
		keepGoing = true;
	}
	
	public void play() {	
		while (keepGoing){
			UI.menuWelcome.play();
			if (UI.menuWelcome.getChoice().equals("Q")) keepGoing = false;
		}
		
		System.out.println("Application terminated");
	}
	
	
	/**
	 * Plays 'account menu' and activates functions according to user input
	 */
/*
	private void handleBillsMenu() {
		currentMenu = billsMenu;
		currentMenu.play();
		
		switch(currentMenu.getChoice()) {
			
		case "Q":
			state = AppState.LOGGED_IN;
			break;
			
		default:
			state = AppState.BILLS_MENU;
			break;
		}
	}
	*/

	
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

	

			
}
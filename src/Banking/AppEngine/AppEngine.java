package Banking.AppEngine;

import Banking.DataBase.DataBase;
import Banking.Menu.*;
import Banking.Users.*;

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
	
	public void testUser(String username, String pass) {
		currentUser = DataBase.getAccountUserUsingCredentials(username, pass);
		currentUser.UI.menuAccount.play();
	}

			
}
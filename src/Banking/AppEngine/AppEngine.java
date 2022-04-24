package Banking.AppEngine;

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
	
	private void quit() {
		System.out.println("Quitting, goodbye!");
		Menu.scanner.close();
		this.keepGoing = false;
	}
			
	
}

package Banking.Menu;

import Banking.Users.AccountUser;

/**
 * Userinterface class holds all possible menus
 * AccountUser object is optional - so menus will know who to address while activating functions
 * Menus set to public so they can be accessed easily
 * @author amir
 *
 */
public class UserInterface {
	
	public AccountUser currentUser;
	public Menu menuCurrent;
	public Menu menuWelcome;
	public Menu menuAccount;
	public Menu menuBills;
	
	public UserInterface() {
		this (null);
	}
	
	public UserInterface(AccountUser user) {
		menuWelcome = new MenuWelcome();
		menuWelcome.setUser(user);
		menuAccount = new MenuAccount();
		menuAccount.setUser(user);
		menuBills = new MenuBills();
		menuBills.setUser(user);
	}
	
	public void setUser(AccountUser user) {
		currentUser = user;
	}
	
	public void setMenu(Menu menu) {
		menuCurrent = menu;
	}
	
	public void play() {
		menuCurrent.play();
	}
	
	/**
	 * get current menu choice as string - returned from 'play' function
	 * @return string
	 */
	public String getChoice() {
		return menuCurrent.getChoice();
	}
	
	
}

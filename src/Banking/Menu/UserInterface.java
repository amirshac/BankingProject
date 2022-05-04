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
	public Menu menuAccountManager;
	
	
	public UserInterface() {
		menuWelcome = new MenuWelcome();
		menuAccount = new MenuAccount();
		menuBills = new MenuBills();
		menuAccountManager = new MenuAccountManager();
	}

}

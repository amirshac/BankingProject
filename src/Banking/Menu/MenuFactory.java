package Banking.Menu;

public class MenuFactory {
	
	public MenuFactory() {
	}
	
	public static Menu get(MenuType type) {
		switch (type) {
		case WELCOME:
			return new MenuWelcome();
		case ACCOUNT:
			return new MenuAccount();
		case LOGIN:
			return null;
		default:
			return new MenuWelcome();
		}
		
	}
}

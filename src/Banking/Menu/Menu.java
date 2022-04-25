package Banking.Menu;

import Banking.Input.*;

public abstract class Menu {
	private static final String DEFAULT_MENU_TITLE = "MENU";
	private static final String DEFAULT_ERROR_MSG = "Wrong input please try again";
	private static final int MENU_MAX_SIZE = 10;

//	public static Scanner scanner = new Scanner(System.in);
	
	protected int index;
	protected String title;
	protected String[] optionList;
	protected String[] commandList;
	
	protected String choice;
	
	public Menu() {
		this(DEFAULT_MENU_TITLE);
	}
	
	public Menu(String title) {
		setTitle(title);
		optionList = new String[MENU_MAX_SIZE];
		commandList = new String[MENU_MAX_SIZE];
		choice = new String();
		index = 0;
	}

	public void addOption(String optionName, String optionCommand) {
		
		if (index >= MENU_MAX_SIZE) {
			System.out.println("Error menu size reached, can't add options");
			return;
		}
		
		optionList[index] = optionName;
		commandList[index] = optionCommand;
		index ++;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Prints menu and all options in a subsequental order
	 */
	private void print() {
		String msg = this.title + "\n";
		for (int i=0; i<index; i++){
			msg += String.format("[%s] %s%n", commandList[i], optionList[i]);
		}
		System.out.println(msg);
	}
	
	/**
	 * Displays menu and asks for input until input is valid
	 * String case insensitive
	 */
	public void play() {
		String inputStr = null;
		
		boolean keepGoing = true;
		
		while (keepGoing) {
			this.print();

			inputStr = Input.scanner.nextLine();
			inputStr = inputStr.toUpperCase();
			
			if (!isValidChoice(inputStr))
				System.out.println(DEFAULT_ERROR_MSG);	
			else
				keepGoing = false;
			
		}
		this.choice = inputStr;	
	}
	
	/**
	 * Checks if choice is within range of commands
	 * @param choice
	 * @return true / false if choice is valid
	 */
	private boolean isValidChoice(String choiceStr) {
		boolean result = false;
		
		for (int i=0; i<index; i++) {
			if (choiceStr.equals(commandList[i])) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	// setters getters
	public String getChoice() {
		return this.choice;
	}
	
	@Override
	public String toString() {
		return "Menu [title=" + title + ", optionList=" + optionList + "]";
	}
	
}

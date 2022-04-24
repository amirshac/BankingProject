package Banking.Menu;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Menu {
	private static final String DEFAULT_MENU_TITLE = "MENU";
	private static final String DEFAULT_ERROR_MSG = "Wrong input please try again";
	private static final int DEFAULT_MIN_VALUE = 0;
	private static Scanner scanner;
	
	protected String title;
	protected ArrayList<String> optionList;
	
	private int choice;
	
	public Menu() {
		this(DEFAULT_MENU_TITLE);
	}
	
	public Menu(String title) {
		setTitle(title);
		optionList = new ArrayList<>();
		choice = 0;
	}

	public void addOption(String optionName) {
		optionList.add(optionName);
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Prints menu and all options in a subsequental order
	 */
	private void print() {
		String msg = this.title + "\n";
		for (int i=0; i<optionList.size(); i++){
			msg += String.format("[%d] %s%n", i, optionList.get(i));
		}
		
		System.out.println(msg);
	}
	
	/**
	 * Displays menu and asks for input until input is valid
	 */
	public void play() {
		scanner = new Scanner(System.in);
		
		boolean keepGoing = true;
		
		while (keepGoing) {
			this.print();
			choice = Integer.parseInt(scanner.nextLine());

			if (!isValidChoice(choice)) 
				System.out.println(DEFAULT_ERROR_MSG);
			
			keepGoing = !isValidChoice(choice);
		}
		scanner.close();
	}
	
	/**
	 * Checks if choice is within range of menu
	 * @param choice
	 * @return true / false if choice is valid
	 */
	private boolean isValidChoice(int choice) {
		boolean result = true;
		if (choice < DEFAULT_MIN_VALUE || choice > this.optionList.size())
			result = false;
		
		return result;
	}
	
	@Override
	public String toString() {
		return "Menu [title=" + title + ", optionList=" + optionList + "]";
	}
	
}

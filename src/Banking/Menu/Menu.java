package Banking.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public abstract class Menu {
	private static final String DEFAULT_MENU_TITLE = "MENU";
	private static final String DEFAULT_ERROR_MSG = "Wrong input please try again";

	private static Scanner scanner;
	
	protected String title;
	protected ArrayList<String> optionList;
	protected ArrayList<String> commandList;
	
	protected String choice;
	
	public Menu() {
		this(DEFAULT_MENU_TITLE);
	}
	
	public Menu(String title) {
		setTitle(title);
		optionList = new ArrayList<>();
		commandList = new ArrayList<>();
	}

	public void addOption(String optionName, String optionCommand) {
		optionList.add(optionName);
		commandList.add(optionCommand);
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
			msg += String.format("[%s] %s%n", commandList.get(i), optionList.get(i));
		}
		
		System.out.println(msg);
	}
	
	/**
	 * Displays menu and asks for input until input is valid
	 */
	public void play() {
		scanner = new Scanner(System.in);
		String inputStr;
		
		boolean keepGoing = true;
		
		while (keepGoing) {
			this.print();

			inputStr = scanner.nextLine();
			
			if (!isValidChoice(inputStr))
				System.out.println(DEFAULT_ERROR_MSG);	
			else
				keepGoing = false;
			
		}
		scanner.close();
	}
	
	/**
	 * Checks if choice is within range of commands
	 * @param choice
	 * @return true / false if choice is valid
	 */
	private boolean isValidChoice(String choiceStr) {
		boolean result = false;
		
		result = commandList.contains(choiceStr);
		
		return result;
	}
	
	@Override
	public String toString() {
		return "Menu [title=" + title + ", optionList=" + optionList + "]";
	}
	
}

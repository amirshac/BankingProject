package Banking.Menu;

import java.util.ArrayList;

public abstract class Menu {
	protected String title;
	protected ArrayList<String> optionList;
	
	public Menu() {
		this("");
	}
	
	public Menu(String title) {
		this.title = title;
		optionList = new ArrayList<>();
	}

	public void addOption(String optionName) {
		optionList.add(optionName);
	}
	
	public void print() {
		String msg = "<<"+this.title+">>\n";
		for (int i=0; i<optionList.size(); i++){
			msg += String.format("[%d] %s%n", i, optionList.get(i));
		}
		
		System.out.println(msg);
	}
	
	@Override
	public String toString() {
		return "Menu [title=" + title + ", optionList=" + optionList + "]";
	}
	
}

package Banking.Runner;
import java.time.LocalDate;

import Banking.Account.*;
import Banking.DataBase.DataBase;
import Banking.Menu.*;
import Banking.Users.*;

public class Runner {

	public static void main(String[] args) {
		Menu menu;
		menu = new MenuWelcome();
		menu.play();
		
		//DataBase.populateDataBase();
		//DataBase.print();
	}
}
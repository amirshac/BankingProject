package Banking.Runner;
import java.time.LocalDate;

import Banking.Account.*;
import Banking.AppEngine.AppEngine;
import Banking.DataBase.DataBase;
import Banking.Menu.*;
import Banking.Users.*;

public class Runner {

	public static AppEngine engine = new AppEngine();
	
	public static void testLoginWithFunction() {
		
		engine.login("dory", "dory11");
		AccountUser user;
		user = DataBase.getAccountUserUsingCredentials("dory", "dory11");
		user.printAccountActivityLog();
	}
	
	public static void testLoginScreen() {
		engine.handleLoginScreen();
	}
	
	
	public static void main(String[] args) {		
		DataBase.populateDataBase();
		
		//testLoginScreen();
		engine.play();
	}
}
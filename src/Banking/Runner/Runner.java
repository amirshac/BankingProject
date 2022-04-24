package Banking.Runner;
import java.time.LocalDate;

import Banking.Account.*;
import Banking.AppEngine.AppEngine;
import Banking.DataBase.DataBase;
import Banking.Menu.*;
import Banking.Users.*;

public class Runner {

	
	public static void testRun1() {
		DataBase.populateDataBase();
		AppEngine engine = new AppEngine();
		engine.login("dory", "dory11");
	}
	
	public static void main(String[] args) {		

		testRun1();
		//engine.play();
	}
}
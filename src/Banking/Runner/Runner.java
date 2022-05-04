package Banking.Runner;

import Banking.AppEngine.AppEngine;
import Banking.DataBase.DataBase;
import Banking.Input.*;

public class Runner {

	public static AppEngine engine = new AppEngine();
		
	public static void main(String[] args) {		
		DataBase.populateDataBase();
		//engine.play();
		engine.testUser("amir", "amir11");
		Input.scanner.close();		
	}
}
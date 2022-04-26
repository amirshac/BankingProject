package Banking.Runner;

import Banking.AppEngine.AppEngine;
import Banking.DataBase.DataBase;
import Banking.Input.*;
import Banking.Users.*;

public class Runner {

	public static AppEngine engine = new AppEngine();
		
	public static void main(String[] args) {		
		DataBase.populateDataBase();
		
		engine.play();
		
		Input.scanner.close();		
	}
}
package Banking.Runner;
import java.time.LocalDate;

import Banking.Account.*;
import Banking.AppEngine.AppEngine;
import Banking.DataBase.DataBase;
import Banking.Menu.*;
import Banking.Users.*;

public class Runner {

	public static void main(String[] args) {		
		DataBase.populateDataBase();
		//DataBase.print();
		
		AppEngine engine = new AppEngine();
		engine.play();
	}
}
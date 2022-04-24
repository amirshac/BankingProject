package Banking.Runner;
import java.time.LocalDate;

import Banking.Account.*;
import Banking.Menu.*;
import Banking.Users.*;

public class Runner {

	public static void main(String[] args) {
		//Menu menu = new MenuAccount();
		//menu.print();
		
		Person person = new Person("Amir", "Shachar", "0525403091", LocalDate.of(1983, 4, 12));
		System.out.println(person);
		
		Account account = new Account(50, AccountProperties.GOLD);
		System.out.println(account);
		
		
	}
}
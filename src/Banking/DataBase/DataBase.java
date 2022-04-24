package Banking.DataBase;
import java.time.LocalDate;
import java.util.LinkedList;
import Banking.Users.*;
import Banking.Account.*;

public class DataBase {
	
//	public static LinkedList<Person> personList = new LinkedList<>();
	private static int DB_SIZE = 100;
	private static int index = 0;
	
	public static Person[] personArr = new Person[DB_SIZE];
	
	
	private static void addPersonToDB(Person person) {
		if (index >= DB_SIZE) {
			System.out.println("ERROR DataBase Limit reached can't insert");
			return;
		}
		
		personArr[index] = person;
		index++;
	}
	
	public int getSize() {
		return index;
	}
	
	/**
	 * Populates database with some demo users, including bank manager
	 */
	public static void populateDataBase() {
		Person person;
		AccountUser accountUser;
		BankManagerUser bankManager;
		Account account;
		Credentials credentials;
		double balance = 0;
		double income = 0;
		
		income = 0;
		balance = 0;
		
		person = new Person("Donald", "Trump", "0520000001", LocalDate.of(1946, 7, 14));
		account = new Account(balance, AccountProperties.TITANIUM);
		bankManager = new BankManagerUser(person, income, account);
		
		credentials = new Credentials("admin", "admin11");
		bankManager.setCredentials(credentials);
		
		addPersonToDB(bankManager);
	
		
		income = 3000;
		balance = 500;
		
		person = new Person("Amir", "Arison", "0520000002", LocalDate.of(1983, 4, 12));
		account = new Account(balance, AccountProperties.BRONZE);
		credentials = new Credentials("amir", "amir11");
		
		accountUser = new AccountUser(person, income, account);
		addPersonToDB(accountUser);
		
		income = 9000;
		balance = 22453;	
		person = new Person("Ben", "Bigsby", "0520000003", LocalDate.of(1988, 1, 4));
		account = new Account(balance, AccountProperties.SILVER);
		accountUser = new AccountUser(person, income, account);
		addPersonToDB(accountUser);
		
		income = 15000;
		balance = 34898;	
		person = new Person("Cody", "Connor", "0520000004", LocalDate.of(1960, 5, 23));
		account = new Account(balance, AccountProperties.GOLD);
		accountUser = new AccountUser(person, income, account);
		addPersonToDB(accountUser);
		
		income = 20000;
		balance = 943112;	
		person = new Person("Dory", "Dorshowitz", "0520000005", LocalDate.of(1962, 4, 23));
		account = new Account(balance, AccountProperties.TITANIUM);
		accountUser = new AccountUser(person, income, account);
		addPersonToDB(accountUser);
	}
	
	public static void print() {
		System.out.println(index + " elements in database:\n");
		for (int i=0; i<index; i++) {
			System.out.println(personArr[i]);
			System.out.println();
		}
	}
}

package Banking.DataBase;
import java.time.LocalDate;
import Banking.Users.*;
import Banking.Account.*;
import Banking.ActivityLog.ActivityLog;

public class DataBase {
	
//	public static LinkedList<Person> personList = new LinkedList<>();
	private static int DB_SIZE = 100;
	private static int DB_index = 0;
	
	public static AccountUser[] personArr = new AccountUser[DB_SIZE];

	/**
	 * Adds user to database
	 * @param user
	 */
	public static void addPersonToDB(AccountUser user) {
		if (DB_index >= DB_SIZE) {
			System.out.println("ERROR DataBase Limit reached can't insert");
			return;
		}
		
		personArr[DB_index] = user;
		DB_index++;
	}
	
	public int getSize() {
		return DB_index;
	}
	
	/**
	 * 
	 * @param userName
	 * @return true/ false whether username exists in database
	 */
	public static boolean doesUsernameExist(String userName) {
		boolean result = true;
		
		int index = getIndexOfUsername(userName);
		
		if (index == -1)
			result = false;
		
		return result;
	}
	
	/**
	 * 
	 * @param phonenumber
	 * @return true/ false whether phonenumber exists in database
	 */
	public static boolean doesPhoneNumberExist(String phoneNumber) {
		boolean result = true;
		
		int index = getIndexOfPhoneNumber(phoneNumber);
		
		if (index == -1)
			result = false;
		
		return result;
	}
	
	/**
	 * 
	 * @param userName
	 * @return index of user object in database, -1 if not found
	 */
	public static int getIndexOfUsername(String userName) {
		int result = -1;
		String str;
		
		for (int i=0; i<DB_index; i++) {
			str = personArr[i].getUserName();
			
			if (userName.equals(str)) {
				result = i;
				break;
			}
		}
		
		return result;
	}

	/**
	 * 
	 * @param phoneNumber
	 * @return index of user object in database, -1 if not found
	 */	
	public static int getIndexOfPhoneNumber(String phoneNumber) {
		int result = -1;
		String str;
		
		for (int i=0; i<DB_index; i++) {
			str = personArr[i].getPhoneNumber();
			
			if (phoneNumber.equals(str)) {
				result = i;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param credentials
	 * @return AccountUser with said credentials or NULL if credentials were wrong
	 */
	public static AccountUser getAccountUserUsingCredentials(Credentials credentials) {
		AccountUser result = null;
		String password;
		
		int index = getIndexOfUsername(credentials.getUserName());
		if (index == -1) return null;
		
		password = personArr[index].getPassword();
		if (password == null) return null;
		
		if (password.equals(credentials.getPassword()))
			result = personArr[index];
		
		return result;
	}
	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @return AccountUser with said credentials or NULL if credentials were wrong
	 */
	public static AccountUser getAccountUserUsingCredentials(String userName, String password) {
		AccountUser result = null;
		Credentials credentials = new Credentials(userName, password);
		result = getAccountUserUsingCredentials(credentials);
		return result;
	}
	
	/**
	 * 
	 * @param phoneNumber
	 * @return AccountUser object with said phonenumber, NULL If not found
	 */
	public static AccountUser getAccountUserUsingPhoneNumber(String phoneNumber) {
		AccountUser result = null;
		int index = getIndexOfPhoneNumber(phoneNumber);
		
		if (index == -1)
			return null;
		
		result = personArr[index];
		return result;
	}
	
	/**
	 * returns account user object according to index in database
	 * @param index
	 * @return account user, or NULL of invalid index
	 */
	public static AccountUser getAccountUserUsingIndex(int index) {
		if ( index >= DB_index || index < 0) return null;
		return personArr[index];
	}
	/**
	 * Populates database with some demo users, including bank manager
	 */
	public static void populateDataBase() {
		populateUsersDB();
		populateAccountActivitiesDB();
	}
	
	private static void populateUsersDB() {
		Person person;
		AccountUser accountUser;
		BankManagerUser bankManager;
		Account account;
		Credentials credentials;
		double balance = 0;
		double income = 0;
		
		income = 0;
		balance = 0;
		
		person = new Person("Donald", "Trump CEO", "0520000001", LocalDate.of(1946, 7, 14));
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
		accountUser = new AccountUser(person, income, account, credentials);
		addPersonToDB(accountUser);
		
		income = 9000;
		balance = 22453;	
		person = new Person("Ben", "Bigsby", "0520000003", LocalDate.of(1988, 1, 4));
		account = new Account(balance, AccountProperties.SILVER);
		accountUser = new AccountUser(person, income, account);
		accountUser.setCredentials("benben", "ben11");
		addPersonToDB(accountUser);
		
		income = 15000;
		balance = 34898;	
		person = new Person("Cody", "Connor", "0520000004", LocalDate.of(1960, 5, 23));
		account = new Account(balance, AccountProperties.GOLD);
		accountUser = new AccountUser(person, income, account);
		accountUser.setCredentials("cody", "cody11");
		addPersonToDB(accountUser);
		
		income = 20000;
		balance = 943112;	
		person = new Person("Dory", "Dorshowitz", "0520000005", LocalDate.of(1962, 4, 23));
		account = new Account(balance, AccountProperties.TITANIUM);
		accountUser = new AccountUser(person, income, account);
		accountUser.setCredentials("dory", "dory11");
		addPersonToDB(accountUser);
	}
	
	private static void populateAccountActivitiesDB() {
		// TODO populate account acitivies in database
		AccountUser accountUser = personArr[4];
		
		ActivityLog activityLog = new ActivityLog();
		accountUser.getAccount().addActivityLog(activityLog);
	
	}
	
	public static void print() {
		System.out.println(DB_index + " elements in database:\n");
		for (int i=0; i<DB_index; i++) {
			System.out.println(personArr[i]);
			System.out.println();
		}
	}
}

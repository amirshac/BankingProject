package Banking.DataBase;
import java.time.LocalDate;
import Banking.Users.*;
import Banking.Account.*;
import Banking.ActivityLog.ActivityLog;

public class DataBase {
	
	private static int DB_SIZE = 100;
	private static int DB_index = 0;
	
	public static AccountUser[] userArr = new AccountUser[DB_SIZE];

	/**
	 * Adds user to database
	 * @param user
	 */
	public static void addUserToDB(AccountUser user) {
		if (DB_index >= DB_SIZE) {
			System.out.println("ERROR DataBase Limit reached can't insert");
			return;
		}
		
		userArr[DB_index] = user;
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
			str = userArr[i].getUserName();
			
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
			str = userArr[i].getPhoneNumber();
			
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
		
		password = userArr[index].getPassword();
		if (password == null) return null;
		
		if (password.equals(credentials.getPassword()))
			result = userArr[index];
		
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
		
		result = userArr[index];
		return result;
	}
	
	/**
	 * returns account user object according to index in database
	 * @param index
	 * @return account user, or NULL of invalid index
	 */
	public static AccountUser getAccountUserUsingIndex(int index) {
		if ( index >= DB_index || index < 0) return null;
		return userArr[index];
	}
	
	public static BankManagerUser getBankAccountUser() {
		return (BankManagerUser)getAccountUserUsingIndex(0);
	}
	
	/**
	 * Populates database with some demo users, including bank manager
	 */
	public static void populateDataBase() {
		populateUsersDB();
		//populateAccountActivitiesDB();
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
		credentials = new Credentials("admin", "admin11");
		bankManager = new BankManagerUser(person, income, account, credentials);
		
		addUserToDB(bankManager);
		
		income = 3000;
		balance = 500;
		
		person = new Person("Amir", "Arison", "0520000002", LocalDate.of(1983, 4, 12));
		account = new Account(balance, AccountProperties.BRONZE);
		credentials = new Credentials("amir", "amir11");
		accountUser = new AccountUser(person, income, account, credentials);
		addUserToDB(accountUser);
		
		income = 9000;
		balance = 4000;	
		person = new Person("Benny", "Bigsby", "0520000003", LocalDate.of(1988, 1, 4));
		account = new Account(balance, AccountProperties.SILVER);
		credentials = new Credentials("benny", "benny11");
		accountUser = new AccountUser(person, income, account, credentials);
		addUserToDB(accountUser);
		
		income = 15000;
		balance = 5000;	
		person = new Person("Cody", "Connor", "0520000004", LocalDate.of(1960, 5, 23));
		account = new Account(balance, AccountProperties.GOLD);
		credentials = new Credentials("cody", "cody11");
		accountUser = new AccountUser(person, income, account, credentials);
		addUserToDB(accountUser);
		
		income = 20000;
		balance = 6000;	
		person = new Person("Dory", "Dorshowitz", "0520000005", LocalDate.of(1962, 4, 23));
		account = new Account(balance, AccountProperties.TITANIUM);
		credentials = new Credentials("dory", "dory11");
		accountUser = new AccountUser(person, income, account, credentials);
		addUserToDB(accountUser);
	}
	
	private static void populateAccountActivitiesDB() {
		// TODO populate account acitivies in database
		AccountUser accountUser = userArr[4];
		
		ActivityLog activityLog = new ActivityLog();
		accountUser.getAccount().addActivityLog(activityLog);
	
	}
	
	public static void print() {
		System.out.println(DB_index + " elements in database:\n");
		for (int i=0; i<DB_index; i++) {
			System.out.println(userArr[i]);
			System.out.println();
		}
	}
}

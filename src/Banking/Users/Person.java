package Banking.Users;

import java.time.LocalDate;

//TODO make person abstract

public class Person {
	protected String firstName;
	protected String lastName;
	protected String phoneNumber;
	protected LocalDate birthDate;
	
	private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.of(2000, 1, 1);
	private static final String DEFAULT_FIRSTNAME = "DEFAULT";
	private static final String DEFAULT_LASTNAME = "DEFAULT";
	private static final String DEFAULT_PHONENUMBER = "0000000000";
	
	// constructors
	public Person(String firstName, String lastName, String phoneNumber, LocalDate birthDate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.birthDate = birthDate;
	}
	
	public Person(String firstName, String lastName, String phoneNumber) {
		this(firstName, lastName, phoneNumber, DEFAULT_BIRTH_DATE);
	}
	
	public Person() {
		this(DEFAULT_FIRSTNAME, DEFAULT_LASTNAME, DEFAULT_PHONENUMBER);
	}
	
	public Person(Person person) {
		this(person.firstName, person.lastName, person.phoneNumber, person.birthDate);
	}
	
	@Override
	public String toString() {
		return "<Person>\n"
				+ "[firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber
				+ ", birthDate=" + birthDate + "]\n</Person>";
	}

	// setters getters
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	
	
}

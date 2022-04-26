package Banking.Loan;

public class Loan {
	protected double amount;
	protected double interest;
	protected double monthlyPayment;
	protected int payments;
	protected double debt;
	
	public Loan() {
		this.amount = 0;
		this.interest = 0;
		this.monthlyPayment = 0;
		this.payments = 0;
		this.debt = 0;
	}
	
	public Loan(double amount, double interest, double monthlyPayment, int payments, double debt) {
		super();
		this.amount = amount;
		this.interest = interest;
		this.monthlyPayment = monthlyPayment;
		this.payments = payments;
		this.debt = debt;
	}
	
	// constructs new loan and calculates fields
	public Loan(double amount, double interest, int payments) {
		double debt = calculateDebtAfterInterest(amount, interest);
		double monthlyPayment = debt / (double)payments;
		this.amount = amount;
		this.interest = interest;
		this.monthlyPayment = monthlyPayment;
		this.debt = debt;
		this.payments = payments;
	}
	
	public static double calculateDebtAfterInterest(double amount, double interest) {
		double result = amount;
		result += amount*interest/100.0;
		return result;
	}

	@Override
	public String toString() {
		return "Loan [amount=" + amount + ", interest=" + interest + ", monthlyPayment=" + monthlyPayment
				+ ", payments=" + payments + ", debt=" + debt + "]";
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(double monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public int getPayments() {
		return payments;
	}

	public void setPayments(int payments) {
		this.payments = payments;
	}

	public double getDebt() {
		return debt;
	}

	public void setDebt(double debt) {
		this.debt = debt;
	}	
		
}

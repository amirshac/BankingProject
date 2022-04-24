package Banking.Account;

public enum AccountProperties {
	BRONZE(4.5f, 6f, 5f, 7.5f, 10000, 2500),
	SILVER(3f, 4.5f, 3.8f, 5f, 20000, 4000),
	GOLD(1.5f, 3f, 1.75f, 3.8f, 50000, 6000),
	TITANIUM(0f, 0f, 0f, 0f, 999999, 999999);
	
	float interestMin, interestMax, operationFeeMin, operationFeeMax;
	double maxLoan, maxWithdrawalDaily;
	
	float interest, operationFee;
		
	AccountProperties(float interestMin, float interestMax, float operationFeeMin, 
			float operationFeeMax, double maxLoan, double maxWithdrawalDaily) {
		this.interestMin = interestMin;
		this.interestMax = interestMax;
		this.operationFeeMin = operationFeeMin;
		this.operationFeeMax = operationFeeMax;
		this.maxLoan = maxLoan;
		this.maxWithdrawalDaily = maxWithdrawalDaily;
		
		this.interest = interestMax;
		this.operationFee = operationFeeMax;
	}
	
	
	// setters getters

	public void setInterest(float interest) {
		if (interest >= interestMin && interest < interestMax )
			this.interest = interest;
		else 
			System.out.println("Interest rate not within limits");
	}
	
	public void setOperationFee(float operationFee) {
		if (operationFee >= operationFeeMin && operationFee < operationFeeMax )
			this.operationFee = operationFee;
		else 
			System.out.println("Operation Fee not within limits");
	}
	
	public float getInterest() {
		return this.interest;
	}
	
	public float getOperationFee() {
		return this.operationFee;
	}
	
	public double getMaxLoan() {
		return this.maxLoan;
	}
	
	public double GetMaxWithdrawalDaily() {
		return this.maxWithdrawalDaily;
	}
	
}

package com.uncia.drools_runtime.model;

public class Participant {

	private int age;
	private int creditScore;
	private long annualSalary;
	private long loanAmount;
//	private String gender;
	
	public Participant() {
	}	
	
	

	public Participant(int age, int creditScore, long annualSalary, long loanAmount) {
	this.age = age;
	this.creditScore = creditScore;
	this.annualSalary = annualSalary;
	this.loanAmount = loanAmount;
}



	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}

	public int getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}

	public long getAnnualSalary() {
		return annualSalary;
	}

	public void setAnnualSalary(long annualSalary) {
		this.annualSalary = annualSalary;
	}

	public long getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(long loanAmount) {
		this.loanAmount = loanAmount;
	}

//	public String getGender() {
//		return gender;
//	}
//
//	public void setGender(String gender) {
//		this.gender = gender;
//	}

}

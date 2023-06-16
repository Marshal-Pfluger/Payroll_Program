//********************************************************************
//
//  Developer:     Marshal Pfluger
//
//  Project #:     Ten
//
//  File Name:     SalariedEmployee.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      05/10/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  17.0.4.1
//
//  Description:   SalariedEmployee object  
//
//********************************************************************
public class SalariedEmployee {
	private String sSN;
	private int weekNumber;
	private double salary;
	private double bonus;
	
	public SalariedEmployee(String sSN, int weekNumber, double salary) {
		setSSN(sSN);
		setWeekNumber(weekNumber);
		setSalary(salary);
		bonus = 0.0;
	}
	
	public void setSSN(String sSN) {
		this.sSN = sSN;
	}
	
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	
	public String getSSN() {
	    return this.sSN;
	}

	public int getWeekNumber() {
	    return this.weekNumber;
	}

	public double getSalary() {
	    return this.salary;
	}

	public double getBonus() {
	    return this.bonus;
	}
}

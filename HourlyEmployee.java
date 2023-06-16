//********************************************************************
//
//  Developer:     Marshal Pfluger
//
//  Project #:     Ten
//
//  File Name:     HourlyEmployee.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      05/10/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  17.0.4.1
//
//  Description:   HourlyEmployee object  
//
//********************************************************************
public class HourlyEmployee {
	private String sSN;
	private int weekNumber;
	private double hoursWorked;
	private double payRate;
	private double bonus;
	
	public HourlyEmployee(String sSN, int weekNumber, double hoursWorked, double payRate) {
		setSSN(sSN);
		setWeekNumber(weekNumber);
		setHoursWorked(hoursWorked);
		setPayRate(payRate);
		bonus = 0.0;
	}
	
	public void setSSN(String sSN) {
		this.sSN = sSN;
	}
	
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}
	
	public void setHoursWorked(double hoursWorked) {
		this.hoursWorked = hoursWorked;
	}
	
	public void setPayRate(double payRate) {
		this.payRate = payRate;
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

	public double getHoursWorked() {
	    return this.hoursWorked;
	}

	public double getPayRate() {
	    return this.payRate;
	}

	public double getBonus() {
	    return this.bonus;
	}
}
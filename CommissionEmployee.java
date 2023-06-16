//********************************************************************
//
//  Developer:     Marshal Pfluger
//
//  Project #:     Ten
//
//  File Name:     CommissionEmployee.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      05/10/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  17.0.4.1
//
//  Description:   CommissionEmployee object  
//
//********************************************************************
public class CommissionEmployee {
	private String sSN;
	private int weekNumber;
	private double grossSales;
	private double commissionRate;
	private double bonus;
	
	public CommissionEmployee(String sSN, int weekNumber, double grossSales, double commissionRate) {
		setSSN(sSN);
		setWeekNumber(weekNumber);
		setGrossSales(grossSales);
		setCommissionRate(commissionRate);
		this.bonus = 0.0;
	}
	
	public void setSSN(String sSN) {
		this.sSN = sSN;
	}
	
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}
	
	public void setGrossSales(double grossSales) {
		this.grossSales = grossSales;
	}
	
	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}
	
	public String getSSN() {
	    return this.sSN;
	}

	public int getWeekNumber() {
	    return this.weekNumber;
	}

	public double getGrossSales() {
	    return this.grossSales;
	}

	public double getCommissionRate() {
	    return this.commissionRate;
	}

	public double getBonus() {
	    return this.bonus;
	}

}
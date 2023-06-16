//********************************************************************
//
//  Developer:     Marshal Pfluger
//
//  Project #:     Ten
//
//  File Name:     PlusCommissionEmployee.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      05/10/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  17.0.4.1
//
//  Description:   PlusCommissionEmployee object  
//
//********************************************************************
public class PlusCommissionEmployee {
	private String sSN;
	private int weekNumber;
	private double baseSalary;
	private double grossSales;
	private double commissionRate;
	private double bonus;
	
	public PlusCommissionEmployee(String sSN, int weekNumber, double baseSalary, double grossSales, double commissionRate) {
		setSSN(sSN);
		setWeekNumber(weekNumber);
		setBaseSalary(baseSalary);
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
	
	public void setBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
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

	public double getBaseSalary() {
	    return this.baseSalary;
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
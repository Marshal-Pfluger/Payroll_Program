//********************************************************************
//
//  Developer:     Marshal Pfluger
//
//  Project #:     Ten
//
//  File Name:     PieceEmployee.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      05/10/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  17.0.4.1
//
//  Description:   PieceEmployee object  
//
//********************************************************************
public class PieceEmployee {
	private String sSN;
	private int weekNumber;
	private double pieceRate;
	private int numPieces;
	private double bonus;
	
	public PieceEmployee(String sSN, int weekNumber, double pieceRate, int numPieces) {
		setSSN(sSN);
		setWeekNumber(weekNumber);
		setPieceRate(pieceRate);
		setNumPieces(numPieces);
		this.bonus = 0.0;
	}

	public void setSSN(String sSN) {
		this.sSN = sSN;
	}
	
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}
	
	public void setPieceRate(double pieceRate) {
		this.pieceRate = pieceRate;
	}
	
	public void setNumPieces(int numPieces) {
		this.numPieces = numPieces;
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

	public double getPieceRate() {
	    return this.pieceRate;
	}

	public int getNumPieces() {
	    return this.numPieces;
	}

	public double getBonus() {
	    return this.bonus;
	}

}
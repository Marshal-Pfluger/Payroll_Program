//********************************************************************
//
//  Developer:     Marshal Pfluger
//
//  Project #:     Ten
//
//  File Name:     Employee.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      05/10/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  17.0.4.1
//
//  Description:   Employee object  
//
//********************************************************************
public class Employee {
	private String sSN;
	private String firstName;
	private String lastName;
	private String birthday;
	private String employeeType;
	private String departmentName;
	
	public Employee(String sSN,  String firstName, String lastName, String birthday, String employeeType, String departmentName) {
		setSSN(sSN);
		setFirstName(firstName);
		setLastName(lastName);
		setBirthday(birthday);
		setEmployeeType(employeeType);
		setDepartmentName(departmentName);
		
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
		
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
		
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
		
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
		
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		
	}
	public void setSSN(String sSN) {
		this.sSN = sSN;
	}
	
	public String getSSN() {
	    return this.sSN;
	}

	public String getFirstName() {
	    return this.firstName;
	}

	public String getLastName() {
	    return this.lastName;
	}

	public String getBirthday() {
	    return this.birthday;
	}

	public String getEmployeeType() {
	    return this.employeeType;
	}

	public String getDepartmentName() {
	    return this.departmentName;
	}
}
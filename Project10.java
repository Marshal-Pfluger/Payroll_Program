//********************************************************************
//
//  Developer:     Marshal Pfluger
//
//  Project #:     Ten
//
//  File Name:     Project10.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      05/10/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  17.0.4.1
//
//  Description:   Running class for project 10  
//
//********************************************************************
import java.sql.*;
import java.util.Scanner;

public class Project10 {
	// declare class variable
    private ConnectToOracleDB dbConnect;
    private Connection connection = null;
    private RunSQLQueries queryObj; 

    public static void main(String[] args) {
    	Project10 obj = new Project10();
    	obj.developerInfo();
    	obj.runDemo();
    }
    
	// ***************************************************************
	//
	// Method:      runDemo
	//
	// Description: 
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	// **************************************************************
    public void runDemo() {
    	createConnection();
    	runMainMenu();	
    }
    
	// ***************************************************************
	//
	// Method:      DisplayMainMenu
	//
	// Description: Sends the menu to print output to be displayed
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	// **************************************************************
    public void displayMainMenu() {
    	printOutput("***********MAIN MENU***********\n" +
    			    "1. Add an employee to the employee table\n" +
                    "2. Add payroll information for an employee\n" +
    			    "3. Increase base salary by 10% for all base-plus-commission employees\n" +
                    "4. If the employee's birthday is in the current month, add a $100.00 bonus\n" +
    			    "5. For all commission employees with gross sales over $10,000.00, add a $250.00 bonus\n" + 
                    "6. Display information about an employee\n" +
    			    "7. Exit\n");
    }
    
	// ***************************************************************
	//
	// Method:      runMainMenu
	//
	// Description: Contains switch statement to execute menu
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	// **************************************************************
    public void runMainMenu() {
    	String userChoice = "";
    	do {
    		displayMainMenu();
    	    userChoice = userChoiceString(1);
        	switch(userChoice) {
        	case "1":
        		addEmployee();
        		break;
        	case "2":
        		addPayrollInfo();
        		break;
        	case "3":
        		increaseBaseSalary();
        		break;
        	case "4":
        		addBirthdayBonus();
        		break;
        	case "5":
        		addSalesBonus();
        		break;
        	case "6":
        		displayEmployeeInfo();
        		break;
        	case "7":
        		commitChanges();
        		dbConnect.closeDBConnection();
        		break;
        	}
    	}while(!userChoice.equals("7"));  	
    }

	// ***************************************************************
	//
	// Method:      commitChanges
	//
	// Description: commits the changes made during session. 
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	// **************************************************************
	private void commitChanges() {
		printOutput("\nCommitting changes...");
		if (connection != null)
		{
			try {
				connection.commit();
			} catch (SQLException exp) {
		    	System.out.println("SQL Error Message: " + exp.getMessage());
		    } catch(Exception exp) {
		    	System.out.println("Failed to run query. \n" + exp.getMessage());
		    }
			
		}
	}

	// ***************************************************************
	//
	// Method:      Display Menu
	//
	// Description: Sends the menu to print output to be displayed
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	// **************************************************************
    public void displayEmployeeTypeMenu() {
    	printOutput("Please select the type of employee:\n"+
                    "1. Salaried_Employee\n" +
                    "2. Hourly_Employee\n" +
    			    "3. Piece_Employee\n" +
                    "4. Base_Plus_Commission_Employee\n" +
    			    "5. Commission_Employee\n");
    }
    
	// ***************************************************************
	//
	// Method:      runEmployeeMenu
	//
	// Description: Contains switch statement to execute menu of employee types
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	// **************************************************************
    public String runEmployeeMenu() {
    	String userChoice = "";
    	String employeeType = "";
    	displayEmployeeTypeMenu();
    	userChoice = userChoiceString(2);
        switch(userChoice) {
        case "1":
        	employeeType = "salariedEmployee";
        	break;
        case "2":
        	employeeType = "hourlyEmployee";
        	break;
        case "3":
        	employeeType = "pieceEmployee";
        	break;
        case "4":
        	employeeType = "basePlusCommissionEmployee";
        	break;
        case "5":
        	employeeType = "commissionEmployee";
        	break;

        	}
        return employeeType;
    	  	
    }

	// ***************************************************************
	//
	// Method:      createConnection
	//
	// Description: Establishes connections to database for program
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	// **************************************************************
	public void createConnection() 
	{
		try {
			dbConnect = new ConnectToOracleDB();
			dbConnect.loadDrivers();
			connection = dbConnect.connectDriver();
	
		    if (connection != null)
		    {
			   System.out.print("\nReceived success connection handle ");
			   System.out.println("in main TestApp");
		    }
		} catch(Exception exp) {
            System.out.println("Something terrible went wrong "  + exp);
			System.exit(1);
        }
		queryObj = new RunSQLQueries();
		queryObj.setConnection(connection);
		
	}
	
	// ***************************************************************
	//
	// Method:      addEmployee
	//
	// Description: adds employee to employee table 
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	// **************************************************************
    public void addEmployee() {
    	String employeeLookup = "";
    	// receive info from user get validated input
    	printOutput("Please enter employees SSN in form 123-45-6789: ");
    	String sSN = userChoiceString(4);
    	employeeLookup = employeeTypeLookup(sSN);
    	// If employee already exists do not continue
    	if (employeeLookup.equals("non")) {
        	printOutput("Please enter employees first name: ");
        	String firstName = userChoiceString(5);
        	printOutput("Please enter employees last name: ");
        	String lastName = userChoiceString(5);
        	printOutput("Please enter employees birthday in form yyyy-mm-dd: ");
        	String birthday = userChoiceString(4);
        	printOutput("Please enter employee type: ");
        	String employeeType = runEmployeeMenu();
        	printOutput("Please enter employee department name: ");
        	String departmentName = userChoiceString(5);
        	// create new employee object
        	Employee newEmployee = new Employee(sSN, firstName, lastName, birthday, employeeType, departmentName);
        	// send employee object to query method
        	queryObj.runAddEmployee(newEmployee);
    	}else {
    		printOutput("The employee already exists");
    	}

         }

	//***************************************************************
	//
	// Method:      addPayrollInfo
	//
	// Description: Calls correct query method to add to correct payroll table
    //              based on SSN which gets employee type. 
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	//**************************************************************
    public void addPayrollInfo()  {
    	// receive SSN from user
    	printOutput("Please enter employees SSN in form 123-45-6789: ");
        String sSN = userChoiceString(4);
        // look up employee to make sure they exist
        String employeeType = employeeTypeLookup(sSN);
        // Declare week number variable 
        int weekNumber = 0;
        // if employee does not exist do not get week number 
        if (!employeeType.equals("non")) {
            printOutput("Please enter week number: ");
            weekNumber = Integer.parseInt(userChoiceString(3));
        }
        // if employee is not found inform user
        if (employeeType.equals("non")) {
        	printOutput("Employee not found");
        // if employee is salaried get info, create salaried employee object, send to query method
       }else if (employeeType.equals("salariedEmployee")) {
        	printOutput("Please enter the salary of the employee");
        	double salary = Double.parseDouble(userChoiceString(3));
        	SalariedEmployee salariedEmployee = new SalariedEmployee(sSN, weekNumber, salary);
        	queryObj.runSalaryPayroll(salariedEmployee);
        // if employee is hourly get info, create hourly employee object, send to query method
        }else if (employeeType.equals("hourlyEmployee")) {
        	printOutput("Please enter the number of hours worked: ");
        	double hoursWorked = Double.parseDouble(userChoiceString(3));
        	printOutput("Please enter the pay rate per hour: ");
        	double payRate = Double.parseDouble(userChoiceString(3));
        	HourlyEmployee hourlyEmployee = new HourlyEmployee( sSN, weekNumber, hoursWorked, payRate);
        	queryObj.runHourlyPayroll(hourlyEmployee);
        // if employee is piece  get info, create piece employee object, send to query method
        }else if (employeeType.equals("pieceEmployee")) {
        	printOutput("Please enter the piece rate for the employee: ");
        	double pieceRate = Double.parseDouble(userChoiceString(3));
        	printOutput("Please enter the number of pieces for the employee: ");
        	int numPieces = Integer.parseInt(userChoiceString(3));
        	PieceEmployee pieceEmployee = new PieceEmployee(sSN, weekNumber, pieceRate, numPieces);
        	queryObj.runPiecePayroll(pieceEmployee);
        // if employee is basePlus get info, create basePlus employee object, send to query method
        }else if(employeeType.equals("basePlusCommissionEmployee")) {
        	printOutput("Please enter the base salary of the employee: ");
        	double baseSalary = Double.parseDouble(userChoiceString(3));
        	printOutput("Please enter the gross sales of the employee: ");
        	double grossSales = Double.parseDouble(userChoiceString(3));
        	printOutput("Please enter the commission rate of the employee in decimal form: ");
        	double plusCommissionRate = Double.parseDouble(userChoiceString(5));
        	PlusCommissionEmployee basePlusCommissionEmployee = new PlusCommissionEmployee(sSN, weekNumber, baseSalary, grossSales, plusCommissionRate);
        	queryObj.runBasePlusCommissionPayroll(basePlusCommissionEmployee);
        // if employee is commission get info, create commission employee object, send to query method
        }else if(employeeType.equals("commissionEmployee")) {
        	printOutput("Please enter the gross sales of the employee: ");
        	double grossSales = Double.parseDouble(userChoiceString(3));
        	printOutput("Please enter the commission rate of the employee in decimal form: ");
        	double commissionRate = Double.parseDouble(userChoiceString(5));
        	CommissionEmployee commissionEmployee = new CommissionEmployee(sSN, weekNumber, grossSales, commissionRate);
        	queryObj.runCommissionPayroll(commissionEmployee);
        }
    }
    
	//***************************************************************
	//
	// Method:      increaseBaseSalary
	//
	// Description: Calls the query method to increase base salary for all 
    //              commission employees
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	//**************************************************************
    public void increaseBaseSalary() {
    	queryObj.runIncreaseBaseSalary();
    }
    
	//***************************************************************
	//
	// Method:      addBirtdayBonus
	//
	// Description: Calls the query method to add birthday bonus to employee
    //              if their birthday is in the current month
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	//**************************************************************
    public void addBirthdayBonus() {
    	// declare table info variable 
    	String tableInfo = "";
    	// get SSN from user and validate input
    	printOutput("Please enter employees SSN in form 123-45-6789: ");
    	String sSN = userChoiceString(4);
    	String employeeType = employeeTypeLookup(sSN);
    	// if employee is not found inform user else add correct bonus
    	if (employeeType.equals("non")) {
    		printOutput("Employee not found");
        }else if (employeeType.equals("salariedEmployee")) {
    		tableInfo = "Salaried_Employees";
    	}else if(employeeType.equals("hourlyEmployee")) {
    		tableInfo = "Hourly_Employees";
    	}else if(employeeType.equals("pieceEmployee")) {
    		tableInfo = "Piece_Employees";
    	}else if(employeeType.equals("basePlusCommissionEmployee")) {
    		tableInfo = "Plus_Commission_Employees";
    	}else if(employeeType.equals("commissionEmployee")) {
    		tableInfo = "Commission_Employees";
    	}
    	// call method to run query 
    	if(!employeeType.equals("non"))
    	queryObj.runAddBirthdayBonus(tableInfo, sSN);
    }

	//***************************************************************
	//
	// Method:      addSalesBonus
	//
	// Description: Calls query method to add sales bonus to commission
    //              employees who qualify
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	//**************************************************************
    public void addSalesBonus() {
    	queryObj.runAddSalesBonus();
    }
    
	//***************************************************************
	//
	// Method:      displayEmployeeInfo
	//
	// Description: Calls query method to display all info about an 
    //              employee based on their SSN 
	//
	// Parameters:  None
	//
	// Returns:     N/A
	//
	//**************************************************************
    public void displayEmployeeInfo() {
    	// receive SSN from user and check that user exists and input is valid
    	printOutput("Please enter employees SSN in form 123-45-6789: ");
    	String sSN = userChoiceString(4);
    	String employeeType = employeeTypeLookup(sSN);
    	// if employee does not exist do not run method further 
    	if (employeeType.equals("non")) {
    		printOutput("Employee not found");
    	}else {
    		queryObj.runDisplayEmployee(sSN);
    	}
    	// if employee exists call correct display method 
    	if (employeeType.equals("salariedEmployee")) {
    		queryObj.runDisplaySalariedEmployee(sSN);
    	}else if (employeeType.equals("hourlyEmployee")) {
    		queryObj.runDisplayHourlyEmployee(sSN);
    	}else if (employeeType.equals("pieceEmployee")) {
    		queryObj.runDisplayPieceEmployee(sSN);
    	}else if(employeeType.equals("basePlusCommissionEmployee")) {
    		queryObj.runDisplayBasePlusCommissionEmployee(sSN);
    	}else if(employeeType.equals("commissionEmployee")) {
    		queryObj.runDisplayCommissionEmployee(sSN);
    	}
    }
    
	//***************************************************************
	//
	// Method:      employeeTypeLookup
	//
	// Description: Calls query method to look up the type of employee
    //              associated with the SSN provided
	//
	// Parameters:  None
	//
	// Returns:     String employeeType
	//
	//**************************************************************
    public String employeeTypeLookup(String sSN) {
    	String employeeType = queryObj.runEmployeeTypeLookup(sSN);
    	return employeeType;
    }
    
  //***************************************************************
    //
    //  Method:       printOutput
    // 
    //  Description:  Prints the output from the user selected operation 
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public void printOutput(String outputString) {
    	// print nice looking output for user
    	System.out.println("\n*************************\n");
    	System.out.println(outputString);
    	}// End printOutput method
    	
    //***************************************************************
    //
    //  Method:       userChoiceString
    // 
    //  Description:  takes an string from user. 
    //
    //  Parameters:   boolean questionType
    //
    //  Returns:      String userChoiceString
    //
    //**************************************************************
    public String userChoiceString(int questionType) {
    	boolean validInput = false; 
    	String userChoiceString = "";
    	// Use Scanner to receive user input
    	Scanner userInput = new Scanner(System.in);
    	do {
    		//Get user input 
        	userChoiceString = userInput.nextLine();
            //Send user input to input validation
            validInput = inputValidation(userChoiceString, questionType);
            }while(!validInput);
    	// Close scanner when program exits. 
    	if (userChoiceString == "7") {
    		userInput.close();
    		}
    	return userChoiceString;
    	}// End userChoice
    	
    //***************************************************************
    //
    //  Method:       inputValidation
    // 
    //  Description:  validates the input of the program depending on what is being asked.  
    //
    //  Parameters:   String userChoiceString, boolean questionType
    //
    //  Returns:      boolean validity
    //
    //**************************************************************
    public boolean inputValidation(String userChoiceString, int questionType) {
    	// declare variable to hold bool validity
        boolean validInvalid = false;
        // There are two different types of questions to run validity for, split here
        if(questionType == 1) {
        	int mainMenu = 0;
        	// If input is non numeric this will handle exception and indicate invalid input. 
        	try {
        		mainMenu = Integer.parseInt(userChoiceString);
        	}catch (NumberFormatException e) {
        		validInvalid = false;
        	}
        	// If input is in the correct range will return valid
        	if(mainMenu > 0 && mainMenu <= 7) {
        		validInvalid = true;
        	}
            // If it is a student answer do not allow non-numeric answers. 
        }else if(questionType == 2) {
        	int payrollMenu = 0;
        	// If input is non numeric this will handle exception and indicate invalid input. 
        	try {
        		payrollMenu = Integer.parseInt(userChoiceString);
        	}catch (NumberFormatException e) {
        		validInvalid = false;
        	}
        	// If input is in the correct range will return valid
        	if(payrollMenu > 0 && payrollMenu <= 5) {
        		validInvalid = true;
        	}
        }else if (questionType == 3) {
        	validInvalid = true;
        	// If input is non numeric this will handle exception and indicate invalid input. 
        	try {
        		Double.parseDouble(userChoiceString);
        	}catch (NumberFormatException e) {
        		validInvalid = false;
        	}
        // for date and SSN validation. 
        }else if (questionType == 4) {
        	validInvalid = true;
        	// If input is non numeric this will handle exception and indicate invalid input. 
        	try {
        		String[] parts = userChoiceString.split("-");
                String combined = parts[0] + parts[1] + parts[2];
                Integer.parseInt(combined);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            	validInvalid = false;
        	}
        	}else if(questionType == 5) {
        		double percentCheck = 0;
        		validInvalid = true;
        		// If input is non numeric this will handle exception and indicate invalid input. 
        		try {
        			percentCheck = Double.parseDouble(userChoiceString);
        		}catch (NumberFormatException e) {
        			validInvalid = false;
        		}
        		// If input is in the correct range will return valid
        		if(percentCheck <= 1) {
        			validInvalid = true;
        		}
        	}
        	// If the input is invalid, inform user. 
        	if(!validInvalid)
        		printOutput("Input Error, please try again.");
        	return validInvalid;
        }

    	//***************************************************************
    	//
    	//  Method:       developerInfo (Non Static)
    	// 
    	//  Description:  The developer information method of the program
    	//
    	//  Parameters:   None
    	//
    	//  Returns:      N/A 
    	//
    	//**************************************************************
    	    public void developerInfo()
    	    {
    	    	System.out.println("Name:    Marshal Pfluger");
    		    System.out.println("Course:  COSC 4301 Modern Programming");
    		    System.out.println("Project: Ten\n\n");
    		    } // End of the developerInfo method
}
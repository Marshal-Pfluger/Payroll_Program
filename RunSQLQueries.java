//********************************************************************
//
//  Developer:     Marshal Pfluger
//
//  Project #:     Ten
//
//  File Name:     RunSQLQueries.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      05/10/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  17.0.4.1
//
//  Description:   class runs queries for project10 
//
//********************************************************************

import java.sql.*;
import java.util.Formatter; 

// Start class
public class RunSQLQueries
{
	// Declare class variables 
	private Connection connection;
    private ResultSet resultSet = null;
    
    // Constructor for class
	public RunSQLQueries()
	{
		connection = null;
		
	}
	
	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}

    //*************************************************************************************EMPLOYEE METHODS******************************************************************************************************************************************
	//***************************************************************
	//
	// Method:      runAddEmployee
	//
	// Description: Runs query to insert new employee in employees table             
	//
	// Parameters:  employee Object
    //	
	// Returns:     N/A
	//
	//**************************************************************
	public void runAddEmployee(Employee newEmployee) { 
		// Declare query string
		String sql = "INSERT INTO employees (SSN, First_Name, Last_Name, Birthday, Employee_Type, Department_Name) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			// initialize prepared staatement with sql query 
			PreparedStatement stmt = connection.prepareStatement(sql);
			// set ssn field
    	    stmt.setString(1, newEmployee.getSSN());
    	    // set first name field
            stmt.setString(2, newEmployee.getFirstName());
            // set last name field
            stmt.setString(3, newEmployee.getLastName());
            // set birthday field
            stmt.setDate(4, Date.valueOf(newEmployee.getBirthday()));
            // set employee type field
            stmt.setString(5, newEmployee.getEmployeeType());
            // set department name field
            stmt.setString(6, newEmployee.getDepartmentName());
            // execute the query 
            // indicate successfully added employee
			int rowsAffected = stmt.executeUpdate();
			printOutput(rowsAffected + " row(s) added to Employees");
			// handle sql exception as well as any other that may occur
            } catch (SQLException exp) {
            	System.out.println("SQL Error Message 1: " + exp.getMessage());
            } catch(Exception exp) {
                System.out.println("Failed to run query. \n" + exp.getMessage());
            }
		}
	
	//***************************************************************
	//
	// Method:      runDisplayEmployee
	//
	// Description: Runs the query to display Information from employees table
	//              for specific SSN
	//
	// Parameters:  String SSN
	//
	// Returns:     N/A
	//
	//**************************************************************
	public void runDisplayEmployee(String sSN) {
		// Declare formatter object
		Formatter fmt  = new Formatter();
		// Declare query variable 
		String sql = "SELECT * FROM Employees WHERE SSN = ?";
		// Execute the query and get data from DB
		try {
			printOutput("\nFetching Employee Records...");
			// Declare prepared statment and create connection to db
			PreparedStatement stmt = connection.prepareStatement(sql);
			// Set social into query
			stmt.setString(1, sSN);
			// Initialize resultSet to get output from table
			resultSet = stmt.executeQuery();
			if(resultSet.next()) {
				// Get SSN
				String sSNDisplay = resultSet.getString("SSN");
				// Get first name 
				String firstName = resultSet.getString("First_Name");
				// Get last name 
				String lastName = resultSet.getString("Last_Name");
				// Get birthday 
				Date birthday = resultSet.getDate("Birthday");
				// Get employee type
				String employeeType = resultSet.getString("Employee_Type");
				// Get department name 
				String departmentName = resultSet.getString("Department_Name");
				// Format output to send to print method
				fmt.format("%15s %15s %15s %15s %27s %17s\n", "|SSN|", "|First_Name|", "|Last_Name|", "|Birthday|", "|Employee_Type|", "|Department_Name|");
				fmt.format("%15s %15s %15s %15s %27s %17s\n", sSNDisplay, firstName, lastName, birthday, employeeType, departmentName);
				}
		    // Handle exceptions for sql and all other
			} catch (SQLException exp) {
				System.out.println("SQL Error Message: " + exp.getMessage());
			} catch(Exception exp) {
				System.out.println("Failed to run query. \n" + exp.getMessage());
			} 
        // Send formatted output to print method
		printOutput(fmt + "");
		// Close formatter
		fmt.close();
		}
	
	//*************************************************************************************SALARY_EMPLOYEE METHODS******************************************************************************************************************************************	
	//***************************************************************
	//
	// Method:      runSalaryPayroll
	//
	// Description: Runs the query to add employee to salary employees table
	//
	// Parameters:  SalariedEmployee Object
    //	
	// Returns:     N/A
	//
	//**************************************************************
	public void runSalaryPayroll(SalariedEmployee salariedEmployee) {
		// Declare query variable 
		String sql = "INSERT INTO Salaried_Employees (SSN, Week_Number, Weekly_Salary, Bonus) VALUES (?, ?, ?, ?)";
		try {
			// declare prepared statement with query 
			PreparedStatement stmt = connection.prepareStatement(sql);
		    // set SSN 
			stmt.setString(1, salariedEmployee.getSSN());
			// set Week Number 
			stmt.setInt(2, salariedEmployee.getWeekNumber());
			// set salary 
			stmt.setDouble(3, salariedEmployee.getSalary());
			// Set bonus amount 
			stmt.setDouble(4, salariedEmployee.getBonus());
			// execute query and receive number of rows affected
			int rowsAffected = stmt.executeUpdate();
			printOutput(rowsAffected + " row(s) added to SalariedEmployee");
			// handle sql exceptions and all others that may be thrown
			} catch (SQLException exp) {
			 	   System.out.println("SQL Error Message 1: " + exp.getMessage());
		 	} catch(Exception exp) {
		        System.out.println("Failed to run query. \n" + exp.getMessage());
		    }
		}
	
	//***************************************************************
	//
	// Method:      runDisplaySalariedEmployee
	//
	// Description: Runs the query to display information of salary employee based on SSN
	//
	// Parameters:  String SSN
    //
	// Returns:     N/A
	//
	//**************************************************************
	public void runDisplaySalariedEmployee(String sSN) {
		// Declare formatter object
		Formatter fmt  = new Formatter();
		// Declare query variable
		String sql = "SELECT * FROM Salaried_Employees WHERE SSN = (?) AND Week_Number = (SELECT MAX(Week_Number) FROM Salaried_Employees WHERE SSN = (?))";
		try {
			// declare prepared statment with query
            PreparedStatement stmt = connection.prepareStatement(sql);
            // set SSN in correct places in query
            stmt.setString(1, sSN);
            stmt.setString(2, sSN);
            // Initialize resultSet to get output from table
		    resultSet = stmt.executeQuery();
		    // if there is data from the db proceed
		    if(resultSet.next()) {
		    	// Get week number 
		    	int weekNumber = resultSet.getInt("Week_Number");
		    	// Get weekly salary
			    double weeklySalary = resultSet.getDouble("Weekly_Salary");
			    // Get Bonus
			    double bonus = resultSet.getDouble("Bonus");
			    // build formatted output. 
				fmt.format("%15s %15s %15s %15s\n", "|Week_Number|", "|Weekly_Salary|", "|Bonus|",  "|Gross_Pay|");
				fmt.format("%15s %15s %15s %15s\n", weekNumber, weeklySalary, bonus, weeklySalary + bonus);
				}
		    // Handle exceptions for sql and all other
		    } catch (SQLException exp) {
		    	System.out.println("SQL Error Message: " + exp.getMessage());
		    } catch(Exception exp) {
		    	System.out.println("Failed to run query. \n" + exp.getMessage());
		    }
		// send formatted output to output printer method
		printOutput(fmt + "");
		// Close formatter
		fmt.close();
		}
	
	//*************************************************************************************HOURLY_EMPLOYEE METHODS******************************************************************************************************************************************
	//***************************************************************
	//
	// Method:      runHourlyPayroll
	//
	// Description: Runs the update query
	//
	// Parameters:  Query to run
    //	
	// Returns:     The update count
	//
	//**************************************************************
	public void runHourlyPayroll(HourlyEmployee hourlyEmployee) {
		// declare query variable
		String sql = "INSERT INTO Hourly_Employees (SSN, Week_Number, Hours_Worked, Pay_Rate, Bonus) VALUES (?, ?, ?, ?, ?)";
		// create statement connection 
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			// Set values in query 
			stmt.setString(1, hourlyEmployee.getSSN());
			stmt.setInt(2, hourlyEmployee.getWeekNumber());
			stmt.setDouble(3, hourlyEmployee.getHoursWorked());
			stmt.setDouble(4, hourlyEmployee.getPayRate());
			stmt.setDouble(5, hourlyEmployee.getBonus());
			// execute query and receive response form db
			int rowsAffected = stmt.executeUpdate();
			printOutput(rowsAffected + " row(s) added to HourlyEmployee");
			// Handle exceptions for sql and all other
			} catch (SQLException exp) {
			 	System.out.println("SQL Error Message 1: " + exp.getMessage());
		 	} catch(Exception exp) {
		        System.out.println("Failed to run query. \n" + exp.getMessage());   
		}
	}
	//***************************************************************
	//
	// Method:      runDisplayHourlyEmployee
	//
	// Description: Runs the query to display hourly employee info with SSN
	//
	// Parameters:  String SSN
    //
	// Returns:     N/A
	//
	//**************************************************************
	public void runDisplayHourlyEmployee(String sSN) {
		// declare formatter object 
		Formatter fmt  = new Formatter();
		// declare query variable 
		String sql = "SELECT * FROM Hourly_Employees WHERE SSN = (?) AND Week_Number = (SELECT MAX(Week_Number) FROM Hourly_Employees WHERE SSN = (?))";
		// create statement connection
		try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            // Set values in query 
            stmt.setString(1, sSN);
            stmt.setString(2, sSN);
            // execute query and recive response form db
		    resultSet = stmt.executeQuery();
		    if(resultSet.next()) {
		    	// store all column responses
		    	int weekNumber = resultSet.getInt("Week_Number");
			    double nonOvHoursWorked = resultSet.getDouble("Hours_Worked");
			    double payRate = resultSet.getDouble("Pay_Rate");
			    double bonus = resultSet.getDouble("Bonus");
			    double overtimeHours = 0;
			    // handle overtime situation
			    if (nonOvHoursWorked > 40) {
			    	overtimeHours = nonOvHoursWorked - 40;
			    	nonOvHoursWorked = 40;
					}
			    // Format output to send to output method
				fmt.format("%15s %15s %15s %15s %15s %15s\n", "|Week_Number|", "|Non_Overtime_Hours_Worked|", "|Overtime_Hours|", "|Pay_Rate|", "|Bonus|",  "|Gross_Pay|");
				fmt.format("%13s %15s %20s %20s %18s %15s\n", weekNumber, nonOvHoursWorked, overtimeHours, payRate, bonus, (nonOvHoursWorked * payRate) + (overtimeHours * (payRate * 1.5)));
			    } 
		    // handle sql exceptions and all others
		    } catch (SQLException exp) {
		    	System.out.println("SQL Error Message: " + exp.getMessage());
		    } catch(Exception exp) {
		    	System.out.println("Failed to run query. \n" + exp.getMessage());
		    }
		// send output to output method
		printOutput(fmt + "");
		// Close formatter
		fmt.close();
		}
	
	//*************************************************************************************Piece_EMPLOYEE METHODS******************************************************************************************************************************************
	//***************************************************************
	//
	// Method:      runPiecePayroll
	//
	// Description: Runs the query to add piece employee in payroll table 
	//
	// Parameters:  PieceEmployee pieceEmployee
    //	
	// Returns:     N/A
	//
	//**************************************************************
	public void runPiecePayroll(PieceEmployee pieceEmployee) {
		// Declare sql query variable 
		String sql = "INSERT INTO Piece_Employees (SSN, Week_Number, Piece_Rate, Number_Pieces, Bonus) VALUES (?, ?, ?, ?, ?)";
		// create prepare statment 
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			// Set values in query 
			stmt.setString(1, pieceEmployee.getSSN());
			stmt.setInt(2, pieceEmployee.getWeekNumber());
			stmt.setDouble(3, pieceEmployee.getPieceRate());
			stmt.setDouble(4, pieceEmployee.getNumPieces());
			stmt.setDouble(5, pieceEmployee.getBonus());
			// execute query and receive response from db
			int rowsAffected = stmt.executeUpdate();
			printOutput(rowsAffected + " row(s) added to PieceEmployee");
			// handle sql exceptions and all others
			} catch (SQLException exp) {
			 	   System.out.println("SQL Error Message 1: " + exp.getMessage());
		 	} catch(Exception exp) {
		        System.out.println("Failed to run query. \n" + exp.getMessage());
		    }
		}
	
	//***************************************************************
	//
	// Method:      runDisplayPieceEmployee
	//
	// Description: Runs the query to display piece employee info
	//
	// Parameters:  String SSN 
    //
	// Returns:     N/A
	//
	//**************************************************************
	public void runDisplayPieceEmployee(String sSN) {
		// Declare formatter object
		Formatter fmt  = new Formatter();
		// declare sql query variable 
		String sql = "SELECT * FROM Piece_Employees WHERE SSN = (?) AND Week_Number = (SELECT MAX(Week_Number) FROM Piece_Employees WHERE SSN = (?))";
		// Execute the query and get our result
		try {
			// declare prepare statement 
            PreparedStatement stmt = connection.prepareStatement(sql);
            // set values in query 
            stmt.setString(1, sSN);
            stmt.setString(2, sSN);
            // execute query and receive response from db
		    resultSet = stmt.executeQuery();
		    if(resultSet.next()) {
		    	// store results from db
			    int weekNumber = resultSet.getInt("Week_Number");
			    double pieceRate = resultSet.getDouble("Piece_Rate");
			    double numberPieces = resultSet.getDouble("Number_Pieces");
			    double bonus = resultSet.getDouble("Bonus");
			    // format results from db 
				fmt.format("%15s %15s %15s %15s %15s\n", "|Week_Number|", "|Piece_Rate|", "|Number_Pieces|", "|Bonus|",  "|Gross_Pay|");
				fmt.format("%15s %15s %15s %15s %15s\n", weekNumber, pieceRate, numberPieces, bonus, (pieceRate * numberPieces));
				}
		    // handle exceptions for sql and others
		    } catch (SQLException exp) {
		    	System.out.println("SQL Error Message: " + exp.getMessage());
		    } catch(Exception exp) {
		    	System.out.println("Failed to run query. \n" + exp.getMessage());
		    }
		// send formatted output to method to print
		printOutput(fmt + "");
		// Close formatter
		fmt.close();
		}
	
	//*************************************************************************************BASE_PLUS_COMMISSION_EMPLOYEE METHODS******************************************************************************************************************************************
	//***************************************************************
	//
	// Method:      runBasePlusCommissionPayroll
	//
	// Description: Runs the query to add base plus commission payroll table info
	//
	// Parameters:  PlusCommissionEmployee basePlusCommissionEmployee
    //	
	// Returns:     N/A
	//
	//**************************************************************
	public void runBasePlusCommissionPayroll(PlusCommissionEmployee basePlusCommissionEmployee) {
		// delcare sql query variable 
		String sql = "INSERT INTO Plus_Commission_Employees (SSN, Week_Number, Gross_Sales, Commission_Rate, Base_Salary, Bonus) VALUES (?, ?, ?, ?, ?, ?)";
		// declare prepare statement 
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			// set values in query 
			stmt.setString(1, basePlusCommissionEmployee.getSSN());
			stmt.setInt(2, basePlusCommissionEmployee.getWeekNumber());
			stmt.setDouble(3, basePlusCommissionEmployee.getGrossSales());
			stmt.setDouble(4, basePlusCommissionEmployee.getCommissionRate());
			stmt.setDouble(5, basePlusCommissionEmployee.getBaseSalary());
			stmt.setDouble(6, basePlusCommissionEmployee.getBonus());
			// execute query and receive result from db
			int rowsAffected = stmt.executeUpdate();
			printOutput(rowsAffected + " row(s) added to PlusCommissionEmployee");
			// handle sql exception and all others
			}catch (SQLException exp) {
				System.out.println("SQL Error Message 1: " + exp.getMessage());
			} catch(Exception exp) {
				System.out.println("Failed to run query. \n" + exp.getMessage());
			}
		}
	
	//***************************************************************
	//
	// Method:      runDisplayBasePlusCommissionEmployee
	//
	// Description: Runs the query to display info of employee based on SSN
	//
	// Parameters:  String SSN
    //
	// Returns:     N/A
	//
	//**************************************************************
	public void runDisplayBasePlusCommissionEmployee(String sSN) {
		// declare format specifier object
		Formatter fmt  = new Formatter();
		// declare sql query variable 
		String sql = "SELECT * FROM Plus_Commission_Employees WHERE SSN = (?) AND Week_Number = (SELECT MAX(Week_Number) FROM Plus_Commission_Employees WHERE SSN = (?))";
		
		try {
			// declare prepare statement 
            PreparedStatement stmt = connection.prepareStatement(sql);
            // set values in query 
            stmt.setString(1, sSN);
            stmt.setString(2, sSN);
            // execute query and get result from db
		    resultSet = stmt.executeQuery();
		    if(resultSet.next()) {
		    	// receive results from db and store 
			    int weekNumber = resultSet.getInt("Week_Number");
			    double grossSales = resultSet.getDouble("Gross_Sales");
			    double commissionRate = resultSet.getDouble("Commission_Rate");
			    double baseSalary = resultSet.getDouble("Base_Salary");
			    double bonus = resultSet.getDouble("Bonus");
			    // formatt results from db
				fmt.format("%15s %15s %15s %15s %15s %15s\n", "|Week_Number|", "|Base_Salary|", "|Gross_Sales|", "|Commission_Rate|", "|Bonus|",  "|Gross_Pay|");
				fmt.format("%15s %15s %15s %15s %15s %15s\n", weekNumber, baseSalary, grossSales, commissionRate, bonus, (grossSales * commissionRate) + bonus + baseSalary);
				}
		    // handle exceptions for sql and all others
		    } catch (SQLException exp) {
		    	System.out.println("SQL Error Message: " + exp.getMessage());
		    } catch(Exception exp) {
		    	System.out.println("Failed to run query. \n" + exp.getMessage());
		    }
		// send formatted output to output method
		printOutput(fmt + "");
		// Close formatter
		fmt.close();
		}
	
	//*************************************************************************************COMMISSION_EMPLOYEE METHODS******************************************************************************************************************************************
	//***************************************************************
	//
	// Method:      runCommissionPayroll
	//
	// Description: Runs the query to add employee to commission table 
	//
	// Parameters:  CommissionEmployee commissionEmployee
    //	
	// Returns:     N/A
	//
	//**************************************************************
	public void runCommissionPayroll(CommissionEmployee commissionEmployee) {
		// declare sql query variable 
		String sql = "INSERT INTO Commission_Employees (SSN, Week_Number, Gross_Sales, Commission_Rate, Bonus) VALUES (?, ?, ?, ?, ?)";
		// declare prepare statement 
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			// set query values
			stmt.setString(1, commissionEmployee.getSSN());
			stmt.setInt(2, commissionEmployee.getWeekNumber());
			stmt.setDouble(3, commissionEmployee.getGrossSales());
			stmt.setDouble(4, commissionEmployee.getCommissionRate());
			stmt.setDouble(5, commissionEmployee.getBonus());
			// execute query and receive from db
			int rowsAffected = stmt.executeUpdate();
			printOutput(rowsAffected + " row(s) added to CommissionEmployee");
			// handle all exceptions
			} catch (SQLException exp) {
			 	   System.out.println("SQL Error Message 1: " + exp.getMessage());
		 	} catch(Exception exp) {
		        System.out.println("Failed to run query. \n" + exp.getMessage());
		     }
		}
	
	//***************************************************************
	//
	// Method:      runDisplayCommissionEmployee
	//
	// Description: Runs the query to display info about commission employee with SSN
	//
	// Parameters:  String SSN
    //
	// Returns:     N/A
	//
	//**************************************************************
	public void runDisplayCommissionEmployee(String sSN) {
		// declare format specifier object
		Formatter fmt  = new Formatter();
		// declare query variable 
		String sql = "SELECT * FROM Commission_Employees WHERE SSN = (?) AND Week_Number = (SELECT MAX(Week_Number) FROM Commission_Employees WHERE SSN = (?))";
		try {
			// declare prepare statement 
            PreparedStatement stmt = connection.prepareStatement(sql);
            // set values in query 
            stmt.setString(1, sSN);
            stmt.setString(2, sSN);
            // execute query and receive from db
		    resultSet = stmt.executeQuery();
		    if(resultSet.next()) {
		    	// store info from db
			    int weekNumber = resultSet.getInt("Week_Number");
			    double grossSales = resultSet.getDouble("Gross_Sales");
			    double commissionRate = resultSet.getDouble("Commission_Rate");
			    double bonus = resultSet.getDouble("Bonus");
			    // format output 
				fmt.format("%15s %15s %15s %15s %15s\n", "|Week_Number|", "|Gross_Sales|", "|Commission_Rate|", "|Bonus|",  "|Gross_Pay|");
				fmt.format("%15s %15s %15s %15s %15s\n", weekNumber, grossSales, commissionRate, bonus, (grossSales * commissionRate) + bonus);
		    }    
		    } catch (SQLException exp) {
		    	System.out.println("SQL Error Message: " + exp.getMessage());
		    } catch(Exception exp) {
		    	System.out.println("Failed to run query. \n" + exp.getMessage());
		    }
		// send formatted output to output method
		printOutput(fmt + "");
		// Close formatter
		fmt.close();
		}
	
	//*************************************************************************************OTHER RUN QUERY METHODS******************************************************************************************************************************************
	//***************************************************************
	//
	// Method:      runEmployeeTypeLookup
	//
	// Description: Calls query method to look up the type of employee
    //              associated with the SSN provided
	//
	// Parameters:  String sSN
	//
	// Returns:     N/A
	//
	//**************************************************************
    public String runEmployeeTypeLookup(String sSN) {
    	// declare look up variable 
    	String employeeType = "";
    	// declare query variable 
    	String query = "SELECT Employee_Type FROM EMPLOYEES WHERE SSN = ?";
    	try{
    		// declare prepare statement 
    		PreparedStatement stmt = connection.prepareStatement(query);
    		// set values in query 
    		stmt.setString(1, sSN);
    		// execute query and receive from db
    		ResultSet results = stmt.executeQuery();
    		// if employee exists return type 
    		if (results.next()) {
    			employeeType = results.getString("Employee_Type");
    		}else {
    			employeeType = "non";
    		}
    	// handle all exceptions	
    	}catch (SQLException exp) {
    		System.out.println("SQL Error Message 1: " + exp.getMessage());
     	} catch(Exception exp) {
            System.out.println("Failed to run query. \n" + exp.getMessage());
         }
    	return employeeType;
    }
    
	//***************************************************************
	//
	// Method:      runIncreaseBaseSalary
	//
	// Description: Runs the query to increase all base plus commission 
	//              employees base salary
	//
	// Parameters:  N/A
    //	
	// Returns:     N/A
	//
	//**************************************************************
	public void runIncreaseBaseSalary()
	{
		// declare counter variable
		int count= 0;
		// declare sql query variable
		String sql = "UPDATE Plus_Commission_Employees SET Base_Salary = Base_Salary * 1.1 WHERE Week_Number = (SELECT MAX(Week_Number) FROM Plus_Commission_Employees)";
		try {
			// declare prepare statement 
			PreparedStatement stmt = connection.prepareStatement(sql);
			// execute query and store count changed
			count = stmt.executeUpdate();
	    // handle all exceptions
		} catch (SQLException exp) {
			System.err.println("SQL Error Message 1: " + exp.getMessage());
		} catch(Exception exp) {
			System.err.println("Failed to run query. \n" + exp.getMessage());
		}
		if (count == 0) {
			printOutput("No employees were updated");
		}else {
			printOutput("Increased base salary for " + count + " Employees");
		}
	}

	//***************************************************************
	//
	// Method:      runAddBirthdayBonus
	//
	// Description: Runs the query to add a birthday bonus if the employee 
	//              has birthday in current month
	//
	// Parameters:  String tableInfo, String sSN
    //	
	// Returns:     N/A
	//
	//**************************************************************
	public void runAddBirthdayBonus(String tableInfo, String sSN)
	{
		// declare counter variable 
        int count = 0;
        // declare query variable 
        String sql = "UPDATE " + tableInfo + " SET Bonus = Bonus + 100.0 WHERE Week_Number = (SELECT MAX(Week_Number) FROM " + tableInfo + " WHERE "  + sSN +  " IN (SELECT " + sSN +  " FROM Employees WHERE EXTRACT(MONTH FROM birthday) = EXTRACT(MONTH FROM SYSDATE) AND Bonus < 100))";
		try {
			// declare prepared statement 
			PreparedStatement stmt = connection.prepareStatement(sql);
			// execute query and receive from db
			count = stmt.executeUpdate();
            printOutput(count + " row(s) updated with birthday bonus");
        // handle all exceptions
		} catch (SQLException exp) {
			System.err.println("SQL Error Message 1: " + exp.getMessage());
		} catch(Exception exp) {
			System.err.println("Failed to run query. \n" + exp.getMessage());
		}
		// if employee does not exist inform user
		if (count == 0) {
			printOutput("Employee does not have a birthday this month");
		}
	}
	
	//***************************************************************
	//
	// Method:      runAddSalesBonus
	//
	// Description: Runs the query to add sales bonus to commission employees 
	//              who have sold correct amount
	//
	// Parameters:  N/A
    //	
	// Returns:     N/A
	//
	//**************************************************************
	public void runAddSalesBonus() {
		// declare counter variable 
		int count = 0;
		// declare sql query variable 
        String sql = "UPDATE Commission_Employees SET Bonus = Bonus + 250.0 WHERE Gross_Sales > 10000.00 AND Bonus < 250.00 AND Week_Number = (SELECT MAX(Week_Number) FROM Commission_Employees)";
		try {
			// declare prepare statement 
            PreparedStatement prepStatement = connection.prepareStatement(sql);
            // execute query and receive from db
			count = prepStatement.executeUpdate();
			printOutput(count + " row(s) updated with sales bonus");
		// handle all exceptions
		} catch (SQLException exp) {
			System.err.println("SQL Error Message 1: " + exp.getMessage());
		} catch(Exception exp) {
			System.err.println("Failed to run query. \n" + exp.getMessage());
		}
	} 
	
	//*************************************************************************************UTILITY METHODS******************************************************************************************************************************************
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
    		//System.out.println("\n*************************\n");
    		System.out.println(outputString);
    	}// End printOutput method
    	
}
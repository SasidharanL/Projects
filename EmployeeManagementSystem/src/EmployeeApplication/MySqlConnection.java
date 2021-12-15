package EmployeeApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class MySqlConnection {
	
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet resultSet = null;
	static PreparedStatement preparedStatement = null;
	static Scanner scanner = new Scanner(System.in);
	
	public static Connection  establishConnection () {
		
		try {
			// Connection to database
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","$asi9865Sasi");
			
		} catch(Exception exception) {
			System.out.println(exception);
		}
		
		return connection;
	}

	public static void saveDate(ArrayList<ManageEmployee> detailsArray) throws SQLException {
		
		
		if(detailsArray.size() != 0) {
			for(int iterator = 0; iterator < detailsArray.size(); iterator++) {
				
				String id = detailsArray.get(iterator).employeeID;
				String name = detailsArray.get(iterator).employeeName;
				String email = detailsArray.get(iterator).employeeEmail;
				String dob = detailsArray.get(iterator).employeeDOB;
				String doj = detailsArray.get(iterator).employeeDOJ;
				double phoneNumber = (double)detailsArray.get(iterator).employeePhoneNumber;
				
				// Query for insertion operation
				String query = "insert into employeedetails(employeeID, employeeName, employeeEmail, "
						+ "employeeDOB, employeeDOJ, employeePhoneNumber) values(?, ?, ?, ?, ?, ?)";
				
				// Prepared statement for query execution
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, id);
				preparedStatement.setString(2, name);
				preparedStatement.setString(3, email);
				preparedStatement.setString(4, dob);
				preparedStatement.setString(5, doj);
				preparedStatement.setDouble(6, phoneNumber);
				
				// To check the insertion status
				int status = preparedStatement.executeUpdate();
		        if(status > 0) {
		           System.out.println("Record is inserted successfully !!!");
		        }
			}
		}		
	}

	public static void displayData(Connection connection) throws SQLException {
		statement =  (Statement) connection.createStatement();
		resultSet = (ResultSet) statement.executeQuery("Select * from employeedetails");
		
		// Result set not empty display table details	
		if(resultSet.next() == false) {
			System.out.println("Table is empty!\n");
		}
		else {
			do {
				String id = resultSet.getString("employeeID");
				String name = resultSet.getString("employeeName");
				String email = resultSet.getString("employeeEmail");
				String dob = resultSet.getString("employeeDOB");
				String doj = resultSet.getString("employeeDOJ");
				long phoneNumber = (long)resultSet.getDouble("employeePhoneNumber");
				
				System.out.println("\nEmployeeID: \t\t" + id + "\nEmployee Name: \t\t" + name + "\nEmployee Email: \t" + email +
						"\nEmployee DOB: \t\t" + dob + "\nEmployee DOJ: \t\t" + doj + "\nEmployee Phone number: \t" + phoneNumber + "\n");
			} while(resultSet.next());
		}
		
		connection.close();
	}

	public static boolean detailExist(ArrayList<ManageEmployee> detailsArray) throws SQLException {
		boolean flag = false;
		
		connection = establishConnection();
		statement =  (Statement) connection.createStatement();
		resultSet = (ResultSet) statement.executeQuery("Select * from employeedetails");		
		
		if(resultSet.next() == false) {
			System.out.println("Table is empty!\n");
			flag = false;
			
		} else {
			do {
				String id = resultSet.getString("employeeID");
				String email = resultSet.getString("employeeEmail");
				long phoneNumber = (long)resultSet.getDouble("employeePhoneNumber");
				
				for(int count = 0; count < detailsArray.size(); count++) {
					if(detailsArray.get(count).employeeID == id ||
						detailsArray.get(count).employeeEmail == email ||
						detailsArray.get(count).employeePhoneNumber == phoneNumber) {
						flag = true;
						
						detailsArray.remove(count);
					}
				}
				
			} while(resultSet.next());
		}
		
		return flag;
	}

	public static void deleteData() throws SQLException {
		
		int option = 0;
		System.out.println("\nSelect an option from below: ");
    	System.out.println("1. Delete all the employee data");
    	System.out.println("2. Delete specific employee's data");
    	System.out.println("3. Go to main options");
    	option = scanner.nextInt();
    	
    	scanner.nextLine();    	
    	
    	connection = establishConnection();
		statement =  (Statement) connection.createStatement();
		
    	switch(option) {
	    	case 1:  {
	    		statement.executeUpdate("delete from employeedetails");
	    		System.out.println("\nAll the records has been deleted succesfully.");
	    		displayData(connection);
	    		break;
	    	}
	    	case 2: {
	    		String id = null;
	    		System.out.println("\nEnter the ID of the employee whose data to be deleted: ");
	    		id = scanner.nextLine();
	    		
	    		String query = "delete from employeedetails where employeeID = '" + id + "'";
	    		System.out.println(query);
	    		statement.executeUpdate(query);
	    		System.out.println("\nThe record has been deleted succesfully.");
	    		displayData(connection);
	    		break;
	    	}
	    	case 3: {
	    		Employee employee = new Employee();
	            employee.viewOptions();
	    		break;
	    	}
	    	default: {
	    		System.out.println("Entered option is not valid! select an option from 1 to 5.\n");
	    		deleteData();
	    	}
    	}
    	
	}

	public static void updateData(Connection connection) throws SQLException {
		connection = establishConnection();
		statement =  (Statement) connection.createStatement();
		resultSet = (ResultSet) statement.executeQuery("Select * from employeedetails");		
		
		if(resultSet.next() == false) {
			System.out.println("\nError: Table is empty! Therefore cannot perform update operation\n");
			Employee employee = new Employee();
			employee.viewOptions();
		}
		else{
			
			System.out.println("\nEnter the ID of the employee whose data to be modified: ");
			
			// Employee ID
    		boolean idFlag = true;
    		String id = "";
    		
    		while(idFlag) {
    			 System.out.println("\nEnter ID of employee example(ace1234): "); 
    			 id = scanner.nextLine();
    			
    			if(Validate.isValidID(id)) {
    				idFlag = false;
    			}
    			else {
    				idFlag = true;
    			}
    		}
			
			do {
				String employeeID = resultSet.getString("employeeID");
				if(id.equals(employeeID)) {
					int menuOption = 0;
		        	System.out.println("\nSelect an option from below: ");
		        	System.out.println("1. Update employee name");
		        	System.out.println("2. Update employee email address");
		        	System.out.println("3. Update employee DOB");
		        	System.out.println("4. Update employee DOJ");
		        	System.out.println("5. Update employee phonenumber");
		        	System.out.println("6. Go to main options");
		        	
		        	menuOption = scanner.nextInt();
		        	scanner.nextLine();
		        	
		        	switch(menuOption) {
		        		case 1: {
		        			// Employee name		
		        			boolean nameFlag = true;
		        			String name = "";
		        			
		        			while(nameFlag) {
		        				System.out.println("\nEnter name of employee: ");
		        				name = scanner.nextLine();
		        				
		        				if(Validate.isValidName(name)) {
		        					nameFlag = false;
		        				}
		        				
		        				else {
		        					nameFlag = true;
		        				}
		        				
		        			}
		        			
		        			String query = "UPDATE employeedetails SET employeeName = ? WHERE employeeID = ?";
		        			preparedStatement = connection.prepareStatement(query);
		        			preparedStatement.setString(1, id);
		        			preparedStatement.setString(2, name);
		        			int status = preparedStatement.executeUpdate();
		        			
		        			if(status > 0){
		        				System.out.println("\nSuccessfully data updated.");
		        			}
		        			break;
		        		}
		        		case 2: {
		        			// Employee email
	        				boolean emailFlag = true;
	        				String email = "";
	        				while(emailFlag) {
	        					System.out.println("\nEnter email of employee: ");
	        					email = scanner.nextLine();
	        					
	        					if(Validate.isValidEmail(email)) {
	        						emailFlag = false;
	        					}
	        					
	        					else {
	        						emailFlag = true;
	        					}
	        				}
	        				
	        				String query = "UPDATE employeedetails SET employeeEmail = ? WHERE employeeID = ?";
		        			preparedStatement = connection.prepareStatement(query);
		        			preparedStatement.setString(1, id);
		        			preparedStatement.setString(2, email);
		        			int status = preparedStatement.executeUpdate();
		        			
		        			if(status > 0){
		        				System.out.println("\nSuccessfully data updated.");
		        			}
		        			
		        			break;
		        		}
		        		case 3: {
		        			// Date format
	        				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	        				format.setLenient(false);
	        				
	        				// Employee DOB
	        				boolean dobFlag = true;
	        				String dob = "";
	        				Date dobDate = null;
	        				
	        				while(dobFlag) {
	        					System.out.println("\nEnter DOB of employee in (dd/mm/yyyy) format: ");  
	        					dob = scanner.nextLine();
	        					
	        					dobDate = Validate.isValidDateOfBirth(dob);
	        					
	        					if(dobDate != null) {
	        						dobFlag = false;
	        					}
	        					
	        					else {
	        						dobFlag = true;
	        					}
	        				}
	        				
	        				String query = "UPDATE employeedetails SET employeeDOB = ? WHERE employeeID = ?";
		        			preparedStatement = connection.prepareStatement(query);
		        			preparedStatement.setString(1, id);
		        			preparedStatement.setString(2, format.format(dobDate));
		        			int status = preparedStatement.executeUpdate();
		        			
		        			if(status > 0){
		        				System.out.println("\nSuccessfully data updated.");
		        			}
		        			
		        			break;
		        		}
		        		case 4: {
		        			// Date format
	        				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	        				format.setLenient(false);
	        				
	        				// Employee DOJ
	        				boolean dojFlag = true;
	        				String doj = "";
	        				Date dojDate = null;
	        				Date dobDate = null;
	        				
	        				try {
	        					dobDate = format.parse(resultSet.getString("employeeDOB"));
	        				} catch (ParseException exception) {
	        					System.out.println("Error: "+ resultSet.getString("employeeDOJ") +" is in invalid date format. Please enter date in the format of dd/mm/yyyy.\n");
	        				} 
	        				
	        				while(dojFlag) {
	        					System.out.println("\nEnter DOJ of employee in the (dd/mm/yyyy) format: ");  
	        					doj = scanner.nextLine();
	        					
	        					dojDate = Validate.isValidDateOfJoining(doj, dobDate);
	        					
	        					if(dojDate != null) {
	        						dojFlag = false;
	        					}
	        					
	        					else {
	        						dojFlag = true;
	        					}
	        				}
	        				
	        				String query = "UPDATE employeedetails SET employeeDOJ = ? WHERE employeeID = ?";
		        			preparedStatement = connection.prepareStatement(query);
		        			preparedStatement.setString(1, id);
		        			preparedStatement.setString(2, format.format(dojDate));
		        			int status = preparedStatement.executeUpdate();
		        			
		        			if(status > 0){
		        				System.out.println("\nSuccessfully data updated.");
		        			}
		        			
		        			break;
		        		}
		        		case 5: {
		        			
		        			// Employee phone number
	        				boolean phoneFlag = true;
	        				long phone = 0;
	        				while(phoneFlag) {
	        					System.out.println("\nEnter phone number of employee: ");  
	        					phone = scanner.nextLong();
	        					
	        					// Changing the data from long to string
	        					String phoneNumber = Long.toString(phone);
	        					
	        					if(Validate.isValidPhoneNumber(phoneNumber)) {
	        						phoneFlag = false;
	        					}
	        					
	        					else {
	        						phoneFlag = true;
	        					}
	        				}
	        				
	        				String query = "UPDATE employeedetails SET employeePhoneNumber = ? WHERE employeeID = ?";
		        			preparedStatement = connection.prepareStatement(query);
		        			preparedStatement.setString(1, id);
		        			preparedStatement.setDouble(2, (double)phone);
		        			int status = preparedStatement.executeUpdate();
		        			
		        			if(status > 0){
		        				System.out.println("\nSuccessfully data updated.");
		        			}
		        			
		        			break;
		        		}
		        		case 6: {
		        			Employee employee = new Employee();
		    	            employee.viewOptions();
		        			break;
		        		}
		        		default: {
		        			System.out.println("Entered option is not valid! select an option from 1 to 5.\n");
		    	    		updateData(connection);
		        		}   
		        	}
		        	
		        	updateData(connection);
				}
				
				else {
					System.out.println("\nError: Employee ID doesn't exists!");
				}
				
			} while(resultSet.next());
		}
	}
}

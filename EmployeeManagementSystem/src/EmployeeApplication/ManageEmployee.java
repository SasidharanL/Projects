package EmployeeApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ManageEmployee {
	String employeeName;  
	String employeeID;  
	String employeeEmail;
	String employeeDOB;
	String employeeDOJ;
	long employeePhoneNumber; 	
	
	Scanner scan = new Scanner(System.in);
	
	void getValue(ArrayList<ManageEmployee> details) {  
		
		// Employee ID
		boolean idFlag = true;
		String id = "";
		
		while(idFlag) {
			 System.out.println("\nEnter ID of employee example(ace1234): "); 
			 id = scan.nextLine();
			
			if(Validate.isValidID(id)) {
				employeeID = id;
				idFlag = false;
			}
			else {
				idFlag = true;
			}
		}
		
		// Employee name		
		boolean nameFlag = true;
		String name = "";
		
		while(nameFlag) {
			System.out.println("\nEnter name of employee: ");
			name = scan.nextLine();
			
			if(Validate.isValidName(name)) {
				employeeName = name;
				nameFlag = false;
			}
			
			else {
				nameFlag = true;
			}
			
		}
		
		// Employee email
		boolean emailFlag = true;
		String email = "";
		while(emailFlag) {
			System.out.println("\nEnter email of employee: ");
			email = scan.nextLine();
			
			if(Validate.isValidEmail(email)) {
				employeeEmail = email;
				emailFlag = false;
			}
			
			else {
				emailFlag = true;
			}
		}
		
		
		
		// Date format
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.setLenient(false);
		
		// Employee DOB
		boolean dobFlag = true;
		String dob = "";
		Date dobDate = null;
		
		while(dobFlag) {
			System.out.println("\nEnter DOB of employee in (dd/mm/yyyy) format: ");  
			dob = scan.nextLine();
			
			dobDate = Validate.isValidDateOfBirth(dob);
			
			if(dobDate != null) {
				employeeDOB = format.format(dobDate);
				dobFlag = false;
			}
			
			else {
				dobFlag = true;
			}
		}
		
		// Employee DOJ
		boolean dojFlag = true;
		String doj = "";
		Date dojDate = null;
		
		while(dojFlag) {
			System.out.println("\nEnter DOJ of employee in the (dd/mm/yyyy) format: ");  
			doj = scan.nextLine();
			
			dojDate = Validate.isValidDateOfJoining(doj, dobDate);
			
			if(dojDate != null) {
				employeeDOJ = format.format(dojDate);
				dojFlag = false;
			}
			
			else {
				dojFlag = true;
			}
		}
		 
		
		// Employee phone number
		boolean phoneFlag = true;
		long phone = 0;
		while(phoneFlag) {
			System.out.println("\nEnter phone number of employee: ");  
			phone = scan.nextLong();
			
			// Changing the data from long to string
			String phoneNumber = Long.toString(phone);
			
			if(Validate.isValidPhoneNumber(phoneNumber)) {
				employeePhoneNumber = phone;
				phoneFlag = false;
			}
			
			else {
				phoneFlag = true;
			}
		}
		
		System.out.println("\nRecord successfully created for the employee " + employeeID + "\n");
    }  
}

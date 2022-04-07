/*
 Title - Employee Management System
 Author - Sasidharan
 Created on - 20/10/2021
 Updated on - 30/03/2022
 Reviewed by - Akshaya, Jaya on 31/03/2022
 */

package EmployeeApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class EmployeeDetails {
	String employeeName;
	String employeeID;
	String employeeEmail;
	String employeeDOB;
	String employeeDOJ;
	long employeePhoneNumber;

	Scanner scanner = new Scanner(System.in);

	void getValue(ArrayList<EmployeeDetails> details) {

		// Employee ID
		boolean idFlag = true;
		String id = "";

		while (idFlag) {
			System.out.println("\nEnter ID of employee example(ace1234): ");
			id = scanner.nextLine();

			if (Validation.isValidID(id)) {
				employeeID = id;
				idFlag = false;
				
				 if(Validation.checkIDExists(id)) {
					 System.out.println("Error: Entered employee ID already exists!\n"); 
					 idFlag = true;
				 }
				 
				 if(MySqlConnection.checkIDExcistsInDataBase(id)) {
					 System.out.println("Error: Entered employee ID already exists!!\n"); 
					 idFlag = true;
				 }
				 
			} else {
				idFlag = true;
			}
		}

		// Employee name
		boolean nameFlag = true;
		String name = "";

		while (nameFlag) {
			System.out.println("\nEnter name of employee: ");
			name = scanner.nextLine();

			if (Validation.isValidName(name)) {
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
		while (emailFlag) {
			System.out.println("\nEnter email of employee: ");
			email = scanner.nextLine();

			if (Validation.isValidEmail(email)) {
				employeeEmail = email;
				emailFlag = false;
				
				if(Validation.checkEmailExists(email)) {
					 System.out.println("Error: Entered employee email already exists!\n"); 
					 emailFlag = true;
				 }
				 
				 if(MySqlConnection.checkEmailExcistsInDataBase(email)) {
					 System.out.println("Error: Entered employee email already exists!!\n"); 
					 emailFlag = true;
				 }
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

		while (dobFlag) {
			System.out.println("\nEnter DOB of employee in (dd/mm/yyyy) format: ");
			dob = scanner.nextLine();

			dobDate = Validation.isValidDateOfBirth(dob);

			if (dobDate != null) {
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

		while (dojFlag) {
			System.out.println("\nEnter DOJ of employee in the (dd/mm/yyyy) format: ");
			doj = scanner.nextLine();

			dojDate = Validation.isValidDateOfJoining(doj, dobDate);

			if (dojDate != null) {
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
		while (phoneFlag) {
			System.out.println("\nEnter phone number of employee: ");
			phone = scanner.nextLong();

			// Changing the data from long to string
			String phoneNumber = Long.toString(phone);

			if (Validation.isValidPhoneNumber(phoneNumber)) {
				employeePhoneNumber = phone;
				phoneFlag = false;
				
				if(Validation.checkPhoneExists(phone)) {
					 System.out.println("Error: Entered employee email already exists!\n"); 
					 phoneFlag = true;
				 }
				 
				 if(MySqlConnection.checkPhoneExcistsInDataBase(phone)) {
					 System.out.println("Error: Entered employee email already exists!!\n"); 
					 phoneFlag = true;
				 }
			}

			else {
				phoneFlag = true;
			}
		}

		System.out.println("\nRecord successfully created for the employee " + employeeID + "\n");
	}
}

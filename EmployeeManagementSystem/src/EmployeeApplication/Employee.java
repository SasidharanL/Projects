/*
 Title - Employee Management System
 Author - Sasidharan
 Created on - 20/10/2021
 Updated on - 30/03/2022
 Reviewed by - Akshaya, Jaya on 31/03/2022
 */

package EmployeeApplication;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Employee {

	static Scanner scanner = new Scanner(System.in);
	public static ArrayList<EmployeeDetails> detailsArray = new ArrayList<EmployeeDetails>();

	public Employee() {
		System.out.println("Welcome to Employee Management System!\n");
		System.out.println("Data available in the database:");
		MySqlConnection.displayData();
	}


	public void viewOptions() {
		int menuOption = 0;

		System.out.println("\nSelect an option from below: ");
		System.out.println("1. Create a new employee");
		System.out.println("2. Update the details of an employee");
		System.out.println("3. Display the employee details");
		System.out.println("4. Delete a employee's data");
		System.out.println("5. Exit the program");
		System.out.println("6. Manipulate details in database");

		menuOption = scanner.nextInt();

		switch (menuOption) {
		case 1: {
			createEmployee();
			break;
		}
		case 2: {
			updateEmployee();
			break;
		}
		case 3: {
			displayDetails();
			break;
		}
		case 4: {
			deleteEmployee();
			break;
		}

		case 5: {
			System.out.println("\nApplication terminated!\nHope to see you again :)");
			System.exit(0);
			break;
		}

		case 6: {
			dataBaseManipulate();
			break;
		}

		default: {
			System.out.println("Enter a valid option!!\nEnter numbers between 1 and 6 only.\n");
			viewOptions();
		}
		}

	}

	public void dataBaseManipulate() {

		try {
			if (MySqlConnection.getData().next() == false) {
				System.out.println(
						"There is no data to be found for this operation.\nKindly create a employee detail to continue.\n");
				viewOptions();
			} else {

				int menuOption = 0;
				System.out.println("\nSelect an option from below: ");
				System.out.println("1. Display all the employee data from database");
				System.out.println("2. Delete employee's data from the database");
				System.out.println("3. Update employee's data from the database");
				System.out.println("4. Go to main options");

				menuOption = scanner.nextInt();

				switch (menuOption) {
				case 1: {
					MySqlConnection.displayData();
					dataBaseManipulate();
					break;
				}

				case 2: {
					MySqlConnection.deleteData();
					dataBaseManipulate();
					break;
				}

				case 3: {
					MySqlConnection.updateData();
					dataBaseManipulate();
					break;
				}

				case 4: {
					viewOptions();
					break;
				}

				default: {
					System.out.println("Entered option is not valid! select an option from 1 to 4.\n");
					viewOptions();
				}
				}

			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

	private void updateEmployee() {
		scanner.nextLine();

		if (detailsArray.isEmpty() == true) {
			System.out.println("Error: No records available. Please create an employee first.");
			viewOptions();
		}

		else {
			System.out.println("Enter the ID of the employee whose data to be modified: ");

			// Employee ID
			boolean idFlag = true;
			String tempID = "";

			while (idFlag) {
				System.out.println("\nEnter ID of employee example(ace1234): ");
				tempID = scanner.nextLine();

				if (Validation.isValidID(tempID)) {
					idFlag = false;
				} else {
					idFlag = true;
				}
			}

			int flag = 0;
			for (int count = 0; count < detailsArray.size(); count++) {
				if (tempID.equals(detailsArray.get(count).employeeID)) {
					flag++;
				}
			}

			if (!(flag >= 1)) {
				System.out.println("The employee id: " + tempID + " is not available. Please provide a valid id");
				updateEmployee();
			}

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

			if (menuOption == 1) {
				for (int count = 0; count < detailsArray.size(); count++) {
					if (tempID.equals(detailsArray.get(count).employeeID)) {

						// Employee name
						boolean nameFlag = true;
						String name = "";

						while (nameFlag) {
							System.out.println("\nEnter name of employee: ");
							name = scanner.nextLine();

							if (Validation.isValidName(name)) {
								nameFlag = false;
							}

							else {
								nameFlag = true;
							}

						}

						detailsArray.get(count).employeeName = name;

						System.out.println("Name has been changed for " + detailsArray.get(count).employeeID
								+ " the new name is: " + detailsArray.get(count).employeeName);
					}
				}

				viewOptions();
			}

			else if (menuOption == 2) {
				for (int count = 0; count < detailsArray.size(); count++) {
					if (tempID.equals(detailsArray.get(count).employeeID)) {

						// Employee email
						boolean emailFlag = true;
						String email = "";
						while (emailFlag) {
							System.out.println("\nEnter email of employee: ");
							email = scanner.nextLine();

							if (Validation.isValidEmail(email)) {
								emailFlag = false;
							}

							else {
								emailFlag = true;
							}
						}

						detailsArray.get(count).employeeEmail = email;

						System.out.println("Name has been changed for " + detailsArray.get(count).employeeID
								+ " the new name is: " + detailsArray.get(count).employeeEmail);
					}
				}
				viewOptions();
			}

			else if (menuOption == 3) {
				for (int count = 0; count < detailsArray.size(); count++) {
					if (tempID.equals(detailsArray.get(count).employeeID)) {

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
								dobFlag = false;
							}

							else {
								dobFlag = true;
							}
						}

						detailsArray.get(count).employeeDOB = format.format(dobDate);

						System.out.println("Name has been changed for " + detailsArray.get(count).employeeID
								+ " the new name is: " + detailsArray.get(count).employeeDOB);
					}
				}

				viewOptions();
			}

			else if (menuOption == 4) {
				for (int count = 0; count < detailsArray.size(); count++) {
					if (tempID.equals(detailsArray.get(count).employeeID)) {

						// Date format
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
						format.setLenient(false);

						// Employee DOJ
						boolean dojFlag = true;
						String doj = "";
						Date dojDate = null;
						Date dobDate = null;

						try {
							dobDate = format.parse(detailsArray.get(count).employeeDOJ);
						} catch (ParseException exception) {
							System.out.println("Error: " + detailsArray.get(count).employeeDOJ
									+ " is in invalid date format. Please enter date in the format of dd/mm/yyyy.\n");
						}

						while (dojFlag) {
							System.out.println("\nEnter DOJ of employee in the (dd/mm/yyyy) format: ");
							doj = scanner.nextLine();

							dojDate = Validation.isValidDateOfJoining(doj, dobDate);

							if (dojDate != null) {
								dojFlag = false;
							}

							else {
								dojFlag = true;
							}
						}

						detailsArray.get(count).employeeDOJ = format.format(dojDate);

						System.out.println("Name has been changed for " + detailsArray.get(count).employeeID
								+ " the new name is: " + detailsArray.get(count).employeeDOJ);
					}
				}

				viewOptions();
			}

			else if (menuOption == 5) {
				for (int count = 0; count < detailsArray.size(); count++) {
					if (tempID.equals(detailsArray.get(count).employeeID)) {

						// Employee phone number
						boolean phoneFlag = true;
						long phone = 0;
						while (phoneFlag) {
							System.out.println("\nEnter phone number of employee: ");
							phone = scanner.nextLong();

							// Changing the data from long to string
							String phoneNumber = Long.toString(phone);

							if (Validation.isValidPhoneNumber(phoneNumber)) {
								phoneFlag = false;
							}

							else {
								phoneFlag = true;
							}
						}

						detailsArray.get(count).employeePhoneNumber = phone;

						System.out.println("Name has been changed for " + detailsArray.get(count).employeeID
								+ " the new name is: " + detailsArray.get(count).employeePhoneNumber);
					}
				}

				viewOptions();
			}

			else if (menuOption == 6) {
				viewOptions();
			}

			else {
				System.out.println("Enter a valid option!!");
				updateEmployee();
			}
		}
	}

	private void deleteEmployee() {

		if (detailsArray.isEmpty() == true) {
			System.out.println("Error: No records available. Please create an employee first.");
			viewOptions();
		}

		else {
			int menuOption = 0;
			System.out.println("\nSelect an option from below: ");
			System.out.println("1. Delete all the employee data");
			System.out.println("2. Delete specific employee's data");
			System.out.println("3. Go to main options");

			menuOption = scanner.nextInt();
			scanner.nextLine();

			if (menuOption == 1) {
				detailsArray.removeAll(detailsArray);
				System.out.println("All the employee data has been deleted succesfully.");
				viewOptions();
			}

			else if (menuOption == 2) {

				System.out.println("Enter the ID of the employee whose data to be modified: ");
				String id = scanner.nextLine();

				for (int count = 0; count < detailsArray.size(); count++) {
					if (id.equals(detailsArray.get(count).employeeID)) {
						detailsArray.remove(count);
						System.out.println("The selected employee data has been deleted succesfully.");
					}
				}
				viewOptions();
			}

			else if (menuOption == 3) {
				viewOptions();
			}

			else {
				System.out.println("Enter a valid option!!");
				deleteEmployee();
			}
		}
	}

	private void displayDetails() {

		if (detailsArray.isEmpty() == true) {
			System.out.println("Error: No records available. Please create an employee first.");
			viewOptions();
		}

		else {
			int menuOption = 0;
			System.out.println("Select a display option from below: ");
			System.out.println("1. Display all the employee details");
			System.out.println("2. Display specific employee data");
			System.out.println("3. Go to main options");

			menuOption = scanner.nextInt();
			scanner.nextLine();

			if (menuOption == 1) {
				System.out.println(detailsArray.size());
				for (int count = 0; count < detailsArray.size(); count++) {

					System.out.println("\nEmployee ID: " + detailsArray.get(count).employeeID);
					System.out.println("Employee Name: " + detailsArray.get(count).employeeName);
					System.out.println("Employee email: " + detailsArray.get(count).employeeEmail);
					System.out.println("Employee DOB: " + detailsArray.get(count).employeeDOB);
					System.out.println("Employee DOJ: " + detailsArray.get(count).employeeDOJ);
					System.out.println("Employee phone number: " + detailsArray.get(count).employeePhoneNumber + "\n");
				}

				viewOptions();
			} else if (menuOption == 2) {
				String employeeKey;
				System.out.println("Enter the ID of the employee whose data to be retrieved: ");
				employeeKey = scanner.nextLine();

				for (int count = 0; count < detailsArray.size(); count++) {
					String option = detailsArray.get(count).employeeID;

					if (employeeKey.equals(option)) {
						System.out.println("\nEmployee ID: " + detailsArray.get(count).employeeID);
						System.out.println("Employee Name: " + detailsArray.get(count).employeeName);
						System.out.println("Employee email: " + detailsArray.get(count).employeeEmail);
						System.out.println("Employee DOB: " + detailsArray.get(count).employeeDOB);
						System.out.println("Employee DOJ: " + detailsArray.get(count).employeeDOJ);
						System.out.println(
								"Employee phone number: " + detailsArray.get(count).employeePhoneNumber + "\n");
					}
				}

				viewOptions();
			} else if (menuOption == 3) {
				viewOptions();
			}

			else {
				System.out.println("Enter a valid option!!");
				displayDetails();
			}
		}
	}

	private void createEmployee() {
		int numberOfEmployees = 0;

		System.out.println("Number of employees to be added: ");
		numberOfEmployees = scanner.nextInt();

		for (int iterator = 0; iterator < numberOfEmployees; iterator++) {

			EmployeeDetails details = new EmployeeDetails();
			details.getValue(detailsArray);

			detailsArray.add(details);
			
			MySqlConnection.saveDate(details);

			// System.out.println(detailsArray.size());
		}

		viewOptions();
	}

	public static void main(String[] args) {
		Employee employee = new Employee();
		employee.viewOptions();
	}

}
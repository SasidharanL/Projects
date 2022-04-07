/*
 Title - Employee Management System
 Author - Sasidharan
 Created on - 20/10/2021
 Updated on - 30/03/2022
 Reviewed by - Akshaya, Jaya on 31/03/2022
 */

package EmployeeApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MySqlConnection {

	static Connection connection = null;
	static Statement statement = null;
	static ResultSet resultSet = null;
	static PreparedStatement preparedStatement = null;
	static Scanner scanner = new Scanner(System.in);

	public static ResultSet getData() {

		connection = establishConnection();

		try {

			statement = (Statement) connection.createStatement();
			resultSet = (ResultSet) statement.executeQuery("Select * from employeedetails");
		} catch (SQLException exception) {
			resultSet = null;
			System.out.println(exception.getMessage());

		}

		return resultSet;
	}

	public static Connection establishConnection() {

		try {
			// Connection to database
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root",
					"Aspire@123");

		} catch (Exception exception) {
			System.out.println(exception);
		}

		return connection;
	}

	public static boolean checkIDExcistsInDataBase(String id) {
		boolean flag = false;

		try {
			connection = establishConnection();
			String query = "Select employeeID from employeedetails where employeeID = ?";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				flag = true;
			}

		} catch (SQLException exception) {
			resultSet = null;
			System.out.println(exception.getMessage());

		}

		return flag;
	}

	public static boolean checkEmailExcistsInDataBase(String email) {
		boolean flag = false;

		try {
			connection = establishConnection();
			String query = "Select employeeEmail from employeedetails where employeeEmail = ?";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				flag = true;
			}

		} catch (SQLException exception) {
			resultSet = null;
			System.out.println(exception.getMessage());

		}

		return flag;
	}

	public static boolean checkPhoneExcistsInDataBase(long phone) {
		boolean flag = false;

		try {
			connection = establishConnection();
			String query = "Select employeeEmail from employeedetails where employeePhoneNumber = ?";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setDouble(1, (double)phone);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				flag = true;
			}

		} catch (SQLException exception) {
			resultSet = null;
			System.out.println(exception.getMessage());

		}

		return flag;
	}

	public static void saveDate(EmployeeDetails details) {

		String id = details.employeeID;
		String name = details.employeeName;
		String email = details.employeeEmail;
		String dob = details.employeeDOB;
		String doj = details.employeeDOJ;
		double phoneNumber = (double) details.employeePhoneNumber;

		// Query for insertion operation
		String query = "insert into employeedetails(employeeID, employeeName, employeeEmail, "
				+ "employeeDOB, employeeDOJ, employeePhoneNumber) values(?, ?, ?, ?, ?, ?)";

		try {

			connection = establishConnection();

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
			if (status > 0) {
				System.out.println("Record is inserted successfully !!!");
			} else {
				System.out.println("Record is not inserted into the table !!!");
			}
		} catch (SQLException exception) {

			System.out.println(exception.getMessage());

		} finally {

			try {
				preparedStatement.clearBatch();
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
	}

	public static void displayData() {

		connection = establishConnection();

		try {

			statement = (Statement) connection.createStatement();
			resultSet = (ResultSet) statement.executeQuery("Select * from employeedetails");

			// Result set not empty display table details
			if (resultSet.next() == false) {
				System.out.println("Table is empty!\n");
			} else {

				do {
					String id = resultSet.getString("employeeID");
					String name = resultSet.getString("employeeName");
					String email = resultSet.getString("employeeEmail");
					String dob = resultSet.getString("employeeDOB");
					String doj = resultSet.getString("employeeDOJ");
					long phoneNumber = (long) resultSet.getDouble("employeePhoneNumber");

					System.out.println("\nEmployeeID: \t\t" + id + "\nEmployee Name: \t\t" + name
							+ "\nEmployee Email: \t" + email + "\nEmployee DOB: \t\t" + dob + "\nEmployee DOJ: \t\t"
							+ doj + "\nEmployee Phone number: \t" + phoneNumber + "\n");
				} while (resultSet.next());
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

	public static void deleteData() {

		int option = 0;
		System.out.println("\nSelect an option from below: ");
		System.out.println("1. Delete all the employee data");
		System.out.println("2. Delete specific employee's data");
		System.out.println("3. Go to main options");

		option = scanner.nextInt();
		scanner.nextLine();

		try {
			connection = establishConnection();
			statement = (Statement) connection.createStatement();

			switch (option) {
			case 1: {
				statement.executeUpdate("delete from employeedetails");
				System.out.println("\nAll the records has been deleted succesfully.");
				displayData();
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
				displayData();
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
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {

			try {

				statement.clearBatch();
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}

	}

	public static void updateData() {
		preparedStatement = null;
		resultSet = null;
		Employee employee = new Employee();
		try {
			connection = establishConnection();
			statement = (Statement) connection.createStatement();
			resultSet = (ResultSet) statement.executeQuery("Select * from employeedetails");

			if (resultSet.next() == false) {

				System.out.println("\nError: Table is empty! Therefore cannot perform update operation\n");
				employee.viewOptions();

			} else {

				System.out.println("\nEnter the ID of the employee whose data to be modified: ");

				// Employee ID
				boolean idFlag = true;
				String id = "";

				while (idFlag) {
					id = scanner.nextLine();

					if (Validation.isValidID(id)) {
						idFlag = false;
					} else {
						idFlag = true;
					}
				}

				do {

					String employeeID = resultSet.getString("employeeID");

					if (employeeID.equals(id)) {
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

						switch (menuOption) {
						case 1: {
							// Employee name
							boolean nameFlag = true;
							String name = "";

							while (nameFlag) {
								System.out.println("\nEnter name of the employee: ");
								name = scanner.nextLine();

								if (Validation.isValidName(name)) {
									nameFlag = false;
								}

								else {
									nameFlag = true;
								}

							}

							String query = "update employeedetails SET employeeName = ? WHERE employeeID = ?";
							preparedStatement = connection.prepareStatement(query);
							preparedStatement.setString(1, name);
							preparedStatement.setString(2, id);
							int status = preparedStatement.executeUpdate();

							if (status > 0) {
								System.out.println("\nSuccessfully data updated.");
							} else {
								System.out.println("\nUpdation of employee name is unsuccessful!");
							}

							employee.dataBaseManipulate();
							displayData();
							break;
						}
						case 2: {
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

							String query = "UPDATE employeedetails SET employeeEmail = ? WHERE employeeID = ?";
							preparedStatement = connection.prepareStatement(query);
							preparedStatement.setString(1, email);
							preparedStatement.setString(2, id);
							int status = preparedStatement.executeUpdate();

							if (status > 0) {
								System.out.println("\nSuccessfully data updated.");
							} else {
								System.out.println("\nUpdation of employee email is unsuccessful!");
							}

							employee.dataBaseManipulate();
							displayData();
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

							String query = "UPDATE employeedetails SET employeeDOB = ? WHERE employeeID = ?";
							preparedStatement = connection.prepareStatement(query);
							preparedStatement.setString(1, format.format(dobDate));
							preparedStatement.setString(2, id);
							int status = preparedStatement.executeUpdate();

							if (status > 0) {
								System.out.println("\nSuccessfully data updated.");
							} else {
								System.out.println("\nUpdation of employee DOB is unsuccessful!");
							}

							employee.dataBaseManipulate();
							displayData();
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
								System.out.println("Error: " + resultSet.getString("employeeDOJ")
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

							String query = "UPDATE employeedetails SET employeeDOJ = ? WHERE employeeID = ?";
							preparedStatement = connection.prepareStatement(query);
							preparedStatement.setString(1, format.format(dojDate));
							preparedStatement.setString(2, id);
							int status = preparedStatement.executeUpdate();

							if (status > 0) {
								System.out.println("\nSuccessfully data updated.");
							} else {
								System.out.println("\nUpdation of employee DOJ is unsuccessful!");
							}

							employee.dataBaseManipulate();
							displayData();
							break;
						}
						case 5: {

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

							String query = "UPDATE employeedetails SET employeePhoneNumber = ? WHERE employeeID = ?";
							preparedStatement = connection.prepareStatement(query);
							preparedStatement.setDouble(1, (double) phone);
							preparedStatement.setString(2, id);
							int status = preparedStatement.executeUpdate();

							if (status > 0) {
								System.out.println("\nSuccessfully data updated.");
							}

							employee.dataBaseManipulate();
							displayData();
							break;
						}
						case 6: {
							employee.viewOptions();
							break;
						}
						default: {
							System.out.println("Entered option is not valid! select an option from 1 to 5.\n");
							updateData();
							break;
						}
						}
					}

					else {
						System.out.println("\nError: Employee ID doesn't exists!");
					}

				} while (resultSet.next());
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {

			try {
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}

	}
}

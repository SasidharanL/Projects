/*
 Title - Employee Management System
 Author - Sasidharan
 Created on - 20/10/2021
 Updated on - 30/03/2022
 Reviewed by - Akshaya, Jaya on 31/03/2022
 */

package EmployeeApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

	public static long getYearInDifference(Date dateOne, Date dateTwo) {

		long yearInDifference = 0;
		long differenceInTime = dateOne.getTime() - dateTwo.getTime();

		yearInDifference = TimeUnit.MILLISECONDS.toDays(differenceInTime) / 365l;

		return yearInDifference;
	}

	public static SimpleDateFormat getDateFormat() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);

		return dateFormat;
	}

	public static Date getTodaysDate(SimpleDateFormat format) {

		Date dateObject = new Date();
		Date todayDate = null;
		String tempDate = format.format(dateObject);

		try {
			todayDate = format.parse(tempDate);
		} catch (ParseException execption) {
			execption.printStackTrace();
		}

		return todayDate;
	}

	public static boolean checkIDExists(String id) {
		boolean flag = false;

		if (Employee.detailsArray.isEmpty() != true) {
			for (int count = 0; count < Employee.detailsArray.size(); count++) {
				String option = Employee.detailsArray.get(count).employeeID;

				if (id.equals(option)) {
					flag = true;
				}
			}
		}

		else {
			flag = false;
		}

		// Return true if id exists
		return flag;
	}

	public static boolean checkEmailExists(String email) {
		boolean flag = false;

		if (Employee.detailsArray.isEmpty() != true) {
			for (int count = 0; count < Employee.detailsArray.size(); count++) {
				String option = Employee.detailsArray.get(count).employeeEmail;

				if (email.equals(option)) {
					flag = true;
				}
			}
		}

		else {
			flag = false;
		}

		// Return true if id exists
		return flag;
	}

	public static boolean checkPhoneExists(long phone) {
		boolean flag = false;

		if (Employee.detailsArray.isEmpty() != true) {
			for (int count = 0; count < Employee.detailsArray.size(); count++) {
				long option = Employee.detailsArray.get(count).employeePhoneNumber;

				if (option == phone) {
					flag = true;
				}
			}
		}

		else {
			flag = false;
		}

		// Return true if id exists
		return flag;
	}

	public static boolean isSequence(String string) {

		boolean flag = true;

		int count = 0;
		int maxCount = 0;

		for (int iterator = 0; iterator < string.length() - 1; iterator++) {
			if ((string.charAt(iterator) + 1) == string.charAt(iterator + 1)) {
				count++;
			} else {
				count = 1;
			}

			maxCount = Math.max(maxCount, count);
		}

		if (maxCount > 1) {
			flag = true;
		} else {
			flag = false;
		}

		// Return true when there is a sequence
		return flag;
	}

	public static boolean isSameSequence(String string) {

		boolean flag = true;

		int count = 0;
		int maxCount = 0;

		for (int iterator = 0; iterator < string.length() - 1; iterator++) {
			if ((string.charAt(iterator)) == string.charAt(iterator + 1)) {
				count++;
			} else {
				count = 1;
			}

			maxCount = Math.max(maxCount, count);
		}

		if (maxCount > 1) {
			flag = true;
		} else {
			flag = false;
		}

		// Return true when there is a same sequence
		return flag;
	}

	// ID validation
	public static boolean isValidID(String id) {

		boolean idFlag = true;

		Pattern pattern = Pattern.compile("[a]{1}[c]{1}[e]{1}[0-9]{4}");
		Matcher matcher = pattern.matcher(id);

		if (id.length() != 7) {
			System.out
					.println("Error: Employee ID should have 7 character starting with ace followed by four digit.\n");
			idFlag = false;
		}

		else if (id.substring(3).equals("0000")) {
			System.out.println("Error: Employee ID shouldn't be ace0000!\n");
			idFlag = false;
		}

		else if (isSequence(id.substring(0, 3))) {
			System.out.println(
					"Error: Employee id shouldn't have three or more consecutive character.\nThe prefix should be ace\n");
			idFlag = false;
		}

		else if (isSameSequence(id.substring(0, 3))) {
			System.out.println(
					"Error: Employee id shouldn't have three or more same consecutive character.\nThe prefix should be ace\n");
			idFlag = false;
		}

		if (matcher.matches() == true) {
			idFlag = true;
		}

		else {
			System.out.println(
					"Error: Employee ID must start with three alphabets which is (ace) followed by four numeric example ace1234.\nAnd no same consecutive are allowed for alphabets.\nNo special characters are allowed.\n");
			idFlag = false;
		}

		// Return false when there is an error and returns true when no error is found.
		return idFlag;
	}

	// Name validation
	public static boolean isValidName(String name) {

		boolean nameFlag = true;

		Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z ]*");
		Matcher match = pattern.matcher(name);

		if (match.matches() == true) {
			nameFlag = true;

			if (name.length() < 2) {
				System.out.println("Error: Employee name should have minimum of two characters.\n");
				nameFlag = false;
			}

			else if (isSequence(name)) {
				System.out.println("Error: Employee name shouldn't have three or more consecutive character!\n");
				nameFlag = false;
			}

			else if (isSameSequence(name)) {
				System.out.println("Error: Employee name shouldn't have three or more same consecutive character!\n");
				nameFlag = false;
			}
		}

		else {
			System.out.println("Error: Employee name musn't contain any special character or numeric.\n");
			nameFlag = false;
		}

		// Return false when there is an error and returns true when no error is found.
		return nameFlag;
	}

	// Email validation
	public static boolean isValidEmail(String email) {

		boolean emailFlag = true;

		// Compiling and matching the input with regular expression
		// ^[a-zA-Z0-9]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z-]+\\.)+[a-z" +
		// "A-Z]{2,7}$

		String expression = "^([a-zA-Z][a-zA-Z0-9]*)+(?:\\." + "[a-zA-Z0-9]+)*@" + "(?:[a-zA-Z-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pattern = Pattern.compile(expression);
		Matcher match = pattern.matcher(email);

		if (match.matches() == true) {
			emailFlag = true;
		}

		// Need to find any sequence in the email

		else {
			System.out.println("Error: Email address must start with an alphabet and can be follow by numeric"
					+ "\nEmail address mustn't have any special special characters other than @ and dot(.)"
					+ "\nDomain name of the email must not contain numeric or special characters.\n");
			emailFlag = false;
		}

		// Return false when there is an error and returns true when no error is found.
		return emailFlag;
	}

	// DOB validation
	public static Date isValidDateOfBirth(String dob) {

		boolean dobFlag = true;
		Date dobDate = null;
		SimpleDateFormat format = getDateFormat();
		Date todaysDate = getTodaysDate(format);
		String pattern = "(0?[1-9]|[12][0-9]|3[01])\\/(0?[1-9]|1[0-2])\\/([0-9]{4})";

		if (dob.matches(pattern)) {
			try {
				dobDate = format.parse(dob);
				dobFlag = true;
			} catch (ParseException exception) {
				System.out.println("Error: " + dob
						+ " is in invalid date format. Please enter date in the format of dd/mm/yyyy.\n");
				dobFlag = false;
			}

			if (dobDate.after(todaysDate)) {
				System.out
						.println("Error: " + dob + " is a future date and date of birth shouldn't be a future date.\n");
				dobFlag = false;
			}

			else if (dobDate.equals(todaysDate)) {
				System.out.println(
						"Error: " + dob + " is the current date and date of birth shouldn't be a todays date.\n");
				dobFlag = false;
			}

			long yearInDifference = getYearInDifference(todaysDate, dobDate);

			if (yearInDifference < 18L) {
				dobFlag = false;
				System.out.println(
						"Error: Age of the employee shouldn't be less than 18! Enter a valid date of birth.\n");
			}

			else if (yearInDifference > 60L) {
				dobFlag = false;
				System.out.println(
						"Error: Age of the employee shouldn't be more than 60! Enter a valid date of birth.\n");
			}
		}

		else {
			System.out.println("Error: In date day, month, year should be seperated by /."
					+ "\nDate must have day(1-31) and month(1-12).\nAnd year should be from (0001-9999)");
			dobFlag = false;
		}

		if (dobFlag) {
			try {
				dobDate = format.parse(dob);
			} catch (ParseException exception) {
				exception.printStackTrace();
			}
		}

		else {
			dobDate = null;
		}

		return dobDate;
	}

	// DOJ validation
	public static Date isValidDateOfJoining(String doj, Date dobDate) {

		boolean dojFlag = true;
		Date dojDate = null;
		SimpleDateFormat format = getDateFormat();
		Date todaysDate = getTodaysDate(format);
		String pattern = "(0?[1-9]|[12][0-9]|3[01])\\/(0?[1-9]|1[0-2])\\/([0-9]{4})";

		if (doj.matches(pattern)) {
			try {
				dojDate = format.parse(doj);
				dojFlag = true;
			} catch (ParseException exception) {
				System.out.println("Error: " + doj
						+ " is in invalid date format. Please enter date in the format of dd/mm/yyyy.\n");
				dojFlag = false;
			}

			if (dojDate.after(todaysDate)) {
				System.out
						.println("Error: " + doj + " is a future date and date of birth shouldn't be a future date.\n");
				dojFlag = false;
			}

			else if (dojDate.equals(todaysDate)) {
				System.out.println(
						"Error: " + doj + " is the current date and date of birth shouldn't be a todays date.\n");
				dojFlag = false;
			}

			else if (dojDate.equals(dobDate)) {
				System.out.println("Error: Date of joining: " + format.format(dobDate) + " and Date of birth: " + doj
						+ " shouldn't be same\n");
				dojFlag = false;
			}

			long yearInDifference = getYearInDifference(dojDate, dobDate);

			if (yearInDifference < 18L) {
				dojFlag = false;
				System.out.println("Error: Employee's age shouldn't be less than 18!\n");
			}

			else if (yearInDifference > 40L) {
				dojFlag = false;
				System.out.println(
						"Error: Age of the employee shouldn't be more than 60! Enter a valid date of birth.\n");
			}
		}

		else {
			System.out.println("Error: In date day, month, year should be seperated by /."
					+ "\nDate must have day(1-31) and month(1-12).\nAnd year should be from (0001-9999)");
			dojFlag = false;
		}

		if (dojFlag) {
			try {
				dojDate = format.parse(doj);
			} catch (ParseException exception) {
				exception.printStackTrace();
			}
		}

		else {
			dojDate = null;
		}

		return dojDate;
	}

	// Phone number validation
	public static boolean isValidPhoneNumber(String phoneNumber) {

		boolean phoneFlag = true;

		// Compiling and matching the input with regular expression
		Pattern pattern = Pattern.compile("(0/91)?[6-9][0-9]{9}");
		Matcher match = pattern.matcher(phoneNumber);

		if (match.matches() == true) {
			phoneFlag = true;
		}

		else {
			System.out.println("Error: Entered phone number is invalid"
					+ "\nPhone number should start with 6, 7, 8 or 9 and must have 10 digits\n");
			phoneFlag = false;
		}

		// Return false when there is an error and returns true when no error is found.
		return phoneFlag;
	}
}

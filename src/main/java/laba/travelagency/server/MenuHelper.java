package laba.travelagency.server;

import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import laba.travelagency.exceptions.InputDoesNotMatchException;
import laba.travelagency.exceptions.InvalidInputException;

public class MenuHelper {
	
	private static Scanner scanner = new Scanner(System.in);
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
	private static final Logger logger = LogManager.getLogger(MenuHelper.class);
	
	public static String readEmailInput(String prompt) {
		
		logger.info(prompt);
		String email = scanner.nextLine();
		
		try {
			if(!(pattern.matcher(email).matches()))
				throw new InvalidInputException("Invalid Email. Please provide correct email !!");
		}
		catch(InvalidInputException e) 
		{
			logger.info("\nInvalidInputException : " + e.getMessage());
			logger.info("Enter Input again...");
			email = readEmailInput(prompt);
		}
		
		return email;
	}
	
	
	public static String requestCustomerEmail() {
		
		String customerEmail = readEmailInput("Customer Email : ");
		String confirmEmail = readEmailInput("Confirm Email : ");
		
		try {
			if(!(customerEmail.equals(confirmEmail)))
			{
				throw new InputDoesNotMatchException("Email Inputs do not match !!");
			}
		}
		catch(InputDoesNotMatchException e)
		{
			logger.info("\nInputDoesNotMatchException : " + e.getMessage());
			logger.info("Enter Inputs again");
			customerEmail = requestCustomerEmail();
		}
		return customerEmail;
		
	}
	
}

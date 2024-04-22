package Controllers;

import java.sql.SQLException;
import java.util.Scanner;


import UserCustomExceptions.*;

public class ShoppingManagementIndex {
    
    public static void main(String[] args) throws SQLException, InvalidGenderException, InvalidPasswordException, InvalidPhoneNumberException, InvalidLocationException, InvalidPincodeException, InvalidEmailException, InvalidIdException{
        Scanner inputMain = new Scanner(System.in);
      //  AdminProcess adminProcess = new AdminProcess();
        
     do {
	    System.out.println("Menu");
	    System.out.println("1. Admin");
	    System.out.println("2. User");
	    System.out.println("3. Exit");
	    System.out.print("Enter your choice: ");
	    
	    if (inputMain.hasNextInt()) {
	        int choice = inputMain.nextInt();
	        inputMain.nextLine(); // Consume newline
	        AdminProcess adminProcess1 = new AdminProcess();

	        switch (choice) {
	            case 1:
	                adminProcess1.admin();
	                break;
	            case 2:
	                adminProcess1.admin();
	                break;
	            case 3:
	                System.out.println("Thank you");
	                System.exit(0);
	                break;
	            default:
	                System.out.println("Invalid choice. Please enter a valid choice.");
	        }
	    } else {
	        System.out.println("Invalid input. Please enter a valid integer choice.");
	        inputMain.next(); 
	    }
	} while (true);
    }
}

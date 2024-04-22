package Controllers;

import UserCustomExceptions.*;
import java.sql.SQLException;
import java.util.Scanner;
import DBConnections.DBConnectTable;

public class AdminProcess {
    private Scanner scanner = new Scanner(System.in);

    public void admin() throws InvalidGenderException, InvalidPasswordException, InvalidPhoneNumberException, InvalidLocationException, InvalidPincodeException, InvalidEmailException, InvalidIdException {
        int choice;

        do {
            displayMenu();
            choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    register(scanner);
                    break;
                case 2:
                    LoginProcess loginprocess = new LoginProcess();
                    loginprocess.Login();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 3);

        scanner.close();
    }

    private void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice(Scanner scanner) {
        return scanner.nextInt();
    }

    private void register(Scanner scanner) throws InvalidGenderException, InvalidPasswordException, InvalidPhoneNumberException, InvalidLocationException, InvalidPincodeException, InvalidEmailException {
        String userId, name, gender, password, phoneNo, email, location, pincode;

        userId = validateUserId(scanner);
        name = validateName(scanner);
        gender = validateGender(scanner);
        password = validatePassword(scanner);
        phoneNo = validatePhoneNumber(scanner);
        email = validateEmail(scanner);
        location = validateLocation(scanner);
        pincode = validatePincode(scanner);

        DBConnectTable dbConnectTable = new DBConnectTable();
		dbConnectTable.ConnectDB();

		String sql = "INSERT INTO SHOPPINGMANAGEMENTDBTABLE (UserId, Name, Gender, Password, PhoneNo, Email, Location, Pincode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] params = {userId, name, gender, password, phoneNo, email, location, pincode};

		int rowsInserted = dbConnectTable.InsertDB(sql, params);
		if (rowsInserted > 0) {
		    System.out.println("User was inserted successfully!");
		}
    }

    private String validateUserId(Scanner scanner) {
        String userId;
        boolean validUserId;

        do {
        	scanner.nextLine();
            System.out.print("Enter the USER ID [A1001- Admin/ C1001--Customer]: ");
            userId = scanner.nextLine();
            validUserId = isValidUserId(userId);

            if (!validUserId) {
                System.out.println("Invalid ID: ID must contain only alphanumeric characters.");
            }
        } while (!validUserId);

        return userId;
    }

    private boolean isValidUserId(String userId) {
        return userId.matches("^[a-zA-Z0-9]+$");
    }

    private String validateName(Scanner scanner) {
        String name;
        boolean validName;

        do {
            System.out.print("Enter Name: ");
            name = scanner.nextLine();
            validName = isValidName(name);

            if (!validName) {
                System.out.println("Invalid Name format. It should not contain any numbers or special characters.");
            }
        } while (!validName);

        return name;
    }

    private boolean isValidName(String name) {
        return name.matches("^[a-zA-Z\\s]+$");
    }

    private String validateGender(Scanner scanner) throws InvalidGenderException {
        String gender;

        do {
            System.out.print("Enter Gender (Male/Female): ");
            gender = scanner.nextLine();

            if (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")) {
                System.out.println("Invalid Gender format. It should be either Male or Female.");
            }
        } while (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female"));

        return gender;
    }

    private String validatePassword(Scanner scanner) throws InvalidPasswordException {
        String password;

        do {
            System.out.print("Enter Password: ");
            password = scanner.nextLine();

            if (!isValidPassword(password)) {
                System.out.println("Invalid Password format. It should contain at least one lowercase, one uppercase, one digit, and be at least 8 characters long.");
            }
        } while (!isValidPassword(password));

        return password;
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$");
    }

    private String validatePhoneNumber(Scanner scanner) throws InvalidPhoneNumberException {
        String phoneNo;

        do {
            System.out.print("Enter Phone Number: ");
            phoneNo = scanner.nextLine();

            if (!isValidPhoneNumber(phoneNo)) {
                System.out.println("Invalid Phone Number format. It should be a 10 digit number.");
            }
        } while (!isValidPhoneNumber(phoneNo));

        return phoneNo;
    }

    private boolean isValidPhoneNumber(String phoneNo) {
        return phoneNo.matches("^[0-9]{10}$");
    }

    private String validateEmail(Scanner scanner) throws InvalidEmailException {
        String email;

        do {
            System.out.print("Enter Email: ");
            email = scanner.nextLine();

            if (!isValidEmail(email)) {
                System.out.println("Invalid Email format.");
            }
        } while (!isValidEmail(email));

        return email;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
    }

    private String validateLocation(Scanner scanner) throws InvalidLocationException {
        String location;

        do {
            System.out.print("Enter Location: ");
            location = scanner.nextLine();

            if (!isValidLocation(location)) {
                System.out.println("Invalid Location format. It should not contain any numbers or special characters.");
            }
        } while (!isValidLocation(location));

        return location;
    }

    private boolean isValidLocation(String location) {
        return location.matches("^[a-zA-Z\\s]+$");
    }

    private String validatePincode(Scanner scanner) throws InvalidPincodeException {
        String pincode;

        do {
            System.out.print("Enter Pincode: ");
            pincode = scanner.nextLine();

            if (!isValidPincode(pincode)) {
                System.out.println("Invalid Pincode format. It should be a 6 digit number.");
            }
        } while (!isValidPincode(pincode));

        return pincode;
    }

    private boolean isValidPincode(String pincode) {
        return pincode.matches("^[0-9]{6}$");
    }
}

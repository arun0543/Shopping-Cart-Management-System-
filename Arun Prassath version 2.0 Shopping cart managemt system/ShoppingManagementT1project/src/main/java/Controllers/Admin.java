package Controllers;

import DBConnections.DBConnectProductTable;
import DBConnections.DBConnectTable;
import UserCustomExceptions.*;

import java.sql.SQLException;
import java.util.Scanner;

public class Admin {
    DBConnectTable connectAdmin = new DBConnectTable();
    private Scanner input = new Scanner(System.in);

    public void AdminManage() throws SQLException{
        int choice;

        do {
            displayAdminMenu();
            choice = getUserChoice(input);

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    UpdatedProduct();
                    break;
                case 3:
                    DeleteProduct();
                    break;
                case 4:
                    CustomerProduct view = new CustomerProduct();
                    view.readAllData();
                    break;
                case 5:
                    PaymentProcess paymentview = new PaymentProcess();
                    paymentview.ViewPaymentstatus();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (true);
    }

    private void displayAdminMenu() {
        System.out.println("+++++++++++++++++++++++++++");
        System.out.println(" |                         Welcome                  |");
        System.out.println("++++++++++++++++++++++++++++");

        System.out.println("AdminMenu");
        System.out.println("1. Add Product");
        System.out.println("2. Update Product ");
        System.out.println("3. Delete Product / Profile USE userID or productID  : ");
        System.out.println("4. View Product");
        System.out.println("5. View Payment Status");
        System.out.println("6. Exit");
        System.out.print("Enter your Choice: ");
    }

    private int getUserChoice(Scanner scanner) {
        return scanner.nextInt();
    }

    public void addProduct() throws SQLException{
        String productID, productName, productDescription, productType;
        int pricePerUnit, quantity;

        productID = validateProductID(input);
        productName = validateProductName(input);
        productDescription = validateProductDescription(input);
        productType = validateProductType(input);
        pricePerUnit = validatePricePerUnit(input);
        quantity = validateQuantity(input);

        DBConnectProductTable productConnect = new DBConnectProductTable();
        productConnect.ConnectDB();

        String sql = "INSERT INTO Products (ProductID, ProductName, productDescription, Type, Priceperunit, Quantity) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] params = {productID, productName, productDescription, productType, pricePerUnit, quantity};

        int rowsInserted = productConnect.InsertDB(sql, params);
        if (rowsInserted > 0)
            System.out.println("Product was inserted successfully!");
        else
            System.out.println("Failed to insert the product.");
    }

    private String validateProductID(Scanner scanner)  {
        String productid;
        boolean validProductid;

        do {
            scanner.nextLine();
            System.out.print("Enter the Product ID: ");
            productid = scanner.nextLine();
            validProductid = isValidProductID(productid);

            if (!validProductid) {
                //throw new InvalidIdException("Invalid ID: ID must contain only alphanumeric characters.");
            	System.out.println("Invalid ID: ID must contain only alphanumeric characters.");
            }
        } while (!validProductid);

        return productid;
    }

    private boolean isValidProductID(String productID) {
        return productID.matches("^[a-zA-Z0-9]+$");
    }

    private String validateProductName(Scanner scanner) {
        String name;
        boolean validName;

        do {
            System.out.print("Enter Product Name: ");
            name = scanner.nextLine();
            validName = isValidProductName(name);

            if (!validName) {
               // throw new InvalidProductNameException("Invalid Product Name format. It should not contain any numbers or special characters.");
            	System.out.println("Invalid Product Name format. It should not contain any numbers or special characters.");
            }
        } while (!validName);

        return name;
    }

    private boolean isValidProductName(String name) {
        return name.matches("^[a-zA-Z\\s]+$");
    }

    private String validateProductDescription(Scanner scanner)  {
        String description;
        boolean validDescription;

        do {
            System.out.print("Enter Product Description: ");
            description = scanner.nextLine();
            validDescription = isValidProductDescription(description);

            if (!validDescription) {
//                throw new InvalidProductDescriptionException("Invalid Product Description format. It should not contain any special characters.");
            	System.out.println("Invalid Product Description format. It should not contain any special characters");
            }
        } while (!validDescription);

        return description;
    }

    private boolean isValidProductDescription(String description) {
        return description.matches("^[a-zA-Z0-9\\s]+$");
    }

    private String validateProductType(Scanner scanner)  {
        String type;
        boolean validType;

        do {
            System.out.print("Enter the product type: ");
            type = scanner.nextLine();
            validType = isValidProductType(type);

            if (!validType) {
              //  throw new InvalidProductTypeException("Invalid Product Type format. It should not contain any numbers or special characters.");
            	System.out.println("Invalid Product Type format. It should not contain any numbers or special characters.");
            }
        } while (!validType);

        return type;
    }

    private boolean isValidProductType(String type) {
        return type.matches("^[a-zA-Z\\s]+$");
    }

    private int validatePricePerUnit(Scanner scanner)  {
        int pricePerUnit;

        do {
            System.out.print("Enter the price per unit (Rs.): ");
            pricePerUnit = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (pricePerUnit <= 0) {
            //    throw new InvalidPricePerUnitException("Price per unit must be a positive number.");
            	System.out.println("Price per unit must be a positive number.");
            }
        } while (pricePerUnit <= 0);

        return pricePerUnit;
    }

    private int validateQuantity(Scanner scanner)  {
        int quantity;

        do {
            System.out.print("Enter the Quantity: ");
            quantity = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (quantity <= 0) {
             //   throw new InvalidQuantityException("Quantity must be a positive number.");
            	System.out.println("Quantity must be a positive number.");
            }
        } while (quantity <= 0);

        return quantity;
    }

	
	

	public void UpdatedProduct() throws SQLException{
		Scanner scanner=new Scanner(System.in);
			connectAdmin.ConnectDB();

	        // Step 3: Prompt the user for the ProductID to update
      System.out.print("Enter the ProductID you want to update-- ");
//	        String productID = input.nextLine();
			String productID=validateProductID(scanner);

	        // Step 4: Prompt the user for the field to update
	        System.out.println("Which field do you want to update?");
	        System.out.println("1. Product Name");
	        System.out.println("2. Product Description");
	        System.out.println("3. Product Type");
	        System.out.println("4. Price per Unit");
	        System.out.println("5. Quantity");
	        System.out.print("Enter your choice: ");
	        int choice = input.nextInt();
	        input.nextLine(); // Consume the newline character

	        // Step 5: Prompt the user for the new value
	        String newValue;
	        String value = null;
	        String productColumnName = null;
	        switch (choice) {
	            case 1:
	                System.out.print("Enter the new Product Name: ");
	                newValue = input.nextLine();
	                productColumnName="ProductName";
	                value=newValue;
	         //       updateField(connection, productID, "ProductName", newValue);
	                break;
	            case 2:
	                System.out.print("Enter the new Product Description: ");
	                newValue = input.nextLine();
	                value=newValue;
	                productColumnName="productDescription";
	             //   updateField(connection, productID, "productDescription", newValue);
	                break;
	            case 3:
	                System.out.print("Enter the new Product Type: ");
	                newValue = input.nextLine();
	                value=newValue;
	                productColumnName="Type";
	               // updateField(connection, productID, "Type", newValue);
	                break;
	            case 4:
	                System.out.print("Enter the new Price per Unit: ");
	                int pricePerUnit = input.nextInt();
	                productColumnName="Priceperunit";
	                value=String.valueOf(pricePerUnit);
	                input.nextLine(); // Consume the newline character
	             //   updateField(connection, productID, "Priceperunit", String.valueOf(pricePerUnit));
	                break;
	            case 5:
	                System.out.print("Enter the new Quantity: ");
	                int quantity = input.nextInt();
	                input.nextLine(); // Consume the newline character
	                productColumnName="Quantity";
	                value=String.valueOf(quantity);
	                
	                //updateField(connection, productID, "Quantity", String.valueOf(quantity));
	                break;
	            default:
	                System.out.println("Invalid choice.");
	        }
	        String tablename="Products";
	        String primary="ProductID";
	        connectAdmin.updateField(productID,productColumnName,value,tablename,primary);
	
	        // Step 6: Close the connection
	      /// conn.close();
	} 

	
	
	

    public void DeleteProduct() throws SQLException {
    	Scanner scanner=new Scanner(System.in);
        System.out.print("Enter the ID you want to delete: ");
        
        String id = validateProductID(scanner);
        String table;
        String idColumn;
        String entity;

        if (id.startsWith("P")) {
            table = "Products";
            idColumn = "ProductID";
            entity = "Product";
        } else if (id.startsWith("A") || id.startsWith("C")) {
            table = "ShoppingManagementDBTable";
            idColumn = "UserID";
            entity = "Profile";
        } else {
            System.out.println("Invalid ID.");
            return;
        }
        DBConnectTable connectAdmin=new DBConnectTable();
        
        connectAdmin.ConnectDB();

        connectAdmin.DeleteProfileProduct(table,idColumn,entity,id);
        
    }
        
       /* try {
            Class.forName("oracle.jdbc.OracleDriver");
            String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
            String username = "SHOPPINGMANAGEMENT";
            String password = "1234";
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);  */
/*
            String sql = "DELETE FROM " + table + " WHERE " + idColumn + " = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println(entity + " deleted successfully.");
            } else {
                System.out.println("No " + entity + " found with " + idColumn + " = " + id);
            }

            stmt.close();           */
        /*    conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }*/



	
}

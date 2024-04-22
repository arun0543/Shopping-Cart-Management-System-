package Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import DBConnections.DBConnectCommon;
import DBConnections.DBConnectTable;

public class CustomerProduct {
	PaymentProcess paymentprocess1=new PaymentProcess();

	DBConnectTable CustomerConnect=new DBConnectTable();
	
	CartInfo usecart=new CartInfo();
	Scanner input1=new Scanner(System.in);

    public void customerPage() {
    	Scanner input=new Scanner(System.in);
    	
      //  clearConsole();
        System.out.println("++====================================================+");
        System.out.println("|            WELCOME TO CUSTOMER SECTION              |");
        System.out.println("+=====================================================+");

        do {
            System.out.println("| 1 - VIEW PRODUCTS LIST                    |");
            System.out.println("| 2 - SEARCH A PRODUCT NAMEWISE             |");
            System.out.println("| 3 - SEARCH PRODUCTS TYPEWISE              |");
            System.out.println("| 4 - ADD PRODUCT TO CART                   |");
            System.out.println("| 5 - REMOVE PRODUCT FROM CART              |");
            System.out.println("| 6 - VIEW CART                             |");
            System.out.println("| 7 - PROCEED TO PAYMENT                   |");
            System.out.println("| 8 - VIEW PAYMENT STATUS                         |");
            System.out.println("| 9 - LOGOUT FROM SYSTEM                    |");

            System.out.println("Please enter your choice: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                	readAllData();
                    break;
                case 2:
                    searchProductNamewise();
                    break;
                
                case 3:
                    searchProductsTypewise();
                    break;
                    
                case 4:
                	usecart.addProductToCart();
                    break;
                case 5:
                	usecart.removeProductFromCart();
                    break;  
                case 6:
                	usecart. viewCart();
                    break;
                case 7:
                	paymentprocess1.proceedToPayment();
                    break;
                case 8:
                   paymentprocess1.ViewPaymentstatus();
                    break;
                case 9:
                    System.out.println("Thanks for visiting ");
                    break;
                
                default:
                    System.out.println("Invalid Choice. Try again.");
            }

        } while (true);
    }

    
    public void readAllData()  {
    	//CustomerProduct connectObj=new CustomerProduct();
    	
    	CustomerConnect.ConnectDB();
    	 String sql = "SELECT * FROM products";
    	 
    	 try {
    	 ResultSet resultSet =CustomerConnect.ReadAllDB(sql);
    	 CustomerConnect.printResultSet(resultSet);
    	 resultSet.close();
    	 } catch (SQLException e) {
    	 e.printStackTrace();
    	 } /*finally {
    		 connectObj.CloseConnection(); 
    	 }*/
    	}


    public static void searchProductNamewise() {
    	Scanner scanner=new Scanner(System.in);
    	DBConnectTable CustomerConnect=new DBConnectTable();
  
    	CustomerConnect.ConnectDB();
    	


           System.out.print("Enter product name: ");
           
           String productName=validateNameBy(scanner);
            CustomerConnect.Searchproductname(productName);

            
    }
    private static  String validateNameBy(Scanner scanner) {
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

    private static boolean isValidName(String name) {
        return name.matches("^[a-zA-Z\\s]+$");
    }
    
    
   


	public void searchProductsTypewise() {
    	Scanner scanner=new Scanner(System.in);
    	DBConnectTable CustomerConnect=new DBConnectTable();
  
    	CustomerConnect.ConnectDB();


            System.out.print("Enter product Type : ");
            String productType= validateNameBy(scanner);
            String TableName="products";
            CustomerConnect.ProductTypeSearch(productType,TableName);

            //.close();
    	
    }
    
    class CartInfo
    {

    public void  addProductToCart() {
    	Scanner scanner=new Scanner(System.in);
    	
    	DBConnectTable connectCart=new DBConnectTable();
    	connectCart.ConnectDB();
    	CustomerProduct returnCustomerview=new CustomerProduct();
    System.out.println()	;
  //  System.out.println(getUserID(LPhoneNo));
        String cartID,productID,ID;
        System.out.println("Enter the cartID : ");
        cartID=validateID(scanner);
//    	String cartID=input1.nextLine();
//   
        System.out.println("Enter the ProductID : ");
        productID=validateID(scanner);
//    	String productID=input1.nextLine();
//    	
        System.out.println("Enter the User ID : ");
        ID=validateID(scanner);
//    	String ID=input1.nextLine();
    	String tablename="ShoppingManagementDBTable";
    	String Tableprimary="userid";
    	String record="Phoneno";
    	connectCart.selectrecordDisplayByID(tablename,Tableprimary, ID,record);
    	
    	String retrieve=connectCart.selectrecordDisplayByID(tablename,Tableprimary, ID,record);
    	System.out.println("To verify is this you . Your phone no : "+retrieve);
    	System.out.println("to continue yes(Y)/No(N) : ");
    	String verify=input1.nextLine();
    	if(verify.equalsIgnoreCase("Y")) {
    		System.out.println("Enter the quantity : ");
        	int Quantity=input1.nextInt();
        	String tablename_P="products";
        	String primary_P="productid";
        	String ID_P=productID;
        	String record_P="priceperunit";
       
        	String priceperunitas=connectCart.selectrecordDisplayByID(tablename_P,primary_P, ID_P,record_P);
        	int  priceperunitasvalue= Integer.parseInt(priceperunitas);
        	
        	int totalcost=priceperunitasvalue*Quantity;
        	System.out.println("The Total cost of  "+productID+"   :   Rs."+totalcost);
        	
        	System.out.println("Confirm  to add cart this product : Yes(Y)/No(N) : ");
        	String cartverify=input1.next();
        	if(cartverify.equalsIgnoreCase("Y")) {
        		 String sql = "INSERT INTO cart (CARTID, PRODUCTID, USERID, QUANTITY, Priceperunit, TOTALCOST) VALUES (?, ?, ?, ?, ?, ?)";
        	        Object[] params = {cartID, productID, ID, Quantity, priceperunitasvalue,totalcost};

        	        int rowsInserted = connectCart.InsertDB(sql, params);
        	        if (rowsInserted > 0) {
        	        	try {
        	            System.out.println("Product was inserted successfully!");
        	        	String record_Q="Quantity";
        	        
        	        	String INproductQuantity=connectCart.selectrecordDisplayByID(tablename_P,primary_P, ID_P,record_Q);
        	        	int   INproductQuantityValue= Integer.parseInt( INproductQuantity);
        	        	int reduceQuantity= INproductQuantityValue-Quantity;
        	        	//String tablename="Products";
        		      //  String primary="ProductID";
        	        	//String productColumnName="Quantity";
        	        	String value=String.valueOf(reduceQuantity);
        	        	connectCart.updateField(productID,record_Q,value,tablename_P,primary_P);
        	        	}catch(SQLException e) {
        	        		System.out.println(e.getMessage());
        	        	}
        	        }
        	        else {
        	            System.out.println("Failed to insert the product.");
        	        }
        		
        	}else {
        		returnCustomerview.customerPage();
        	}
        	
        	
    	}
    	else {
    		
    		returnCustomerview.customerPage();
    	}
    	}
    	
    private String validateID(Scanner scanner) {
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
    /*	
    	System.out.println("Enter the quantity : ");
    	int Quantity=input1.nextInt();
    	
    	
    	*/
   // }
    
    
    
    
    
    
    public void  removeProductFromCart() {
    	Scanner scanner=new Scanner(System.in);
    	try {
    		CustomerConnect.ConnectDB();
    	System.out.print("Enter the CartID(Ex..D1001) : ");
    	String RemoveCart=validateID(scanner);
    	//String RemoveCart=input1.nextLine();
    	String tablename_C="cart";
    	String ID_C=RemoveCart;
    	String primary_C="cartid";
    	String record_C="Quantity";
    	String AddQuantity=CustomerConnect.selectrecordDisplayByID(tablename_C,primary_C, ID_C,record_C);
    	
    	int AddQuantityvalue=Integer.parseInt(AddQuantity);
    	String record_Productid="PRODUCTID";
    	
    	String RetriveProductId=CustomerConnect.selectrecordDisplayByID(tablename_C,primary_C, ID_C,record_Productid);
    	//String INproductQuantity=connectCart.selectrecordDisplayByID(tablename_P,primary_P, ID_P,record_Q);
    	String ExistingQuantity=CustomerConnect.selectrecordDisplayByID("products","productid", RetriveProductId,"Quantity");
    	int ExistingQuantityvalue=Integer.parseInt(ExistingQuantity);
    	
    	int QuantityUpdate=ExistingQuantityvalue+AddQuantityvalue;
    	String value11=String.valueOf(QuantityUpdate);
    	String tablename_P1="products";
    	String primary_p1="productid";
    	
    	CustomerConnect.updateField( RetriveProductId, record_C,value11,tablename_P1,primary_p1);
    	
    	CustomerConnect.DeleteProfileProduct("cart","cartid","cart product",RemoveCart);
    	
    	}catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	
    }
    
    
    public void viewCart()  {
    	//CustomerProduct connectObj=new CustomerProduct();
    	
    	CustomerConnect.ConnectDB();
    	 String sql = "SELECT * FROM cart";
    	 
    	 try {
    	 ResultSet resultSet =CustomerConnect.ReadAllDB(sql);
    	 CustomerConnect.printResultSet(resultSet);
    	 resultSet.close();
    	 } catch (SQLException e) {
    	 e.printStackTrace();
    	 } /*finally {
    		 connectObj.CloseConnection(); 
    	 }*/
    	}
    }
    
    
   
    
    
    
  /*  private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            System.err.println("Failed to clear console: " + ex.getMessage());
        }
    }   */
}

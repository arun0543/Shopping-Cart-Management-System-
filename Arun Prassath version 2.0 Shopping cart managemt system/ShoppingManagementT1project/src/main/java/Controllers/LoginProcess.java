package Controllers;
import java.sql.Connection;
import Controllers.Admin;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import Controllers.CustomerProduct;
//import UserCustomExceptions.InvalidIdException;
public class LoginProcess {
	
	
	public void Login()   {
		try {
		Scanner input=new Scanner(System.in);
		System.out.println("Enter your Phone number : ");
		String LPhoneNo=input.nextLine();
		System.out.println("Enter your Password : ");
		String LPassword=input.nextLine();
		

		if(authenticate(LPhoneNo,LPassword)) {
			System.out.println("Login successfull ");
			System.out.println(getUserID(LPhoneNo));
			if(getUserID(LPhoneNo).startsWith("A")) {
				Admin adminobj=new Admin();
				adminobj.AdminManage();
			}else {
				CustomerProduct customerproduct=new CustomerProduct();
				customerproduct.customerPage();
				
			}
		}
		else {
			System.out.println("Invalid Credentials ");
		}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean authenticate(String phoneNo, String password) {
	    boolean isAuthenticated = false;
	    
	    // Replace the placeholders with your actual database connection details
	    String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
	    String username = "SHOPPINGMANAGEMENT";
	    String dbPassword = "1234";
	    
	    try (Connection conn =DriverManager.getConnection(jdbcUrl, username, dbPassword )) {
	        // Prepare the SQL query
	        String query = "SELECT COUNT(*) FROM SHOPPINGMANAGEMENTDBTABLE WHERE phoneno = ? AND password = ?";
	        PreparedStatement stmt = conn.prepareStatement(query);
	        stmt.setString(1, phoneNo);
	        stmt.setString(2, password);
	        
	        // Execute the query and check the result
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            if (count > 0) {
	                isAuthenticated = true;
	            }
	        }
	        
	        rs.close();
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return isAuthenticated;
	}
	
	///////////
	public static String getUserID(String phoneNo) {
	    String userID = null;
	    
	    // Replace the placeholders with your actual database connection details
	    String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
	    String username = "SHOPPINGMANAGEMENT";
	    String dbPassword = "1234";
	    
	    try (Connection conn = DriverManager.getConnection(jdbcUrl, username, dbPassword)) {
	        // Prepare the SQL query
	        String query = "SELECT userID FROM SHOPPINGMANAGEMENTDBTABLE WHERE phoneNo = ?";
	        PreparedStatement stmt = conn.prepareStatement(query);
	        stmt.setString(1, phoneNo);
	        
	        // Execute the query and retrieve the userID
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            userID = rs.getString("userID");
	        }
	        
	        rs.close();
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return userID;
	}
	
	
	
}

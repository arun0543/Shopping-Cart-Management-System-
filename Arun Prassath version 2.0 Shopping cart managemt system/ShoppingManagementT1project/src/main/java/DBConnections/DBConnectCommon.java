package DBConnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectCommon {
	
	public void ConnectDBProducts() {
		try {
	    // Step 1: Load the JDBC driver
        Class.forName("oracle.jdbc.OracleDriver");

        // Step 2: Establish the connection
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
        String username = "SHOPPINGMANAGEMENT";
        String password = "1234";
        Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
        
        // Step 3: Create a statement
        Statement stmt = conn.createStatement();
        
        
        
	}catch (ClassNotFoundException | SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
		
	}

}

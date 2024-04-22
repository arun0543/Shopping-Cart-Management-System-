package DBConnections;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Controllers.Admin;
public class DBConnectProductTable {

	
	    
	    Connection connection;
	    
	    public void ConnectDB() {
	        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
	        String username = "SHOPPINGMANAGEMENT";
	        String password = "1234";

	        try {
	            Class.forName("oracle.jdbc.OracleDriver");
	            connection = DriverManager.getConnection(jdbcUrl, username, password);
	        } catch (ClassNotFoundException | SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }
	    
	    public int InsertDB(String sql, Object[] params) {
	        int result = 0;
	        try {
	            PreparedStatement statement = connection.prepareStatement(sql);
	            for (int i = 0; i < params.length; i++) {
	                statement.setObject(i + 1, params[i]);
	            }
	            result = statement.executeUpdate();
	           // return result;
	         // **   statement.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	}
	    

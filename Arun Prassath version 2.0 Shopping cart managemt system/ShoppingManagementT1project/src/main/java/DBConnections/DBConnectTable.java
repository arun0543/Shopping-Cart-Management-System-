package DBConnections;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
/*import java.util.Scanner;
import Controllers.*;
import CustomExceptions.EmailException;
import CustomExceptions.NameException;
import CustomExceptions.PasswordException;
import CustomExceptions.PhoneNoException;
import CustomExceptions.PincodeException;
*/
public class DBConnectTable {
    
    Connection connection;
	//private String table;
	///private String idColumn;
	//public Object ConnectDB;
    
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
    
    
    public void updateField(String productID,String productColumnName,String newValue,String tablename,String primary) throws SQLException {
	    String sql = "UPDATE "+tablename+"  SET " + productColumnName + " = ? WHERE "+primary+" = ?";
	    PreparedStatement stmt = connection.prepareStatement(sql);
	    stmt.setString(1, newValue);
	    stmt.setString(2, productID);
	    int rowsUpdated = stmt.executeUpdate();

	    if (rowsUpdated > 0) {
	        System.out.println("Product updated successfully.");
	    } else {
	        System.out.println("No product found with ProductID = " + productID);
	    }

	    stmt.close();
	}
    
    
    public ResultSet ReadAllDB(String sql) throws SQLException {                //  READ ALL DATA FROM TABLE 
    	 PreparedStatement statement = connection.prepareStatement(sql);
    	 return statement.executeQuery();
    	 }
    	 
    	 public void printResultSet(ResultSet resultSet) throws SQLException {
    	 ResultSetMetaData metaData = resultSet.getMetaData();
    	 int columnCount = metaData.getColumnCount();
    	 while (resultSet.next()) {
    	 StringBuilder row = new StringBuilder();
    	 for (int i = 1; i <= columnCount; i++) {
    	 if (i > 1) {
    	 row.append(" \n ");
    	 }
    	
    	String columnName = metaData.getColumnName(i);
    	 Object value = resultSet.getObject(i);
    	 row.append(columnName).append(": ").append(value); 
    	 }
    	 System.out.println(row.toString());
    	 System.out.println("*--------------------***--------------------------- *");
    	 }
    	 }


    
    
    
    
    
    
    
    public void DeleteProfileProduct(String table,String idColumn,String entity,String id) throws SQLException {
    	
    	  String sql = "DELETE FROM " + table + " WHERE " + idColumn + " = ?";
          PreparedStatement stmt = connection.prepareStatement(sql);
          stmt.setString(1, id);
          int rowsDeleted = stmt.executeUpdate();

          if (rowsDeleted > 0) {
              System.out.println(entity + " deleted successfully.");
          } else {
              System.out.println("No " + entity + " found with " + idColumn + " = " + id);
          }

          stmt.close();
    	
    }
    
    
    
    
    
    
    
    
    
    
    public ResultSet selectDB(String sql, Object[] params) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    
    

public void Searchproductname(String  productName) {
	try {
    String sql = "SELECT PRODUCTID, PRODUCTNAME, TYPE, QUANTITY, PRICEPERUNIT FROM PRODUCTS WHERE PRODUCTNAME LIKE ?";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setString(1, "%" + (productName.toUpperCase())+ "%"); 

    // Step 5: Execute the query
    ResultSet rs = stmt.executeQuery();

    // Step 6: Process result set
    System.out.println("Product ID\tProduct Name\tProduct Type\tQuantity\tPrice Per Unit");
    while (rs.next()) {
        String productId = rs.getString("PRODUCTID");
        String name = rs.getString("PRODUCTNAME");
        String type = rs.getString("TYPE");
        int quantity = rs.getInt("QUANTITY");
        double pricePerUnit = rs.getDouble("PRICEPERUNIT");
        System.out.println(productId + "\t\t" + name + "\t\t" + type + "\t\t" + quantity + "\t\t" + pricePerUnit);
    }

    // Step 7: close
    rs.close();
    stmt.close();
    
} catch (SQLException e) {
    System.out.println("Error: " + e.getMessage());
}
}


// ProductTypeSearch starts
public void ProductTypeSearch(String productType,String TableName) {
	try {
String sql = "SELECT * FROM PRODUCTS WHERE TYPE LIKE ?";

// Read data from the database
PreparedStatement stmt = connection.prepareStatement(sql);
stmt.setString(1, "%" + productType.toUpperCase() + "%");
ResultSet rs = ReadAllDB(stmt);

// Print the result set
printResultSet1(rs);

// Close resources
rs.close();
stmt.close();



} catch(SQLException e) {
	System.out.println("Error: " + e.getMessage());
}
}

private static ResultSet ReadAllDB(PreparedStatement statement) throws SQLException {
    return statement.executeQuery();
}

public  static void printResultSet1(ResultSet resultSet) throws SQLException {
    ResultSetMetaData metaData = resultSet.getMetaData();
    int columnCount = metaData.getColumnCount();
    while (resultSet.next()) {
        StringBuilder row = new StringBuilder();
        for (int i = 1; i <= columnCount; i++) {
            if (i > 1) {
                row.append(" - ");
            }
            String columnName = metaData.getColumnName(i);
            Object value = resultSet.getObject(i);
            row.append(columnName).append(": ").append(value);
        }
        System.out.println(row.toString());
    }
}
// productTypeSearch Ends



/*
// select record to display starts 
public void  selectrecordDisplayByID(String tablename, String Tableprimary, String ID, String record) {
   String name = "";
    try {
        String sql = "SELECT " + record + " FROM " + tablename + " WHERE " + Tableprimary + " = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, ID);

        // Step 5: Execute the query
        ResultSet rs = stmt.executeQuery();

        // Process the result set
        if (rs.next()) {
            name = rs.getString(record);
          /*  if (name == null) {
                name = ""; // Assign an empty string if the value is null
            }*/
      /*  }
        System.out.println(name);

        // Step 7: Close resources
        rs.close();
        stmt.close();
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }

  //  return name;
} 
*/



public String selectrecordDisplayByID(String  tablename,String tableprimary,String ID,String record) {
	//String row;
	String phoneno = null;
	try {
    String sql = "SELECT " +record+" FROM "+tablename+" WHERE "+tableprimary+" = ?";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setString(1,ID); 

    // Step 5: Execute the query
    ResultSet rs = stmt.executeQuery();

    while (rs.next()) {
//        String productId = rs.getString(tableprimary);
        phoneno = rs.getString(record);
 
    }
   // String row=name;
  
    // Step 7: close
    rs.close();
    stmt.close();
    
    
} catch (SQLException e) {
    System.out.println("Error: " + e.getMessage());
}
	return phoneno ;
	//return row;   
	//String name;
	
}
}




//
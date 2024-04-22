package DBConnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertData {
    
    Connection connection;
    
    public void ConnectDB() {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
        String username = "system";
        String password = "1234";

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static void main(String[] args) {
        InsertData objID = new InsertData();
        Scanner scanner = new Scanner(System.in);
        objID.ConnectDB();
        
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter Name: ");
        String name = scanner.next();
        System.out.print("Enter Department: ");
        String dept = scanner.next();
        System.out.print("Enter Email: ");
        String email = scanner.next();

        String sql = "INSERT INTO Student (Id, Name, Course, email) VALUES (?, ?, ?, ?)";
        Object[] params = { id, name, dept, email };

        int rowsInserted = objID.InsertDB(sql, params);
        if (rowsInserted > 0) 
            System.out.println("User was inserted successfully!");
        
        scanner.close();
        try {
            objID.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

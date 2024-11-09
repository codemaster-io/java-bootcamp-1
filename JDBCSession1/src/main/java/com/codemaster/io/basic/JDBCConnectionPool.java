package com.codemaster.io.basic;

import com.codemaster.io.connection.ConnectionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCConnectionPool {

    public static void main(String[] args) {
        String dbUrl = "localhost:3306";
        String dbName = "bootcamp_db";
        String username = "root";
        String password = "rootpassword";

        DataSource dataSource = ConnectionManager.getDataSourceHikariCp(dbUrl, dbName, username, password);

        try {
            // Establish the connection
            Connection connection = dataSource.getConnection();
            System.out.println("Connected to the database.");

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query
            String sql = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(sql);

            // Process the result
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    // Print column value
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println(); // New line after each row
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.codemaster.io.basic;

import com.codemaster.io.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class SimpleJDBC {

    public static void main(String[] args) throws ClassNotFoundException {
        Class clz = Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("clz.getName() = " + clz.getName());

        String dbUrl = "localhost:3306";
        String dbName = "bootcamp_db";
        String username = "root";
        String password = "rootpassword";

        try {
            // Establish the connection
            Connection connection = ConnectionManager.getConnection( dbUrl, dbName, username, password);
            System.out.println("Connected to the database." + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Connected to the database." + connection.getMetaData().getDatabaseProductName());

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

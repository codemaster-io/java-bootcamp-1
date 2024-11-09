package com.codemaster.io.sqlinjection;

import com.codemaster.io.connection.ConnectionManager;

import java.sql.*;

public class SQLInjectionExample {

    private Connection connection;

    private String dbUrl;
    private String dbName;
    private String username;
    private String password;

    public SQLInjectionExample(String dbUrl, String dbName, String username, String password) {
        this.dbUrl = dbUrl;
        this.dbName = dbName;
        this.username = username;
        this.password = password;

        connection = ConnectionManager.getConnection(dbUrl, dbName, username, password);
    }

    public boolean loginByPlainStatement(String email, String password) {
        try {
            String sql = "SELECT * FROM email_password WHERE " +
                    "email = '" + email + "' AND password = '" + password + "'";
            System.out.println("sql = " + sql);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid credentials.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginPreparedStatement(String email, String password) {
        String sql = "SELECT * FROM email_password WHERE email = ? AND password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set parameters securely
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid credentials.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        String dbUrl = "localhost:3306";
        String dbName = "bootcamp_db";
        String username = "root";
        String password = "rootpassword";

        SQLInjectionExample sqlInjectionExample = new SQLInjectionExample(dbUrl, dbName, username, password);


        String email = "admin@gmail.com";
        String pass = "anything' OR '1'='1";
        sqlInjectionExample.loginByPlainStatement(email, pass);
        // SELECT * FROM email_password WHERE email = 'admin@gmail.com' AND password = 'anything' OR '1'='1'

        sqlInjectionExample.loginPreparedStatement(email, pass);
        // SELECT * FROM email_password WHERE email = 'admin@gmail.com\' OR \'1\'=\'1' AND password = 'anything';
    }
}

/*

-- Create login_info table
CREATE TABLE email_password (
    email VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

-- Insert sample values into login_info
INSERT INTO email_password (email, password) VALUES ('admin@gmail.com', 'password123');
INSERT INTO email_password (email, password) VALUES ('manager@gmail.com', 'securePass!45');
* */

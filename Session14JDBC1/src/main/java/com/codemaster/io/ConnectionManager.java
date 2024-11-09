package com.codemaster.io;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    public static Connection getConnection() throws SQLException {
        String url = "127.0.0.1:3306";
        String username = "root";
        String password = "rootpassword";
        String dbName = "bootcamp_db";


        String jdbcUrl = "jdbc:mysql://"+url+ "/" + dbName;
        System.out.println("jdbcUrl = " + jdbcUrl);

        return DriverManager.getConnection(jdbcUrl,username, password);
    }

    public static DataSource getDataSource() {
        String url = "127.0.0.1:3306";
        String username = "root";
        String password = "rootpassword";
        String dbName = "bootcamp_db";


        String jdbcUrl = "jdbc:mysql://"+url+ "/" + dbName;
        System.out.println("jdbcUrl = " + jdbcUrl);

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        dataSource.setMaximumPoolSize(20);
        dataSource.setConnectionTimeout(30000);

        return dataSource;
    }
}

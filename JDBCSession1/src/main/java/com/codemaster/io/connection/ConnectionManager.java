package com.codemaster.io.connection;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    public static Connection getConnection(String dbUrl, String dbName, String username, String password) {
        try {
            String jdbcUrl = "jdbc:mysql://" + dbUrl + "/" + dbName +"?useSSL=true" +
                    "&requireSSL=true&serverTimezone=UTC";

            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataSource getDataSourceHikariCp(String dbUrl, String dbName, String username, String password) {
        String jdbcUrl = "jdbc:mysql://" + dbUrl + "/" + dbName +"?useSSL=true" +
                "&requireSSL=true&serverTimezone=UTC";

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // Set connection pool size and other properties
        dataSource.setMaximumPoolSize(10);
        dataSource.setConnectionTimeout(30000); // 30 seconds
        dataSource.setIdleTimeout(600000); // 10 minutes

        return dataSource;
    }
}

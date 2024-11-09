package com.codemaster.io;

import javax.sql.DataSource;
import java.sql.*;

public class JDBCMain {


    public static void insertUserAndAuditData(User user) {

        try {
            DataSource dataSource= ConnectionManager.getDataSource();
            Connection connection = dataSource.getConnection();

            try {

                connection.setAutoCommit(false);

                String insertUserQuery = "insert into users (name, email, age, country) values (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery);

                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setInt(3, user.getAge());
                preparedStatement.setString(4, user.getCountry());

                int cnt = preparedStatement.executeUpdate();
                if (cnt > 0) {
                    System.out.println("User inserted");
                }

                String auditQuery = "insert into audit (user_name, operation_type, timestamp) values(?, ?, ?)";

                preparedStatement = connection.prepareStatement(auditQuery);
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, "INSERT");
                preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

                cnt = preparedStatement.executeUpdate();
                if (cnt > 0) {
                    System.out.println("Audit inserted");
                }

                connection.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) throws SQLException {
        User user = User.builder()
                .name("user17")
                .email("user17@gmail.com")
                .age(32)
                .country("BD")
                .build();
        insertUserAndAuditData(user);
    }
}


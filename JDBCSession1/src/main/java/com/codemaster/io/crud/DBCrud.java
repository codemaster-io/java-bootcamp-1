package com.codemaster.io.crud;

import com.codemaster.io.connection.ConnectionManager;
import com.codemaster.io.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBCrud {

    private Connection connection;

    private String dbUrl;
    private String dbName;
    private String username;
    private String password;

    public DBCrud(String dbUrl, String dbName, String username, String password) {
        this.dbUrl = dbUrl;
        this.dbName = dbName;
        this.username = username;
        this.password = password;

        connection = ConnectionManager.getConnection(dbUrl, dbName, username, password);
    }

    public void insertUserByStatement(User user) {
        try {
            String sql = "INSERT INTO users (name, email, age, country) VALUES ('" +
                    user.getName() + "', '" +
                    user.getEmail() + "', " +
                    user.getAge() + ", '" +
                    user.getCountry() + "')";

            Statement statement = connection.createStatement();
            statement.execute(sql);
            System.out.println("Insert is done.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int insertUserByStatementAndReturnGeneratedId(User user) {
        try {
            String sql = "INSERT INTO users (name, email, age, country) VALUES ('" +
                    user.getName() + "', '" +
                    user.getEmail() + "', " +
                    user.getAge() + ", '" +
                    user.getCountry() + "')";
            Statement statement = connection.createStatement();
            int cnt = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println("Inserted Items = " + cnt);
            if(cnt > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()) {
                    int generateId = resultSet.getInt(1);
                    return generateId;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public void insertUserByPrepareStatement(User user) {
        try {
            String sql = "INSERT INTO users (name, email, age, country) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getCountry());
            preparedStatement.execute();
            System.out.println("Insert is done.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int insertUserByPrepareStatementAndReturnGeneratedId(User user) {
        try {
            String sql = "INSERT INTO users (name, email, age, country) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getCountry());

            int cnt = preparedStatement.executeUpdate();

            System.out.println("Inserted Items = " + cnt);
            if(cnt > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()) {
                    int generateId = resultSet.getInt(1);
                    return generateId;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public boolean updateUserByPrepareStatement(User user) {
        try {
            String sql = "UPDATE users SET name = ?, email = ?, age = ?, country = ? WHERE ID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getCountry());
            preparedStatement.setInt(5, user.getId());
            int cnt = preparedStatement.executeUpdate();
            return cnt > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteUserByPrepareStatement(int id) {
        try {
            String sql = "DELETE from users WHERE ID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int cnt = preparedStatement.executeUpdate();
            return cnt > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {

            String sql = "SELECT * from users";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .email(resultSet.getString("email"))
                        .age(resultSet.getInt("age"))
                        .country(resultSet.getString("country"))
                        .build();
                users.add(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public User getUser(int id) {
        try {

            String sql = "SELECT * from users WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return User.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .email(resultSet.getString("email"))
                        .age(resultSet.getInt("age"))
                        .country(resultSet.getString("country"))
                        .build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean transactionalInserts(User user) throws SQLException {

        try {
            String insertUserQuery = "INSERT INTO users (name, email, age, country) VALUES (?, ?, ?, ?)";
            String insertAuditQuery = "INSERT INTO audit (user_name, operation_type, timestamp) VALUES (?, ?, ?)";

            connection.setAutoCommit(false);
            PreparedStatement userStatement = connection.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement auditStatement = connection.prepareStatement(insertAuditQuery);

            // Insert user 1
            userStatement.setString(1, user.getName());
            userStatement.setString(2, user.getEmail());
            userStatement.setInt(3, user.getAge());
            userStatement.setString(4, user.getCountry());
            int cnt = userStatement.executeUpdate();
            if (cnt > 0) {
                System.out.println("Data Inserted Successfully");
            }


            auditStatement.setString(1, user.getName());
            auditStatement.setString(2, "INSERT");
            auditStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            auditStatement.execute();

            // Commit the transaction
            connection.commit();
            System.out.println("Transaction committed: User inserted and audit log recorded.");
            return true;
        } catch (SQLException ex) {
            connection.rollback();
            System.out.println("Error occurred, transaction rolled back.");
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws SQLException {
        String dbUrl = "localhost:3306";
        String dbName = "bootcamp_db";
        String username = "root";
        String password = "rootpassword";

        DBCrud dbCrud = new DBCrud(dbUrl, dbName, username, password);

        User user1 = User.builder()
                .name("user1")
                .email("user1@gmail.com")
                .age(32)
                .country("BD")
                .build();
        dbCrud.insertUserByStatement(user1);

        User user2 = User.builder()
                .name("user2")
                .email("user2@gmail.com")
                .age(32)
                .country("BD")
                .build();
        long id2 = dbCrud.insertUserByStatementAndReturnGeneratedId(user2);
        System.out.println("id = " + id2);

        User user3 = User.builder()
                .name("user3")
                .email("user3@gmail.com")
                .age(32)
                .country("BD")
                .build();
        dbCrud.insertUserByPrepareStatement(user3);

        User user4 = User.builder()
                .name("user4")
                .email("user4@gmail.com")
                .age(32)
                .country("BD")
                .build();
        int id4 = dbCrud.insertUserByPrepareStatementAndReturnGeneratedId(user4);
        System.out.println("id = " + id4);

        user4 = user4.toBuilder()
                .id(id4)
                .name("user4Updated")
                .build();
        boolean success =dbCrud.updateUserByPrepareStatement(user4);
        System.out.println("success = " + success);

        user4 = dbCrud.getUser(id4);
        System.out.println("Updated user4 = " + user4);

        success = dbCrud.deleteUserByPrepareStatement(id4);
        System.out.println("Delete success = " + success);

        List<User> users = dbCrud.getAllUsers();
        System.out.println("users = " + users);


        User user5 = User.builder()
                .name("hello")
                .email("user5@gmail.com")
                .age(32)
                .country("BD")
                .build();
        success = dbCrud.transactionalInserts(user5);
        System.out.println("Transaction Status = " + success);

        User user6 = User.builder()
                .name("helloajs")
                .email("user6@gmail.com")
                .age(36)
                .country("US")
                .build();
        dbCrud.transactionalInserts(user6);
    }
}

/*

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100),
    age INT,
    country VARCHAR(50)
);

CREATE TABLE audit (
    audit_id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(20),   --- if user name cross 20 lenght, trnsaction rollback example
    operation_type VARCHAR(50),
    timestamp TIMESTAMP
);


* */
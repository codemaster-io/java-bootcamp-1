package com.codemaster.io.repository;

import com.codemaster.io.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class UserRepository {
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = new RowMapper<>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .country(rs.getString("country"))
                    .build();
        }
    };

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addUser(User user) {
        final String sql = "INSERT INTO users (name, email, age, country) VALUES (?, ?, ?, ?)";
        int cnt = jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getAge(), user.getCountry());
        return cnt > 0;
    }

    public int addUserAndReturnId(User user) {
        final String sql = "INSERT INTO users (name, email, age, country) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setInt(3, user.getAge());
                preparedStatement.setString(4, user.getCountry());
                return preparedStatement;
            }
        };

        int cnt = jdbcTemplate.update(psc, keyHolder);
        if(cnt > 0) {
            int id = keyHolder.getKey().intValue();
            System.out.println("id = " + id);
            return id;
        }
        return -1;
    }

    public User getUser(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, id);
    }

    // Update
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, age = ?, country = ? WHERE id = ?";
        int cnt = jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getAge(), user.getCountry(), user.getId());
        return cnt > 0;
    }

    // Delete
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        int cnt = jdbcTemplate.update(sql, id);
        return cnt > 0;
    }

    // List all Users
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public boolean addAuditData(String username) {
        String sql = "INSERT INTO audit (user_name, operation_type, timestamp) VALUES (?, ?, ?)";
        int cnt = jdbcTemplate.update(sql, username, "INSERT", new Timestamp(System.currentTimeMillis()));
        return cnt > 0;
    }

}

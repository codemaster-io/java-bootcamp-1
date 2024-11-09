package com.codemaster.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = (rs, rowNum) -> User.builder()
            .id(rs.getInt("id"))
            .name(rs.getString("name"))
            .email(rs.getString("email"))
            .age(rs.getInt("age"))
            .country(rs.getString("country"))
            .build();

    public boolean insertUser(User user) {
        String sql = "insert into users (name, email, age, country) values(?, ?, ?, ?)";
        int cnt = jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getAge(), user.getCountry());
        return cnt > 0;
    }

    @Transactional
    public boolean insertUserAndAuditData(User user) {
        String sql = "insert into users (name, email, age, country) values(?, ?, ?, ?)";
        int cnt = jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getAge(), user.getCountry());
        if(cnt > 0) {
            System.out.println("User inserted");
        }
        sql = "insert into audit (user_name, operation_type, timestamp) values(?, ?, ?)";
        cnt = jdbcTemplate.update(sql, user.getName(), "INSERT", new Timestamp(System.currentTimeMillis()));
        return cnt > 0;
    }

    public int insertUserGetId(User user) {
        String sql = "insert into users (name, email, age, country) values(?, ?, ?, ?)";

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

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int cnt = jdbcTemplate.update(psc, keyHolder);
        if(cnt > 0) {
            return keyHolder.getKey().intValue();
        }
        return -1;
    }

    public User getUser(int id) {
        String sql = "select * from users where id = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, id);
    }

    public List<User> getUsers() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public boolean deleteUser(int id) {
        String sql = "delete from users where id = ?";
        int cnt = jdbcTemplate.update(sql, id);
        return cnt > 0;
    }
}

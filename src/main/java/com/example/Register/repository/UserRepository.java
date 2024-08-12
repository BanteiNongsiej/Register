package com.example.Register.repository;

import com.example.Register.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<User> findByEmail(String email) {
    	String sql = "SELECT * FROM users WHERE email = ?";
        //String sql = "SELECT * FROM users WHERE email = email";
        logger.info("Executing query: {} with email: {}", sql, email);
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, new UserRowMapper());
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.error("Error fetching user by email: " + e.getMessage(), e);
            return Optional.empty();
        }
    }



    public void saveUser(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        logger.info("Executing query: {} with user: {}", sql, user);
        try {
            jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword());
            logger.info("User saved: " + user);
        } catch (Exception e) {
            logger.error("Error saving user: " + e.getMessage(), e);
        }
    }


    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}


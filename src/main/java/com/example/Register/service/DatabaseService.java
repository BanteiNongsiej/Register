package com.example.Register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean doesUsersTableExist() {
        try {
        	System.out.println("::doesUsersTableExist::");
            // Adjust query based on your database type
            String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'users'";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
            boolean result = count != null && count > 0;
            System.out.println("::Result::" + result);

            return result;
        } catch (Exception e) {
            // Log the exception
            System.err.println("Error checking users table existence: " + e.getMessage());
            return false;
        }
    }
}

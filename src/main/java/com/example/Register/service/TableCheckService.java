package com.example.Register.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TableCheckService {

    private final JdbcTemplate jdbcTemplate;

    public TableCheckService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean doesTableExist() {
        String sql = "SELECT * FROM information_schema.tables WHERE table_name = 'users'";
        return !jdbcTemplate.queryForList(sql).isEmpty();
    }
}

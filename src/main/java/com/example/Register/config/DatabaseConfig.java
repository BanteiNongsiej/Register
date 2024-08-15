//package com.example.Register.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import jakarta.annotation.PostConstruct;
//
//@Configuration
//public class DatabaseConfig {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @PostConstruct
//    public void init() {
//        jdbcTemplate.execute("SET search_path TO studentmanagement");
//    }
//}

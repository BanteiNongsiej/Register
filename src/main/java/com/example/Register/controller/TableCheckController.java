package com.example.Register.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Register.service.TableCheckService;

@RestController
@RequestMapping("/api")
public class TableCheckController {

    private final TableCheckService tableCheckService;

    public TableCheckController(TableCheckService tableCheckService) {
        this.tableCheckService = tableCheckService;
    }

    @GetMapping("/check-users-table")
    public String checkUsersTable() {
        boolean exists = tableCheckService.doesTableExist();
        return exists ? "Table 'users' exists." : "Table 'users' does not exist.";
    }
}


package com.example.Register.controller;

import com.example.Register.helper.AuthenticationHelper;
import com.example.Register.model.User;
import com.example.Register.service.UserService;
import com.example.Register.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        try {
            if (AuthenticationHelper.authenticated()) {
                return "redirect:/createStudent";
            }

            // Check if the users table exists
            if (!databaseService.doesUsersTableExist()) {
                logger.error("Users table does not exist.");
                model.addAttribute("loginError", "Error: Users table does not exist.");
            }

            model.addAttribute("user", new User());
            return "RegisterLogin";
        } catch (Exception e) {
            logger.error("Error in login page: " + e.getMessage());
            model.addAttribute("loginError", "Unexpected error occurred. Please try again.");
            return "RegisterLogin";
        }
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, Model model) {
        try {
            logger.info("Registering new User: " + user);

            // Check if the users table exists
            if (!databaseService.doesUsersTableExist()) {
                logger.error("users table does not exist.");
                model.addAttribute("registrationError", "Error: users table does not exist.");
                return "RegisterLogin";
            }

            if (result.hasErrors()) {
                logger.warn("Form has errors: " + result.getAllErrors());
                return "RegisterLogin";
            }

            if (userService.findByEmail(user.getEmail()).isPresent()) {
                logger.warn("Email already in use: " + user.getEmail());
                model.addAttribute("emailError", "Email is already in use");
                return "RegisterLogin";
            }

            logger.info("TABLE EXISTS");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userService.registerUser(user);
            logger.info("User registered successfully: " + user.getEmail());

            return "redirect:/login";
        } catch (Exception e) {
            logger.error("Error registering user: " + e.getMessage());
            model.addAttribute("registrationError", "Error registering user. Please try again.");
            return "RegisterLogin";
        }
    }
}

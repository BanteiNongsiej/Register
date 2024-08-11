package com.example.Register.controller;

import com.example.Register.helper.AuthenticationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.Register.model.User;
import com.example.Register.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import java.util.Optional;

@Controller
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(Model model) {
        if (AuthenticationHelper.authenticated()) {
            return "redirect:/createStudent";
        }
        logger.info("Accessing login page");
        model.addAttribute("user", new User());
        return "RegisterLogin";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, Model model) {
        logger.info("Attempting to register user with email: {}", user.getEmail());

        if (result.hasErrors()) {
            logger.warn("Registration form has errors");
            return "RegisterLogin";
        }

        // Check if the email already exists
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            logger.warn("Email already in use: {}", user.getEmail());
            model.addAttribute("emailError", "Email is already in use");
            return "RegisterLogin";
        }

        // Encode the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.registerUser(user);

        logger.info("User registered successfully: {}", user.getEmail());
        return "redirect:/createStudent";  // Redirect to the login page after successful registration
    }

}

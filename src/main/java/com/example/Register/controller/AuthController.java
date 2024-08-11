package com.example.Register.controller;

import com.example.Register.helper.AuthenticationHelper;
import com.example.Register.model.User;
import com.example.Register.service.UserService;
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

    @GetMapping("/login")
    public String loginPage(Model model) {
        if (AuthenticationHelper.authenticated()) {
            return "redirect:/createStudent";
        }
        model.addAttribute("user", new User());
        return "RegisterLogin";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, Model model) {
        System.out.println("Registering new User: " + user);
        if (result.hasErrors()) {
            System.out.println("Form has errors: " + result.getAllErrors());
            return "RegisterLogin";
        }

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            System.out.println("Email already in use: " + user.getEmail());
            model.addAttribute("emailError", "Email is already in use");
            return "RegisterLogin";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userService.registerUser(user);
            System.out.println("User registered successfully: " + user.getEmail());
        } catch (Exception e) {
            System.err.println("Error registering user: " + e.getMessage());
            model.addAttribute("registrationError", "Error registering user. Please try again.");
            return "RegisterLogin";
        }

        return "redirect:/login";
    }


}

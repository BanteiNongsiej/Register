package com.example.Register.controller;

import com.example.Register.model.User;
import com.example.Register.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/registerPage")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "registerPage";
    }

    @PostMapping("/registerPage")
    public String registerUser(User user, Model model) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already exists.");
            return "registerPage";
        }
        userService.registerUser(user);
        return "redirect:/loginPage";
    }

    @GetMapping("/loginPage")
    public String showLoginForm() {
        return "loginPage";
    }

    @GetMapping("/afterLogin/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        return "afterLogin/dashboard";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/loginPage";
    }
}

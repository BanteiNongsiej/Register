package com.example.Register.service;

import com.example.Register.model.User;
import com.example.Register.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void registerUser(User user) {
    	System.out.println("Registering user: " + user);
        userRepository.saveUser(user);
    }
    
}

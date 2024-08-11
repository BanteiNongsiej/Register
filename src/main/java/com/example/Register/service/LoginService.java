package com.example.Register.service;

import java.util.Optional;

import com.example.Register.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.Register.model.User;
import com.example.Register.mapper.LoginDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

	private final UserRepository userLoginRepo;

	@Autowired
	public LoginService(UserRepository userLoginRepo) {
		this.userLoginRepo = userLoginRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Fetch the user by email
		Optional<User> userLogin = (userLoginRepo.findByEmail(username));

		System.out.println("username "+username+" userlogin "+userLogin);

		// Map the User to UserDetails and throw exception if not found
		return userLogin.map(LoginDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));
	}
}

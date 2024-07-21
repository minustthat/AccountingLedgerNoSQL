package org.minustthat.theledge.Controllers;

import org.minustthat.theledge.Models.AuthenticatedUser;
import org.minustthat.theledge.Repositories.AuthenticatedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AuthenticatedUserController {
    AuthenticatedUserRepository authenticatedUserRepository;
    PasswordEncoder passwordEncoder;
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    public AuthenticatedUserController(AuthenticatedUserRepository authenticatedUserRepository, PasswordEncoder passwordEncoder){
        this.authenticatedUserRepository = authenticatedUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody AuthenticatedUser user){
        try {
            if (authenticatedUserRepository.findByUsername(user.getUsername()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already Exists.");
        } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                AuthenticatedUser newUser = authenticatedUserRepository.save(user);
                return ResponseEntity.ok(HttpStatus.CREATED);
            }
        } catch(Exception e){
return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }



}

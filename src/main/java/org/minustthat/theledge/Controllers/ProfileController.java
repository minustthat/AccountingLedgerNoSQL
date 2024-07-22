package org.minustthat.theledge.Controllers;

import org.minustthat.theledge.Models.AuthenticatedUser;
import org.minustthat.theledge.Repositories.AuthenticatedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

import static org.yaml.snakeyaml.nodes.Tag.STR;

@RestController
@EnableMethodSecurity
public class ProfileController {

    AuthenticatedUserRepository authenticatedUserRepository;
    PasswordEncoder passwordEncoder;
    @Autowired
    public ProfileController(AuthenticatedUserRepository authenticatedUserRepository, PasswordEncoder passwordEncoder){
        this.authenticatedUserRepository = authenticatedUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public int getCurrentUser(Principal principal){
        String username = principal.getName();
        Optional<AuthenticatedUser> user = authenticatedUserRepository.findByUsername(username);
        AuthenticatedUser foundUser = user.get();
        return foundUser.getCustomerId();
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/changeUsername")
    public String changeUsername(Principal principal, String newName){
        String username = principal.getName();
        Optional<AuthenticatedUser> user = authenticatedUserRepository.findByUsername(username);
        AuthenticatedUser foundUser = user.get();
         foundUser.setUsername(newName);
         return "Username has been changed to" + newName;
        }
    }



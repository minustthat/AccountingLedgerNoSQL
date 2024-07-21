package org.minustthat.theledge.Configuration;

import org.minustthat.theledge.Models.AuthenticatedUser;
import org.minustthat.theledge.Repositories.AuthenticatedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthenticatedUserDetailService implements UserDetailsService {

    AuthenticatedUserRepository authenticatedUserRepository;
    @Autowired
    public AuthenticatedUserDetailService(AuthenticatedUserRepository authenticatedUserRepository){
        this.authenticatedUserRepository = authenticatedUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthenticatedUser> user = authenticatedUserRepository.findByUsername(username);

        if(!user.isPresent()){
            throw new UsernameNotFoundException(username);
        } else {
            return User.builder()
                    .username(user.get().getUsername())
                    .password(user.get().getPassword())
                    .disabled(user.get().isEnabled())
                    .build();
        }
    }
}

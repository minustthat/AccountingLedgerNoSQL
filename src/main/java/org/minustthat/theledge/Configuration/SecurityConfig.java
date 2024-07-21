package org.minustthat.theledge.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

@Bean
    SecurityFilterChain securityFilter(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable);
    http.authorizeHttpRequests((authorize) -> authorize
                    .anyRequest().authenticated());
        http.httpBasic(Customizer.withDefaults());
                http.formLogin(Customizer.withDefaults());

               return http.build();

    }
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails admin  = User.withUsername("admin")
//                .password("$2a$12$SXLhLScEXIz/FUBjExdrWO3lUKaL.i79NX527N2v2nu99m0HbERwa")
//                .roles("ADMIN", "USER")
//                .build();
//
//        UserDetails user1  = User.withUsername("testUser")
//                .password("$2a$12$SyxXJg6x1EH6IEhkDmZ5c.bHhXO/l.Dlwwe520nAw1BFSZWpxl.yS")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, admin);
//        /* Created two users, one admin and one regular,
//        configured csrf to accept all requests,
//        allowed any request to be authenticated,
//        allowed basic http authentication
//        , created stateless api session.
//        adm
//        */
//    }
@Bean
    public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}

}

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
@Bean
    public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}

}

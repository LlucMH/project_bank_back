package com.project_lluc.bank_back_lluc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        // ADMIN (password = "admin" for your AdminIntegrationTest)
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .roles("ADMIN")
                .build();

        // ACCOUNT HOLDER
        UserDetails alice = User.builder()
                .username("alice")
                .password(encoder.encode("alice123"))
                .roles("ACCOUNTHOLDER")
                .build();

        // THIRD PARTY (only for potential Basic‑Auth of other endpoints;
        // your header logic sits in the controller)
        UserDetails thirdp = User.builder()
                .username("thirdp")
                .password(encoder.encode("tp123"))
                .roles("THIRD_PARTY")
                .build();

        return new InMemoryUserDetailsManager(admin, alice, thirdp);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        // Admin API: Basic‐Auth ADMIN
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // AccountHolder API: Basic‐Auth ACCOUNTHOLDER
                        .requestMatchers("/account-holder/**").hasRole("ACCOUNTHOLDER")

                        // Third‐Party API: open to everyone—your controller will read the
                        // "hashed-key" header and return 400/200 as needed
                        .requestMatchers(HttpMethod.POST, "/third-party/**").permitAll()

                        // User creation is public
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                        // Everything else needs at least authentication
                        .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }
}

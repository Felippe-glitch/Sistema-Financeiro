package com.example.appfinance.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // libera PUT/POST/DELETE sem precisar de CSRF token
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/usuario/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuario/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/usuario/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/usuario/**").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }
}

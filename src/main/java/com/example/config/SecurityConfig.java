package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**").permitAll()
                        .anyRequest().permitAll() // Permitting all for now as requested for the home page building
                                                  // phase
                )
                .formLogin((form) -> form.permitAll())
                .logout((logout) -> logout.permitAll());

        return http.build();
    }
}

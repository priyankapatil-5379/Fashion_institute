package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) 
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/login", "/register", "/forgot-password", "/css/**", "/images/**", "/js/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/vendor/**").hasRole("VENDOR")
                        .requestMatchers("/user/**").hasAnyRole("STUDENT", "ADMIN")
                        .anyRequest().authenticated() 
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            new HttpSessionRequestCache().removeRequest(request, response);
                            String redirectUrl = "/";
                            var authorities = authentication.getAuthorities();
                            for (var authority : authorities) {
                                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                                    redirectUrl = "/admin/dashboard";
                                    break;
                                } else if (authority.getAuthority().equals("ROLE_VENDOR")) {
                                    redirectUrl = "/vendor/dashboard";
                                    break;
                                }
                            }
                            response.sendRedirect(redirectUrl);
                        })
                        .permitAll())
                .logout((logout) -> logout
                        .logoutSuccessUrl("/")
                        .permitAll());

        return http.build();
    }
}

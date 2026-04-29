package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
<<<<<<< HEAD
                        .requestMatchers("/", "/login", "/register", "/forgot-password", "/css/**", "/images/**", "/js/**", "/vendor/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("STUDENT", "ADMIN")
                        .anyRequest().authenticated()
=======
                        .requestMatchers("/", "/login", "/register", "/forgot-password", "/css/**", "/images/**", "/js/**", "/user/course/**", "/user/payment/**").permitAll()
                        .requestMatchers("/vendor/**").hasRole("VENDOR")
                        .requestMatchers("/user/**").hasRole("STUDENT")
                        .anyRequest().authenticated() 
>>>>>>> 4cc25f987f84f23dba798e5830c6615b40ab091d
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            new HttpSessionRequestCache().removeRequest(request, response);
                            String redirectUrl = "/";
                            var authorities = authentication.getAuthorities();
                            for (var authority : authorities) {
                                if (authority.getAuthority().equals("ROLE_VENDOR")) {
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

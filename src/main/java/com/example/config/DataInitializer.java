package com.example.config;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create Admin user if not exists
        if (userRepository.findByUsername("admin@fashion.com").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin@fashion.com");
            admin.setEmail("admin@fashion.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            admin.setGender("Other");
            admin.setPhone("0000000000");
            userRepository.save(admin);
            System.out.println("Admin account created: admin@fashion.com / admin123");
        }
    }
}

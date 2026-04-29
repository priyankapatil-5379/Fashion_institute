package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole("STUDENT"); // Default role
        }
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public long countStudents() {
        return userRepository.countByRole("STUDENT");
    }

    public long countVendors() {
        return userRepository.countByRole("VENDOR");
    }

    public java.util.List<User> getAllStudents() {
        return userRepository.findAllByRole("STUDENT");
    }

    public java.util.List<User> getAllVendors() {
        return userRepository.findAllByRole("VENDOR");
    }
}

package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private com.example.service.UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@org.springframework.web.bind.annotation.RequestParam String username,
                                @org.springframework.web.bind.annotation.RequestParam String email,
                                @org.springframework.web.bind.annotation.RequestParam String password,
                                @org.springframework.web.bind.annotation.RequestParam String dob,
                                @org.springframework.web.bind.annotation.RequestParam String gender,
                                @org.springframework.web.bind.annotation.RequestParam String phone,
                                @org.springframework.web.bind.annotation.RequestParam String role) {
        
        com.example.model.User user = new com.example.model.User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setGender(gender);
        user.setDob(java.time.LocalDate.parse(dob));
        user.setRole(role);

        userService.registerUser(user);
        
        return "redirect:/?registrationSuccess=true";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }
}

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

    @GetMapping("/student-login")
    public String studentLogin() {
        return "student-login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@org.springframework.web.bind.annotation.RequestParam String fullName,
                                 @org.springframework.web.bind.annotation.RequestParam String username,
                                 @org.springframework.web.bind.annotation.RequestParam String email,
                                 @org.springframework.web.bind.annotation.RequestParam String password,
                                 @org.springframework.web.bind.annotation.RequestParam String confirmPassword,
                                 @org.springframework.web.bind.annotation.RequestParam String dob,
                                 @org.springframework.web.bind.annotation.RequestParam String gender,
                                 @org.springframework.web.bind.annotation.RequestParam String phone,
                                 @org.springframework.web.bind.annotation.RequestParam String role,
                                 org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        
        StringBuilder errorMessage = new StringBuilder();

        // 1. Full Name Validation
        if (fullName == null || fullName.trim().length() < 3 || !fullName.matches("^[a-zA-Z\\s]+$")) {
            errorMessage.append("Full Name must be at least 3 characters and contain only letters. ");
        }

        // 2. Username Validation
        if (username == null || username.contains(" ")) {
            errorMessage.append("Username cannot contain spaces. ");
        } else if (userService.findByUsername(username) != null) {
            errorMessage.append("Username already exists. ");
        }

        // 3. Email Validation
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errorMessage.append("Invalid email format. ");
        }

        // 4. Password Validation
        if (password == null || password.length() < 6 || !password.matches("^(?=.*[A-Za-z])(?=.*\\d).{6,}$")) {
            errorMessage.append("Password must be at least 6 characters and include both letters and numbers. ");
        }

        // 5. Confirm Password Validation
        if (!password.equals(confirmPassword)) {
            errorMessage.append("Passwords do not match. ");
        }

        // 6. DOB Validation
        java.time.LocalDate dobDate = null;
        try {
            dobDate = java.time.LocalDate.parse(dob);
            if (dobDate.isAfter(java.time.LocalDate.now())) {
                errorMessage.append("Date of Birth must be in the past. ");
            }
        } catch (Exception e) {
            errorMessage.append("Invalid Date of Birth. ");
        }

        // 7. Phone Number Validation
        if (phone == null || !phone.matches("^\\d{10}$")) {
            errorMessage.append("Phone Number must be exactly 10 digits. ");
        }

        // 8. Gender & Role
        if (gender == null || gender.trim().isEmpty() || role == null || role.trim().isEmpty()) {
            errorMessage.append("Gender and Role are required. ");
        }

        if (errorMessage.length() > 0) {
            redirectAttributes.addFlashAttribute("errorMsg", errorMessage.toString());
            return "redirect:/register?error=true";
        }
        
        com.example.model.User user = new com.example.model.User();
        user.setFullName(fullName);
        user.setUsername(username);
        user.setEmail(email.trim().toLowerCase());
        user.setPassword(password);
        user.setPhone(phone);
        user.setGender(gender);
        user.setDob(dobDate);
        user.setRole(role);

        userService.registerUser(user);
        
        return "redirect:/?registrationSuccess=true";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }
}

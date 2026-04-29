package com.example.controller;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetRestController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/request-otp")
    public String requestOtp(@RequestParam String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String otp = String.format("%06d", new Random().nextInt(999999));
            user.setResetToken(otp);
            user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(10));
            userRepository.save(user);
            System.out.println("DEBUG: OTP for " + email + " is " + otp);
            return "OTP_SENT";
        }
        return "EMAIL_NOT_FOUND";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (otp.equals(user.getResetToken()) && LocalDateTime.now().isBefore(user.getResetTokenExpiry())) {
                return "OTP_VERIFIED";
            }
        }
        return "INVALID_OTP";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (otp.equals(user.getResetToken()) && LocalDateTime.now().isBefore(user.getResetTokenExpiry())) {
                user.setPassword(newPassword); // In real app, encode this!
                user.setResetToken(null);
                user.setResetTokenExpiry(null);
                userRepository.save(user);
                return "SUCCESS";
            }
        }
        return "FAILURE";
    }
}

package com.example.controller;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/request-otp")
    public String requestOtp(@RequestParam String email) {
        System.out.println("DEBUG: Requesting OTP for email: " + email);
        String trimmedEmail = email.trim();
        Optional<User> userOpt = userRepository.findByEmailIgnoreCase(trimmedEmail);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String otp = String.format("%06d", new Random().nextInt(999999));
            user.setResetToken(otp);
            user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(10));
            userRepository.save(user);
            
            try {
                sendOtpEmail(user.getEmail(), otp);
                System.out.println("DEBUG: OTP for " + trimmedEmail + " is " + otp);
                return "OTP_SENT";
            } catch (Exception e) {
                e.printStackTrace();
                return "EMAIL_ERROR";
            }
        }
        return "EMAIL_NOT_FOUND";
    }

    private void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset OTP - The Fashion Institute");
        message.setText("Dear User,\n\nYour OTP for password reset is: " + otp + 
                       "\n\nThis OTP is valid for 10 minutes.\n\nRegards,\nThe Fashion Institute");
        mailSender.send(message);
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp) {
        Optional<User> userOpt = userRepository.findByEmailIgnoreCase(email.trim());
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
        Optional<User> userOpt = userRepository.findByEmailIgnoreCase(email.trim());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (otp.equals(user.getResetToken()) && LocalDateTime.now().isBefore(user.getResetTokenExpiry())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetToken(null);
                user.setResetTokenExpiry(null);
                userRepository.save(user);
                return "SUCCESS";
            }
        }
        return "FAILURE";
    }
}

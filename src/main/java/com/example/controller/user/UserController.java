package com.example.controller.user;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.CourseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserRepository userRepository;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("courses", courseService.getAllCourses());
        if (principal != null) {
            Optional<User> user = userRepository.findByUsername(principal.getName());
            model.addAttribute("wishlist", user.map(User::getWishlistCourses).orElse(new ArrayList<>()));
        } else {
            model.addAttribute("wishlist", new ArrayList<>());
        }
        model.addAttribute("view", "dashboard");
        return "user/dashboard";
    }

    @GetMapping("/my-learning")
    public String myLearning(Model model, Principal principal) {
        if (principal != null) {
            Optional<User> user = userRepository.findByUsername(principal.getName());
            model.addAttribute("courses", user.map(User::getEnrolledCourses).orElse(new ArrayList<>()));
        }
        model.addAttribute("view", "learning");
        return "user/dashboard";
    }

    @GetMapping("/wishlist")
    public String wishlist(Model model, Principal principal) {
        if (principal != null) {
            Optional<User> user = userRepository.findByUsername(principal.getName());
            model.addAttribute("courses", user.map(User::getWishlistCourses).orElse(new ArrayList<>()));
        }
        model.addAttribute("view", "wishlist");
        return "user/dashboard";
    }

    @GetMapping("/messages")
    public String messages(Model model, Principal principal) {
        model.addAttribute("view", "messages");
        model.addAttribute("courses", new ArrayList<>()); // Fix for Thymeleaf null check
        model.addAttribute("vendorContact", "Fashion Guru");
        return "user/dashboard";
    }

    @PostMapping("/add-to-wishlist/{id}")
    @ResponseBody
    public String addToWishlist(@PathVariable Long id, Principal principal) {
        if (principal != null) {
            User user = userRepository.findByUsername(principal.getName()).orElse(null);
            if (user != null) {
                user.getWishlistCourses().add(courseService.getCourseById(id));
                userRepository.save(user);
                return "Added to Wishlist";
            }
        }
        return "Error";
    }

    @PostMapping("/remove-from-wishlist/{id}")
    @ResponseBody
    public String removeFromWishlist(@PathVariable Long id, Principal principal) {
        if (principal != null) {
            User user = userRepository.findByUsername(principal.getName()).orElse(null);
            if (user != null) {
                user.getWishlistCourses().removeIf(c -> c.getId().equals(id));
                userRepository.save(user);
                return "Removed from Wishlist";
            }
        }
        return "Error";
    }

    @GetMapping("/course/{id}")
    public String courseDetails(@PathVariable Long id, Model model) {
        model.addAttribute("course", courseService.getCourseById(id));
        return "course-details";
    }

    @GetMapping("/payment/{id}")
    public String payment(@PathVariable Long id, Model model) {
        model.addAttribute("course", courseService.getCourseById(id));
        model.addAttribute("razorpayKeyId", razorpayKeyId);
        return "payment";
    }

    @GetMapping("/enroll-success/{id}")
    public String enrollSuccess(@PathVariable Long id, Model model, Principal principal) {
        if (principal != null) {
            User user = userRepository.findByUsername(principal.getName()).orElse(null);
            if (user != null) {
                user.getEnrolledCourses().add(courseService.getCourseById(id));
                userRepository.save(user);
            }
        }
        model.addAttribute("course", courseService.getCourseById(id));
        return "enroll-success";
    }
}
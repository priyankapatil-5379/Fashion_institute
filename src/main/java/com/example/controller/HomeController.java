package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.example.service.CourseService;

@Controller
public class HomeController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private com.example.service.GalleryService galleryService;

    @Autowired
    private com.example.service.ReviewService reviewService;

    @GetMapping("/")
    public String home(Model model) {
        java.util.List<com.example.model.Course> allCourses = courseService.getAllCourses();
        model.addAttribute("courses", allCourses);
        java.util.List<com.example.model.GalleryImage> images = galleryService.getAllImages();
        java.util.Collections.shuffle(images);
        model.addAttribute("images", images);
        model.addAttribute("reviews", reviewService.getAllReviews());
        model.addAttribute("newReview", new com.example.model.Review());
        return "index";
    }

    @org.springframework.web.bind.annotation.PostMapping("/post-review")
    public String postReview(@org.springframework.web.bind.annotation.ModelAttribute com.example.model.Review review) {
        reviewService.saveReview(review);
        return "redirect:/#testimonials";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/gallery")
    public String gallery(Model model) {
        java.util.List<com.example.model.GalleryImage> images = galleryService.getAllImages();
        java.util.Collections.shuffle(images);
        model.addAttribute("images", images);
        return "gallery";
    }

    @GetMapping("/scholarship")
    public String scholarship() {
        return "scholarship";
    }

    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }

    @GetMapping("/faculty")
    public String faculty() {
        return "faculty";
    }
}

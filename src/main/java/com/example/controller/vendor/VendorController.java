package com.example.controller.vendor;

import com.example.model.Course;
import com.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Mocking instructor name as "Fashion Guru" for now
        model.addAttribute("courses", courseService.getCoursesByInstructor("Fashion Guru"));
        model.addAttribute("newCourse", new Course());
        return "vendor/dashboard";
    }

    @PostMapping("/add-course")
    public String addCourse(@ModelAttribute Course course) {
        course.setInstructorName("Fashion Guru"); // Mocking
        courseService.saveCourse(course);
        return "redirect:/vendor/dashboard";
    }
}

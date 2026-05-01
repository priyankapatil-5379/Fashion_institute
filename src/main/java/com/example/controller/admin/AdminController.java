package com.example.controller.admin;

import com.example.service.CourseService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;



    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        long totalStudentsCount = userService.countStudents();
        long totalCollegesCount = userService.countVendors();
        long activeCoursesCount = courseService.getAllCourses().size();

        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("totalCourses", activeCoursesCount);

        // Statistics from DB
        model.addAttribute("totalStudents", totalStudentsCount);
        model.addAttribute("totalColleges", totalCollegesCount);
        model.addAttribute("activeCourses", activeCoursesCount);
        
        // Initializing other stats to 0 (for features not yet implemented in DB)
        model.addAttribute("totalRevenue", "₹0");



        return "admin/dashboard";
    }

    @GetMapping("/students")
    public String students(Model model) {
        model.addAttribute("students", userService.getAllStudents());
        return "admin/students";
    }

    @GetMapping("/colleges")
    public String colleges(Model model) {
        model.addAttribute("colleges", userService.getAllVendors());
        return "admin/colleges";
    }

    @GetMapping("/courses")
    public String courses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "admin/courses";
    }



    @GetMapping("/events")
    public String events(Model model) {
        model.addAttribute("events", java.util.Collections.emptyList());
        return "admin/events";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        return "admin/settings";
    }
}

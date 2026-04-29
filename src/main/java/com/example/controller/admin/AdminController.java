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
        long activeCoursesCount = courseService.getAllCourses().size();

        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("totalCourses", activeCoursesCount);

        // Dynamic Statistics from DB
        model.addAttribute("totalStudents", totalStudentsCount);
        model.addAttribute("activeCourses", activeCoursesCount);
        
        // Mock Statistics (for features not yet implemented in DB)
        model.addAttribute("pendingApplications", 85);
        model.addAttribute("totalRevenue", "$45,200");

        // Mock Recent Activity
        List<Map<String, String>> activities = Arrays.asList(
            Map.of("user", "Elena Gilbert", "action", "Enrolled in Fashion Design 101", "time", "2 mins ago"),
            Map.of("user", "Stefan Salvatore", "action", "Submitted Portfolio", "time", "15 mins ago"),
            Map.of("user", "Bonnie Bennett", "action", "Paid Tuition Fee", "time", "1 hour ago"),
            Map.of("user", "Damon Salvatore", "action", "Updated Profile", "time", "3 hours ago")
        );
        model.addAttribute("recentActivities", activities);

        return "admin/dashboard";
    }

    @GetMapping("/students")
    public String students(Model model) {
        List<Map<String, String>> students = Arrays.asList(
            Map.of("id", "STU001", "name", "Elena Gilbert", "email", "elena@fashion.com", "course", "Fashion Design", "status", "Active"),
            Map.of("id", "STU002", "name", "Stefan Salvatore", "email", "stefan@fashion.com", "course", "Textile Tech", "status", "Active"),
            Map.of("id", "STU003", "name", "Bonnie Bennett", "email", "bonnie@fashion.com", "course", "Fashion Design", "status", "Inactive"),
            Map.of("id", "STU004", "name", "Damon Salvatore", "email", "damon@fashion.com", "course", "Luxury Brand Mgmt", "status", "Active"),
            Map.of("id", "STU005", "name", "Caroline Forbes", "email", "caroline@fashion.com", "course", "Fashion Styling", "status", "Active")
        );
        model.addAttribute("students", students);
        return "admin/students";
    }

    @GetMapping("/courses")
    public String courses(Model model) {
        List<Map<String, String>> courses = Arrays.asList(
            Map.of("id", "CRS001", "name", "Fashion Design 101", "duration", "12 Months", "enrolled", "120", "status", "Active"),
            Map.of("id", "CRS002", "name", "Textile Technology", "duration", "6 Months", "enrolled", "45", "status", "Active"),
            Map.of("id", "CRS003", "name", "Luxury Brand Management", "duration", "18 Months", "enrolled", "30", "status", "Active"),
            Map.of("id", "CRS004", "name", "Fashion Illustration", "duration", "3 Months", "enrolled", "85", "status", "Active")
        );
        model.addAttribute("courses", courses);
        return "admin/courses";
    }

    @GetMapping("/applications")
    public String applications(Model model) {
        List<Map<String, String>> applications = Arrays.asList(
            Map.of("id", "APP001", "applicant", "Alice Smith", "course", "Fashion Design", "date", "2024-04-25", "status", "Pending"),
            Map.of("id", "APP002", "applicant", "Bob Johnson", "course", "Textile Tech", "date", "2024-04-26", "status", "Reviewed"),
            Map.of("id", "APP003", "applicant", "Charlie Brown", "course", "Luxury Brand Mgmt", "date", "2024-04-27", "status", "Pending")
        );
        model.addAttribute("applications", applications);
        return "admin/applications";
    }

    @GetMapping("/events")
    public String events(Model model) {
        List<Map<String, String>> events = Arrays.asList(
            Map.of("id", "EVT001", "title", "Spring Fashion Show", "date", "2024-05-15", "location", "Main Hall", "status", "Upcoming"),
            Map.of("id", "EVT002", "title", "Textile Workshop", "date", "2024-05-20", "location", "Lab A", "status", "Upcoming"),
            Map.of("id", "EVT003", "title", "Designer Meetup", "date", "2024-06-05", "location", "Conference Room", "status", "Scheduled")
        );
        model.addAttribute("events", events);
        return "admin/events";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        return "admin/settings";
    }
}

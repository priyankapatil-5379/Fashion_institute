package com.example.controller.vendor;

import com.example.model.Course;
import com.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private com.example.repository.UserRepository userRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping("/dashboard")
    public String dashboard(Model model, java.security.Principal principal) {
        String instructor = principal.getName();
        java.util.List<Course> instructorCourses = courseService.getCoursesByInstructor(instructor);
        java.util.List<com.example.model.User> instructorStudents = userRepository.findStudentsByInstructor(instructor);

        // Calculate Real-time Stats
        long totalCourses = instructorCourses.size();
        long activeStudents = instructorStudents.size();
        
        double avgRating = instructorCourses.stream()
                .mapToDouble(c -> c.getInitialRating() != null ? c.getInitialRating() : 0.0)
                .average()
                .orElse(0.0);
        
        double totalEarnings = instructorCourses.stream()
                .mapToDouble(c -> {
                    long students = userRepository.countStudentsByCourseId(c.getId());
                    return students * (c.getPrice() != null ? c.getPrice() : 0.0);
                })
                .sum();

        model.addAttribute("courses", instructorCourses.stream().limit(4).collect(java.util.stream.Collectors.toList()));
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("activeStudents", activeStudents);
        model.addAttribute("avgRating", String.format("%.1f", avgRating));
        model.addAttribute("totalEarnings", String.format("₹%,.0f", totalEarnings));
        model.addAttribute("newCourse", new Course());
        return "vendor/dashboard";
    }

    @PostMapping("/add-course")
    public String addCourse(@ModelAttribute Course course, @RequestParam("thumbnailFile") MultipartFile file, java.security.Principal principal) {
        course.setInstructorName(principal.getName());
        
        if (file != null && !file.isEmpty()) {
            try {
                java.io.File uploadDir = new java.io.File(UPLOAD_DIR);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(file.getInputStream(), path);
                course.setImageUrl("/uploads/" + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        courseService.saveCourse(course);
        return "redirect:/vendor/dashboard";
    }

    @GetMapping("/courses")
    public String myCourses(Model model, java.security.Principal principal) {
        String instructor = principal.getName();
        model.addAttribute("courses", courseService.getCoursesByInstructor(instructor));
        model.addAttribute("newCourse", new Course());
        return "vendor/courses";
    }

    @GetMapping("/students")
    public String students(Model model, java.security.Principal principal) {
        model.addAttribute("students", userRepository.findStudentsByInstructor(principal.getName()));
        return "vendor/students";
    }

    @GetMapping("/analytics")
    public String analytics(Model model, java.security.Principal principal) {
        String instructor = principal.getName();
        java.util.List<Course> instructorCourses = courseService.getCoursesByInstructor(instructor);
        model.addAttribute("courses", instructorCourses);
        model.addAttribute("totalCourses", instructorCourses.size());
        model.addAttribute("newCourse", new Course());
        return "vendor/dashboard";
    }

    @GetMapping("/messages")
    public String messages(Model model) {
        return "vendor/messages";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        return "vendor/settings";
    }

    @GetMapping("/delete-course/{id}")
    public String deleteCourse(@org.springframework.web.bind.annotation.PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/vendor/courses";
    }

    @GetMapping("/edit-course/{id}")
    public String editCourse(@org.springframework.web.bind.annotation.PathVariable Long id, Model model, java.security.Principal principal) {
        model.addAttribute("courses", courseService.getCoursesByInstructor(principal.getName()));
        model.addAttribute("newCourse", courseService.getCourseById(id));
        return "vendor/courses";
    }
}

package com.example.config;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private com.example.repository.CourseRepository courseRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize Default Admin if not exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@fashioninstitute.edu");
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("Default Admin created: admin/admin");
        }

        // Initialize Default Courses from Home Page
        java.util.List<com.example.model.Course> existingCourses = courseRepository.findAll();
        java.util.Set<String> existingTitles = existingCourses.stream().map(c -> c.getTitle()).collect(java.util.stream.Collectors.toSet());

        String[] defaultCourses = {
            "Fashion Designing",
            "Jewellery Designing",
            "Interior Designing",
            "Photography",
            "Shoe Designing",
            "Beauty and Grooming"
        };
        
        for (String title : defaultCourses) {
            if (!existingTitles.contains(title)) {
                com.example.model.Course course = new com.example.model.Course();
                course.setTitle(title);
                course.setCategory("Diploma Program"); // Default category
                course.setDescription("Comprehensive program in " + title);
                course.setPrice(15000.0);
                course.setDuration("1 Year");
                course.setInstructorName("System");
                courseRepository.save(course);
            }
        }
        System.out.println("Default Courses checked and initialized.");
    }
}

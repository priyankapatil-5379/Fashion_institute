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

    @Autowired
    private com.example.repository.AttendanceRepository attendanceRepository;
    
    @Autowired
    private com.example.repository.AssessmentRepository assessmentRepository;
    
    @Autowired
    private com.example.repository.TestResultRepository testResultRepository;
    
    @Autowired
    private com.example.repository.CertificationRepository certificationRepository;

    @Autowired
    private com.example.repository.InquiryRepository inquiryRepository;

    @Override
    @org.springframework.transaction.annotation.Transactional
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

        // ROLE MIGRATION: Ensure all "College" roles are converted to "VENDOR" for security compatibility
        userRepository.findAll().stream()
            .filter(u -> "College".equalsIgnoreCase(u.getRole()))
            .forEach(u -> {
                u.setRole("VENDOR");
                userRepository.save(u);
                System.out.println("Migrated user " + u.getUsername() + " from College to VENDOR role.");
            });

        // SYSTEM RESET: Force-clear all inquiries and students one last time to ensure synchronization
        System.out.println("Force-cleaning all stale data for system reset...");
        
        // 1. Clear all student-related records
        attendanceRepository.deleteAll();
        assessmentRepository.deleteAll();
        testResultRepository.deleteAll();
        certificationRepository.deleteAll();
        inquiryRepository.deleteAll();
        
        // 2. Clear all users except admin
        userRepository.findAll().stream()
            .filter(u -> !"ADMIN".equals(u.getRole()))
            .forEach(u -> {
                u.getEnrolledCourses().clear();
                u.getWishlistCourses().clear();
                userRepository.save(u);
                userRepository.delete(u);
            });
            
        // 3. Clear all courses (only if they are from the dummy set or unnamed)
        courseRepository.findAll().forEach(c -> {
            if (c.getInstructorName() == null || "The Fashion Institute".equals(c.getInstructorName()) || "System".equals(c.getInstructorName())) {
                courseRepository.delete(c);
            }
        });
        
        System.out.println("System Reset Complete.");
    }
}

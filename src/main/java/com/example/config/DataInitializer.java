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

        // SYSTEM RESET: Removed destructive data clearing to persist registered users.
        System.out.println("Checking system state...");

        // 4. Seed Official Programs (Synchronized with Menu)
        if (courseRepository.count() == 0) {
            System.out.println("Seeding official institute programs...");
            
            com.example.model.Course c1 = new com.example.model.Course();
            c1.setTitle("Advanced Fashion Designing");
            c1.setCategory("Fashion Designing");
            c1.setDuration("1 Year");
            c1.setPrice(150000.0);
            c1.setInstructorName("The Fashion Institute");
            c1.setImageUrl("/images/about_1.png");
            c1.setDescription("Master the art of professional fashion design and garment construction.");
            courseRepository.save(c1);

            com.example.model.Course c2 = new com.example.model.Course();
            c2.setTitle("Fine Jewellery Artistry");
            c2.setCategory("Jewellery Designing");
            c2.setDuration("6 Months");
            c2.setPrice(85000.0);
            c2.setInstructorName("The Fashion Institute");
            c2.setImageUrl("/images/about_2.png");
            c2.setDescription("Learn precision craftsmanship and working with precious metals and stones.");
            courseRepository.save(c2);

            com.example.model.Course c3 = new com.example.model.Course();
            c3.setTitle("Diploma in Fashion Designing");
            c3.setCategory("Diploma in Fashion Designing");
            c3.setDuration("2 Years");
            c3.setPrice(250000.0);
            c3.setInstructorName("The Fashion Institute");
            c3.setImageUrl("/images/about_1.png");
            c3.setDescription("A comprehensive diploma program covering all aspects of the fashion industry.");
            courseRepository.save(c3);

            com.example.model.Course c4 = new com.example.model.Course();
            c4.setTitle("Bachelor of Fashion Design");
            c4.setCategory("Bachelor of Fashion Design");
            c4.setDuration("3 Years");
            c4.setPrice(450000.0);
            c4.setInstructorName("The Fashion Institute");
            c4.setImageUrl("/images/about_1.png");
            c4.setDescription("Our flagship degree program for aspiring international fashion designers.");
            courseRepository.save(c4);
        }
        
        // Force update images for existing courses to fix broken images
        courseRepository.findAll().forEach(c -> {
            if (c.getTitle().contains("Fashion") && !"/images/about_1.png".equals(c.getImageUrl())) {
                c.setImageUrl("/images/about_1.png");
                courseRepository.save(c);
            } else if (c.getTitle().contains("Jewel") && !"/images/about_2.png".equals(c.getImageUrl())) {
                c.setImageUrl("/images/about_2.png");
                courseRepository.save(c);
            } else if (c.getImageUrl() == null) {
                c.setImageUrl("/images/f1.jpeg");
                courseRepository.save(c);
            }
        });
        
        System.out.println("System Reset and Seeding Complete.");
    }
}

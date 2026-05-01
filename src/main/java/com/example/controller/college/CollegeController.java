
package com.example.controller.college;
 
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
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.ModelAttribute;
 
@Controller
@RequestMapping("/college")
public class CollegeController {
 
    @Autowired
    private CourseService courseService;
 
    @Autowired
    private com.example.repository.UserRepository userRepository;

    @Autowired
    private com.example.repository.InquiryRepository inquiryRepository;
 
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
 
    @GetMapping("/dashboard")
    public String dashboard(Model model, java.security.Principal principal) {
        String instructor = principal.getName();
        List<Course> instructorCourses = courseService.getCoursesByInstructor(instructor);
        List<com.example.model.User> instructorStudents = userRepository.findStudentsByInstructor(instructor);
 
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

        // Show all inquiries to college as requested
        List<com.example.model.Inquiry> inquiries = inquiryRepository.findAllByOrderByCreatedAtDesc();

        model.addAttribute("courses", instructorCourses.stream().limit(4).collect(Collectors.toList()));
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("activeStudents", activeStudents);
        model.addAttribute("avgRating", String.format("%.1f", avgRating));
        model.addAttribute("totalEarnings", String.format("₹%,.0f", totalEarnings));
        model.addAttribute("newCourse", new Course());
        
        // Add inquiries to dashboard
        model.addAttribute("recentActivities", inquiries.stream().limit(5).collect(Collectors.toList()));
        model.addAttribute("pendingApplications", inquiryRepository.countByIsReadFalse());

        return "college/dashboard";
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
        return "redirect:/college/dashboard";
    }
 
    @GetMapping("/courses")
    public String myCourses(Model model, java.security.Principal principal) {
        String instructor = principal.getName();
        model.addAttribute("courses", courseService.getCoursesByInstructor(instructor));
        model.addAttribute("newCourse", new Course());
        return "college/courses";
    }
 
    @GetMapping("/students")
    public String students(Model model, java.security.Principal principal) {
        // Fetching all students so manually added students without courses also appear
        model.addAttribute("students", userService.getAllStudents());
        model.addAttribute("courses", courseService.getCoursesByInstructor(principal.getName()));
        return "college/students";
    }

    @PostMapping("/enroll-student")
    public String enrollStudent(@RequestParam Long studentId, @RequestParam Long courseId) {
        com.example.model.User student = userRepository.findById(studentId).orElse(null);
        com.example.model.Course course = courseService.getCourseById(courseId);
        
        if (student != null && course != null) {
            if (!student.getEnrolledCourses().contains(course)) {
                student.getEnrolledCourses().add(course);
                userRepository.save(student);
            }
        }
        return "redirect:/college/students";
    }

    @PostMapping("/add-student")
    public String addStudent(@RequestParam String name,
                             @RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String status) {
        
        com.example.model.User user = userRepository.findByEmail(email).orElse(new com.example.model.User());
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }
        user.setStatus(status);
        user.setRole("STUDENT");
        
        userService.registerUser(user);
        
        return "redirect:/college/students";
    }

    @GetMapping("/applications")
    public String applications(Model model) {
        model.addAttribute("applications", inquiryRepository.findAllByOrderByCreatedAtDesc());
        return "college/applications";
    }

    @GetMapping("/student-register")
    public String studentRegister(@RequestParam(required = false) String name, 
                                @RequestParam(required = false) String email, 
                                Model model) {
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        return "college/student-register";
    }

    @PostMapping("/process-registration")
    public String processRegistration(@RequestParam String name,
                                    @RequestParam String email,
                                    @RequestParam String username,
                                    @RequestParam String password,
                                    org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        
        com.example.model.User user = userRepository.findByEmail(email).orElse(new com.example.model.User());
        user.setName(name);
        user.setEmail(email);
        
        java.util.Optional<com.example.model.User> existingUsername = userRepository.findByUsername(username);
        if (existingUsername.isPresent() && (user.getId() == null || !existingUsername.get().getId().equals(user.getId()))) {
            redirectAttributes.addFlashAttribute("regError", "The username '" + username + "' is already taken. Please choose another.");
            return "redirect:/college/applications";
        }

        user.setUsername(username);
        user.setPassword(password);
        user.setRole("STUDENT");
        
        userService.registerUser(user);
        
        // Find the inquiry and update status to Registered if it exists
        java.util.Optional<com.example.model.Inquiry> inq = inquiryRepository.findAll().stream()
            .filter(i -> i.getEmail().equalsIgnoreCase(email))
            .findFirst();
        if (inq.isPresent()) {
            com.example.model.Inquiry inquiry = inq.get();
            inquiry.setStatus("Registered");
            inquiry.setRead(true);
            inquiry.setGeneratedUsername(username);
            inquiry.setGeneratedPassword(password);
            inquiryRepository.save(inquiry);
        }
        
        return "redirect:/college/applications";
    }

    @Autowired
    private com.example.service.UserService userService;
 
    @GetMapping("/analytics")
    public String analytics(Model model, java.security.Principal principal) {
        String instructor = principal.getName();
        List<Course> instructorCourses = courseService.getCoursesByInstructor(instructor);
        model.addAttribute("courses", instructorCourses);
        model.addAttribute("totalCourses", instructorCourses.size());
        model.addAttribute("newCourse", new Course());
        return "college/dashboard";
    }
 
    @GetMapping("/messages")
    public String messages(Model model) {
        return "college/messages";
    }
 
    @GetMapping("/settings")
    public String settings(Model model) {
        return "college/settings";
    }
 
    @GetMapping("/delete-course/{id}")
    public String deleteCourse(@org.springframework.web.bind.annotation.PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/college/courses";
    }
 
    @GetMapping("/edit-course/{id}")
    public String editCourse(@org.springframework.web.bind.annotation.PathVariable Long id, Model model, java.security.Principal principal) {
        model.addAttribute("courses", courseService.getCoursesByInstructor(principal.getName()));
        model.addAttribute("newCourse", courseService.getCourseById(id));
        return "college/courses";
    }
}

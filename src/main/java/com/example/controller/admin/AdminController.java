package com.example.controller.admin;

import com.example.service.CourseService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import com.example.model.Course;
import com.example.model.GalleryImage;
import com.example.service.GalleryService;
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

    @Autowired
    private com.example.repository.InquiryRepository inquiryRepository;

    @Autowired
    private GalleryService galleryService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        long totalStudentsCount = userService.countStudents();
        long totalCollegesCount = userService.countVendors();
        long activeCoursesCount = courseService.getAllCourses().size();
        
        Double totalRevenueVal = inquiryRepository.sumAllAmountPaid();
        String formattedRevenue = "₹" + (totalRevenueVal != null ? String.format("%.2f", totalRevenueVal) : "0.00");

        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("totalCourses", activeCoursesCount);

        // Statistics from DB
        model.addAttribute("totalStudents", totalStudentsCount);
        model.addAttribute("totalColleges", totalCollegesCount);
        model.addAttribute("activeCourses", activeCoursesCount);
        
        // Initializing other stats to 0 (for features not yet implemented in DB)
        model.addAttribute("totalRevenue", formattedRevenue);



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



    @GetMapping("/courses/new")
    public String createCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "admin/course-form";
    }

    @GetMapping("/courses/edit/{id}")
    public String editCourseForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            model.addAttribute("course", course);
            return "admin/course-form";
        }
        return "redirect:/admin/courses";
    }

    @PostMapping("/courses/save")
    public String saveCourse(Course course) {
        courseService.saveCourse(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/admin/courses";
    }

    @GetMapping("/gallery")
    public String gallery(Model model) {
        model.addAttribute("images", galleryService.getAllImages());
        model.addAttribute("newImage", new GalleryImage());
        return "admin/gallery";
    }

    @PostMapping("/gallery/save")
    public String saveGalleryImage(GalleryImage galleryImage, @RequestParam("file") MultipartFile file) throws java.io.IOException {
        galleryService.saveImage(galleryImage, file);
        return "redirect:/admin/gallery";
    }

    @GetMapping("/gallery/delete/{id}")
    public String deleteGalleryImage(@PathVariable Long id) throws java.io.IOException {
        galleryService.deleteImage(id);
        return "redirect:/admin/gallery";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        return "admin/settings";
    }
}

package com.example.service;

import com.example.model.Course;
import com.example.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private com.example.repository.UserRepository userRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getCoursesByInstructor(String instructorName) {
        return courseRepository.findByInstructorName(instructorName);
    }

    @org.springframework.transaction.annotation.Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null) {
            // Remove course from all users' enrolled and wishlist lists
            userRepository.findByEnrolledCoursesId(id).forEach(user -> {
                user.getEnrolledCourses().remove(course);
                userRepository.save(user);
            });
            userRepository.findByWishlistCoursesId(id).forEach(user -> {
                user.getWishlistCourses().remove(course);
                userRepository.save(user);
            });
            courseRepository.delete(course);
        }
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }
}

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

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getCoursesByInstructor(String instructorName) {
        return courseRepository.findByInstructorName(instructorName);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}

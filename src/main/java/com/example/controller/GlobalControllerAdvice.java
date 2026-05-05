package com.example.controller;

import com.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CourseService courseService;

    @ModelAttribute
    public void addAttributes(Model model) {
        // Provide courses for the dynamic navigation menu
        model.addAttribute("globalCourses", courseService.getAllCourses());
    }
}

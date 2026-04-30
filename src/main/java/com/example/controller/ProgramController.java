package com.example.controller;

import com.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProgramController {

    @Autowired
    private CourseService courseService;

    private static final Map<String, ProgramData> programRegistry = new HashMap<>();

    static {
        programRegistry.put("fashion-designing", new ProgramData(
            "Fashion Designing",
            "Master the art of couture and avant-garde design. From concept to catwalk, explore the world of high fashion.",
            "/images/f1.jpeg",
            "Our Fashion Design program focuses on creativity, technical skills, and industry knowledge. You will learn everything from sketching to garment construction."
        ));
        programRegistry.put("jewellery-designing", new ProgramData(
            "Jewellery Designing",
            "Design the next iconic gem. Learn precision craftsmanship, stone artistry, and luxury market trends.",
            "/images/f4.jpeg",
            "The Jewellery Design course covers gemology, metalwork, and CAD design for the modern luxury market."
        ));
        programRegistry.put("interior-designing", new ProgramData(
            "Interior Designing",
            "Transform spaces into experiences. Learn architectural basics, color theory, and luxury interior styling.",
            "https://images.unsplash.com/photo-1618221195710-dd6b41faaea6?auto=format&fit=crop&q=80&w=1200",
            "Our Interior Design program blends aesthetics with functionality, preparing you for residential and commercial design."
        ));
        programRegistry.put("photography", new ProgramData(
            "Photography",
            "Capture the world through a creative lens. Master studio lighting, editorial shoots, and post-production.",
            "https://images.unsplash.com/photo-1554048612-b6a482bc67e5?auto=format&fit=crop&q=80&w=1200",
            "Focus on fashion, commercial, and artistic photography. Learn from industry pros and build a professional portfolio."
        ));
        programRegistry.put("shoe-designing", new ProgramData(
            "Shoe Designing",
            "Craft luxury from the ground up. Learn footwear architecture, material science, and artisanal techniques.",
            "/images/f3.jpeg",
            "From high-heels to sneakers, master the unique art of footwear design and construction."
        ));
        programRegistry.put("beauty-and-grooming", new ProgramData(
            "Beauty and Grooming",
            "Redefine beauty with expert artistry. Master makeup, hair styling, and professional grooming.",
            "/images/f2.jpeg",
            "A comprehensive program covering editorial makeup, bridal grooming, and skin-care science."
        ));
    }

    @GetMapping("/programs/{page}")
    public String programDetails(@PathVariable String page, Model model) {
        model.addAttribute("relatedCourses", courseService.getAllCourses());
        return "programs/" + page;
    }

    public static class ProgramData {
        private String title;
        private String tagLine;
        private String heroImage;
        private String description;

        public ProgramData(String title, String tagLine, String heroImage, String description) {
            this.title = title;
            this.tagLine = tagLine;
            this.heroImage = heroImage;
            this.description = description;
        }

        public String getTitle() { return title; }
        public String getTagLine() { return tagLine; }
        public String getHeroImage() { return heroImage; }
        public String getDescription() { return description; }
    }
}

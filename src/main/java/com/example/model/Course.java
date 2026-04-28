package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private String programLevel;
    private String duration;
    private Double price;
    private String instructorName;
    private String eligibility;
    private Double initialRating;
    
    // Visual Identity
    private String imageUrl;
    private String videoLink;

    // Detailed Content - Using @Lob and LONGTEXT to ensure enough space
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String whyChoose;
    
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String courseOverview;
    
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String learningOutcomes;
    
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String careerOpportunities;
    
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String courseStructure;

    public Course() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getProgramLevel() { return programLevel; }
    public void setProgramLevel(String programLevel) { this.programLevel = programLevel; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }
    public String getEligibility() { return eligibility; }
    public void setEligibility(String eligibility) { this.eligibility = eligibility; }
    public Double getInitialRating() { return initialRating; }
    public void setInitialRating(Double initialRating) { this.initialRating = initialRating; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getVideoLink() { return videoLink; }
    public void setVideoLink(String videoLink) { this.videoLink = videoLink; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getWhyChoose() { return whyChoose; }
    public void setWhyChoose(String whyChoose) { this.whyChoose = whyChoose; }
    public String getCourseOverview() { return courseOverview; }
    public void setCourseOverview(String courseOverview) { this.courseOverview = courseOverview; }
    public String getLearningOutcomes() { return learningOutcomes; }
    public void setLearningOutcomes(String learningOutcomes) { this.learningOutcomes = learningOutcomes; }
    public String getCareerOpportunities() { return careerOpportunities; }
    public void setCareerOpportunities(String careerOpportunities) { this.careerOpportunities = careerOpportunities; }
    public String getCourseStructure() { return courseStructure; }
    public void setCourseStructure(String courseStructure) { this.courseStructure = courseStructure; }
}

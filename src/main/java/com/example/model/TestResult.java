package com.example.model;

import jakarta.persistence.*;

@Entity
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User student;

    @ManyToOne
    private Course course;

    private String testName;
    private Integer score;
    private Integer maxScore;
    private String status;

    public TestResult() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public String getTestName() { return testName; }
    public void setTestName(String testName) { this.testName = testName; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public Integer getMaxScore() { return maxScore; }
    public void setMaxScore(Integer maxScore) { this.maxScore = maxScore; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

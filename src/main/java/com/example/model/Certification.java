package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User student;

    @ManyToOne
    private Course course;

    private String certificateName;
    private LocalDate issueDate;

    public Certification() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public String getCertificateName() { return certificateName; }
    public void setCertificateName(String certificateName) { this.certificateName = certificateName; }
    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
}

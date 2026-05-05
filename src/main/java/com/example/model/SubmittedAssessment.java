package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SubmittedAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    private String status; // PENDING, GRADED
    private LocalDateTime submittedAt;

    @OneToMany(mappedBy = "submittedAssessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubmittedAnswer> answers = new ArrayList<>();

    public SubmittedAssessment() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    public Assessment getAssessment() { return assessment; }
    public void setAssessment(Assessment assessment) { this.assessment = assessment; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
    public List<SubmittedAnswer> getAnswers() { return answers; }
    public void setAnswers(List<SubmittedAnswer> answers) { this.answers = answers; }
}

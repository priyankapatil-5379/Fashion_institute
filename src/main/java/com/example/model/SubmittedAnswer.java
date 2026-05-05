package com.example.model;

import jakarta.persistence.*;

@Entity
public class SubmittedAnswer { // Renamed to SubmittedAnswer to match SubmittedAssessment.java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String selectedOption; // A, B, C, D

    @ManyToOne
    @JoinColumn(name = "submitted_assessment_id")
    private SubmittedAssessment submittedAssessment;

    public SubmittedAnswer() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }
    public String getSelectedOption() { return selectedOption; }
    public void setSelectedOption(String selectedOption) { this.selectedOption = selectedOption; }
    public SubmittedAssessment getSubmittedAssessment() { return submittedAssessment; }
    public void setSubmittedAssessment(SubmittedAssessment submittedAssessment) { this.submittedAssessment = submittedAssessment; }
}

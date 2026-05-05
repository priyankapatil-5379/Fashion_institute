package com.example.repository;

import com.example.model.SubmittedAssessment;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubmittedAssessmentRepository extends JpaRepository<SubmittedAssessment, Long> {
    List<SubmittedAssessment> findByStudent(User student);
    List<SubmittedAssessment> findByAssessment_Course_CollegeUser_Username(String username);
}

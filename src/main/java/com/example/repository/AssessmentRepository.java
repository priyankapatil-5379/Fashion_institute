package com.example.repository;

import com.example.model.Assessment;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByStudent(User student);
}

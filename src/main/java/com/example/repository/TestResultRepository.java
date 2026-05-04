package com.example.repository;

import com.example.model.TestResult;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findByStudent(User student);
}

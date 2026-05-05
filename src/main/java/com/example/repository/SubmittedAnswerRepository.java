package com.example.repository;

import com.example.model.SubmittedAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmittedAnswerRepository extends JpaRepository<SubmittedAnswer, Long> {
}

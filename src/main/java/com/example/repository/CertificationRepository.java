package com.example.repository;

import com.example.model.Certification;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    List<Certification> findByStudent(User student);
}

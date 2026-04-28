package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    
    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT u FROM User u JOIN u.enrolledCourses c WHERE c.instructorName = :instructorName")
    java.util.List<User> findStudentsByInstructor(String instructorName);

    @org.springframework.data.jpa.repository.Query("SELECT COUNT(u) FROM User u JOIN u.enrolledCourses c WHERE c.id = :courseId")
    long countStudentsByCourseId(Long courseId);
}

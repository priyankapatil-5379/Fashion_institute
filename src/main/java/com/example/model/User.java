package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;
    
    private LocalDate dob;
    
    private String gender;

    private String role; // STUDENT, ADMIN, INSTRUCTOR

    private String resetToken;
    private java.time.LocalDateTime resetTokenExpiry;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_enrolled_courses",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "course_id"))
    private java.util.List<Course> enrolledCourses = new java.util.ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_wishlist_courses",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "course_id"))
    private java.util.List<Course> wishlistCourses = new java.util.ArrayList<>();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public java.util.List<Course> getEnrolledCourses() { return enrolledCourses; }
    public void setEnrolledCourses(java.util.List<Course> enrolledCourses) { this.enrolledCourses = enrolledCourses; }
    public String getResetToken() { return resetToken; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }
    public java.time.LocalDateTime getResetTokenExpiry() { return resetTokenExpiry; }
    public void setResetTokenExpiry(java.time.LocalDateTime resetTokenExpiry) { this.resetTokenExpiry = resetTokenExpiry; }

    public java.util.List<Course> getWishlistCourses() { return wishlistCourses; }
    public void setWishlistCourses(java.util.List<Course> wishlistCourses) { this.wishlistCourses = wishlistCourses; }
}

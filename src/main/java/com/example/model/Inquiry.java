package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String courseOfInterest;
    private String phone;
    
    @Column(length = 2000)
    private String message;

    private LocalDateTime createdAt;
    
    private boolean isRead = false;
    private String status = "New";
    private String paymentMethod; // EMI or Full Payment
    private Double amountPaid;
    private Double dueAmount;
    
    private String generatedUsername;
    private String generatedPassword;

    public Inquiry() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getCourseOfInterest() { return courseOfInterest; }
    public void setCourseOfInterest(String courseOfInterest) { this.courseOfInterest = courseOfInterest; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(Double amountPaid) { this.amountPaid = amountPaid; }

    public Double getDueAmount() { return dueAmount; }
    public void setDueAmount(Double dueAmount) { this.dueAmount = dueAmount; }

    public String getGeneratedUsername() { return generatedUsername; }
    public void setGeneratedUsername(String generatedUsername) { this.generatedUsername = generatedUsername; }

    public String getGeneratedPassword() { return generatedPassword; }
    public void setGeneratedPassword(String generatedPassword) { this.generatedPassword = generatedPassword; }
}

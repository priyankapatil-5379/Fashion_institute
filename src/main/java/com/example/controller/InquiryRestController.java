package com.example.controller;

import com.example.model.Inquiry;
import com.example.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryRestController {

    @Autowired
    private InquiryRepository inquiryRepository;

    @PostMapping
    public ResponseEntity<String> submitInquiry(@RequestBody Inquiry inquiry) {
        try {
            inquiryRepository.save(inquiry);
            return ResponseEntity.ok("Inquiry submitted successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error submitting inquiry");
        }
    }
    @PostMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@org.springframework.web.bind.annotation.PathVariable Long id, @org.springframework.web.bind.annotation.RequestParam String status) {
        try {
            Inquiry inquiry = inquiryRepository.findById(id).orElseThrow();
            inquiry.setStatus(status);
            inquiry.setRead(true); // Automatically mark as read if status changes
            inquiryRepository.save(inquiry);
            return ResponseEntity.ok("Status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating status");
        }
    }
    @PostMapping("/{id}/payment")
    public ResponseEntity<String> updatePayment(@org.springframework.web.bind.annotation.PathVariable Long id, 
                                             @org.springframework.web.bind.annotation.RequestParam(required = false) String paymentMethod,
                                             @org.springframework.web.bind.annotation.RequestParam(required = false) Double amountPaid,
                                             @org.springframework.web.bind.annotation.RequestParam(required = false) Double dueAmount) {
        try {
            Inquiry inquiry = inquiryRepository.findById(id).orElseThrow();
            if (paymentMethod != null) inquiry.setPaymentMethod(paymentMethod);
            if (amountPaid != null) inquiry.setAmountPaid(amountPaid);
            if (dueAmount != null) inquiry.setDueAmount(dueAmount);
            inquiryRepository.save(inquiry);
            return ResponseEntity.ok("Payment info updated");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating payment");
        }
    }
}

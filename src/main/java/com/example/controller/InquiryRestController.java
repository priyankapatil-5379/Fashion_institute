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
}

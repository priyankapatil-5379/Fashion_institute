package com.example.repository;

import com.example.model.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findAllByOrderByCreatedAtDesc();
    List<Inquiry> findByCourseOfInterestInOrderByCreatedAtDesc(List<String> courseTitles);
    long countByIsReadFalse();
    long countByCourseOfInterestInAndIsReadFalse(List<String> courseTitles);

    @org.springframework.data.jpa.repository.Query("SELECT SUM(i.amountPaid) FROM Inquiry i")
    Double sumAllAmountPaid();
}

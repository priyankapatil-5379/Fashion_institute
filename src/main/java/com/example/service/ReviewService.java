package com.example.service;

import com.example.model.Review;
import com.example.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @jakarta.annotation.PostConstruct
    public void init() {
        if (reviewRepository.count() == 0) {
            Review r1 = new Review();
            r1.setStudentName("Sophia Evans");
            r1.setStudentRole("Makeup Artistry Alumna");
            r1.setComment("The most professional faculty I've ever seen. My career in makeup started right here with hands-on training that gave me the edge I needed.");
            r1.setRating(5);
            saveReview(r1);

            Review r2 = new Review();
            r2.setStudentName("Marcus Chen");
            r2.setStudentRole("Fashion Styling Graduate");
            r2.setComment("Excellent placement support. I landed my first fashion show styling gig within a month of graduating. The network here is truly global.");
            r2.setRating(5);
            saveReview(r2);

            Review r3 = new Review();
            r3.setStudentName("Elena Rodriguez");
            r3.setStudentRole("Catwalk & Stage Presence");
            r3.setComment("The Catwalk training transformed my confidence. I went from a shy beginner to walking for major designers. This institute builds icons.");
            r3.setRating(5);
            saveReview(r3);
        }
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAllByOrderByCreatedAtDesc();
    }

    public Review saveReview(Review review) {
        if (review.getAvatarUrl() == null || review.getAvatarUrl().isEmpty()) {
            review.setAvatarUrl("https://ui-avatars.com/api/?name=" + review.getStudentName().replace(" ", "+") + "&background=ff6b35&color=fff");
        }
        return reviewRepository.save(review);
    }
}

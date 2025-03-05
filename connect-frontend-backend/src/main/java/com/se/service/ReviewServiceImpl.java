package com.se.service;

import com.se.model.Review;
import com.se.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getReviewsBySpecialist(Long specialistId) {
        return reviewRepository.findBySpecialistId(specialistId);
    }

    @Override
    public List<Review> getReviewsByFacility(Long facilityId) {
        return reviewRepository.findByFacilityId(facilityId);
    }

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long id, Review review) throws Exception {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new Exception("Review not found"));
        existingReview.setComment(review.getComment());
        existingReview.setRating(review.getRating());
        existingReview.setTimestamp(review.getTimestamp());
        return reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReview(Long id) throws Exception {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new Exception("Review not found"));
        reviewRepository.delete(review);
    }

    @Override
    public List<Review> getReviewsBySalon(Long salonId) {
        return reviewRepository.findBySalonId(salonId);
    }
}

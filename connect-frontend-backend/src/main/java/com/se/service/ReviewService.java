package com.se.service;

import com.se.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews();

    List<Review> getReviewsBySpecialist(Long specialistId);

    List<Review> getReviewsByFacility(Long facilityId);

    Review createReview(Review review);

    Review updateReview(Long id, Review review) throws Exception;

    void deleteReview(Long id) throws Exception;

    List<Review> getReviewsBySalon(Long salonId);
}

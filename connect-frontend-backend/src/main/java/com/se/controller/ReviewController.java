package com.se.controller;

import com.se.model.ApiResponse;
import com.se.model.Review;
import com.se.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Get all reviews
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    // Get reviews by specialist ID
    @GetMapping("/specialist/{specialistId}")
    public List<Review> getReviewsBySpecialist(@PathVariable Long specialistId) {
        return reviewService.getReviewsBySpecialist(specialistId);
    }

    // Get reviews by service ID
    @GetMapping("/facility/{facilityId}")
    public List<Review> getReviewsByFacility(@PathVariable Long facilityId) {
        return reviewService.getReviewsByFacility(facilityId);
    }

    // Get reviews by salon
    @GetMapping("/salon/{salonId}")
    public List<Review> getReviewsBySalon(@PathVariable Long salonId) {
        return reviewService.getReviewsBySalon(salonId);
    }

    // Create a new review
    @PostMapping
    public ApiResponse createReview(@RequestBody Review review) {
        reviewService.createReview(review);
        return new ApiResponse("Review created successfully", true);
    }

    // Update an existing review
    @PutMapping("/{id}")
    public ApiResponse updateReview(@PathVariable Long id, @RequestBody Review review) throws Exception {
        reviewService.updateReview(id, review);
        return new ApiResponse("Review updated successfully", true);
    }

    // Delete a review by ID
    @DeleteMapping("/{id}")
    public ApiResponse deleteReview(@PathVariable Long id) throws Exception {
        reviewService.deleteReview(id);
        return new ApiResponse("Review deleted successfully", true);
    }
}

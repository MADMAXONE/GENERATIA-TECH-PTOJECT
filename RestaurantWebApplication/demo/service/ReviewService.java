package com.RestaurantWebApplication.demo.service;

import com.RestaurantWebApplication.demo.repository.ReviewRepository;
import com.RestaurantWebApplication.demo.entity.ReviewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewEntity> getAllReviews() {
        return reviewRepository.findAll();
    }

    public ReviewEntity getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    public ReviewEntity createReview(ReviewEntity reviewEntity) {
        return reviewRepository.save(reviewEntity);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public ReviewEntity updateReview(Long reviewId, ReviewEntity reviewUpdates) {
        ReviewEntity existingReview = getReviewById(reviewId);
        if (existingReview == null) {
            throw new RuntimeException("Review not found with ID: " + reviewId);
        }

        if (reviewUpdates.getName() != null) {
            existingReview.setName(reviewUpdates.getName());
        }
        if (reviewUpdates.getGrade() != 0) {
            existingReview.setGrade(reviewUpdates.getGrade());
        }
        if (reviewUpdates.getDescription() != null) {
            existingReview.setDescription(reviewUpdates.getDescription());
        }

        return reviewRepository.save(existingReview);
    }


}

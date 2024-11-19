package com.RestaurantWebApplication.demo.facade;

import com.RestaurantWebApplication.demo.entity.ReviewEntity;
import com.RestaurantWebApplication.demo.model.Review;
import com.RestaurantWebApplication.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewFacade {

    private final ReviewService reviewService;

    @Autowired
    public ReviewFacade(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public List<Review> getAllReviews() {
        List<ReviewEntity> reviewEntities = reviewService.getAllReviews();
        List<Review> reviews = new ArrayList<>();
        for (ReviewEntity reviewEntity : reviewEntities) {
            Review review = new Review();
            review.setName(reviewEntity.getName());
            review.setGrade(reviewEntity.getGrade());
            review.setId(reviewEntity.getId());
            review.setDescription(reviewEntity.getDescription());
            review.setDate(reviewEntity.getDate());
            reviews.add(review);
        }
        return reviews;
    }

    public Review getReviewById(Long reviewId) {
        ReviewEntity reviewById = reviewService.getReviewById(reviewId);
        Review review = new Review();
        review.setName(reviewById.getName());
        review.setId(reviewById.getId());
        review.setGrade(reviewById.getGrade());
        review.setDescription(reviewById.getDescription());
        review.setDate(reviewById.getDate());
        return review;
    }

    public void createReview(Review review) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setName(review.getName());
        reviewEntity.setGrade(review.getGrade());
        reviewEntity.setDescription(review.getDescription());
        reviewService.createReview(reviewEntity);
    }

    public void deleteReview(Long reviewId) {
        reviewService.deleteReview(reviewId);
    }

    public Review updateReview(Long reviewId, ReviewEntity reviewEntityToUpdate) {
        ReviewEntity updatedEntity = reviewService.updateReview(reviewId, reviewEntityToUpdate);
        return convertToReviewDTO(updatedEntity);
    }

    public Review convertToReviewDTO(ReviewEntity reviewEntity) {
        Review review = new Review();
        review.setId(reviewEntity.getId());
        review.setName(reviewEntity.getName());
        review.setGrade(reviewEntity.getGrade());
        review.setDescription(reviewEntity.getDescription());
        review.setDate(reviewEntity.getDate());
        return review;
    }


    public ReviewEntity convertToReviewEntity(Review review) {
        ReviewEntity reviewEntity = new ReviewEntity();
        if (review.getName() != null) {
            reviewEntity.setName(review.getName());
        }
        if (review.getGrade() != 0) {
            reviewEntity.setGrade(review.getGrade());
        }
        if (review.getDescription() != null) {
            reviewEntity.setDescription(review.getDescription());
        }

        return reviewEntity;
    }



}

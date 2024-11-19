package com.RestaurantWebApplication.demo.controller;

import com.RestaurantWebApplication.demo.entity.ReviewEntity;
import com.RestaurantWebApplication.demo.facade.ReviewFacade;
import com.RestaurantWebApplication.demo.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewFacade reviewFacade;

    @Autowired
    public ReviewController(ReviewFacade reviewFacade) {
        this.reviewFacade = reviewFacade;
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewFacade.getAllReviews();
    }

    @GetMapping("/{reviewId}")
    public Review getReview(@PathVariable Long reviewId) {
        return reviewFacade.getReviewById(reviewId);
    }

    @PostMapping
    public void createReview(@RequestBody Review review) {
        reviewFacade.createReview(review);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewFacade.deleteReview(reviewId);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId, @RequestBody Review updatedReview) {
        try {
            ReviewEntity reviewEntity = reviewFacade.convertToReviewEntity(updatedReview);

            Review updatedReviewDTO = reviewFacade.updateReview(reviewId, reviewEntity);

            return new ResponseEntity<>(updatedReviewDTO, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

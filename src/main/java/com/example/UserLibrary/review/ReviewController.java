package com.example.UserLibrary.review;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{userId}/{bookId}")
    public void addReview(@PathVariable Integer userId,
                          @PathVariable Integer bookId,
                          @RequestBody String description) {
        reviewService.addReview(userId,bookId,description);
    }


    @GetMapping("/book/{bookId}")
    public List<Review> getBookReviews(@PathVariable Integer bookId) {
      return reviewService.getBookReviews(bookId);
    }

    @GetMapping("/user/{userId}")
    public List<Review> getUserReviews(@PathVariable Integer userId) {
        return reviewService.getUserReviews(userId);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);
    }
}

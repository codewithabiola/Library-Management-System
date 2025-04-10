package com.example.UserLibrary.review;

import com.example.UserLibrary.Book.Book;
import com.example.UserLibrary.Book.BookRepository;
import com.example.UserLibrary.Security.BookNotAvailableException;
import com.example.UserLibrary.Security.UserNotFoundException;
import com.example.UserLibrary.users.UserRepository;
import com.example.UserLibrary.users.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void addReview(Long userId, Integer bookId,String description) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotAvailableException("Book not found"));

        Review review = new Review(user, book, description);
        reviewRepository.save(review);
    }

    public List<Review> getBookReviews(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotAvailableException("Book not found"));

        List<Review> reviews = reviewRepository.findByBook(book);
        if (reviews.isEmpty()) {
            throw new RuntimeException("No reviews found");
        }
        return reviews;
    }

    public List<Review> getUserReviews(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Review> reviews = reviewRepository.findByUser(user);
        if (reviews.isEmpty()) {
            throw new RuntimeException("No reviews found");
        }
        return reviews;
    }

    public void deleteReview(Integer reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}

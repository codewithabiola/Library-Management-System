package com.example.UserLibrary.review;


import com.example.UserLibrary.Book.Book;
import com.example.UserLibrary.users.Users;
import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reviewId;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "bookid",nullable = false)
    private Book book;

    private String description;

    public Review() {
    }

    public Review(Users user, Book book, String description) {
        this.user = user;
        this.book = book;
        this.description = description;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", user=" + user +
                ", book=" + book +
                ", description='" + description + '\'' +
                '}';
    }
}

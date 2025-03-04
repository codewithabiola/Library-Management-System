package com.example.UserLibrary.review;

import com.example.UserLibrary.Book.Book;
import com.example.UserLibrary.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByBook(Book book);

    List<Review> findByUser(Users user);
}

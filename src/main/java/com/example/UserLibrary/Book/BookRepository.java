package com.example.UserLibrary.Book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {


    Optional<Book> findBookByTitle(String title);
    List<Book> findByAvailableCopiesGreaterThan(int copies);
}

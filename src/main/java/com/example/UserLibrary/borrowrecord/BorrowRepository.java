package com.example.UserLibrary.borrowrecord;


import com.example.UserLibrary.Book.Book;
import com.example.UserLibrary.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowRepository extends JpaRepository<BorrowRecord, Integer> {

    List<BorrowRecord> findByUser(Users user);

    boolean existsByUserAndBook(Users user, Book book);

    Optional<BorrowRecord> existsByUserandBook(Users user, Book book);

}

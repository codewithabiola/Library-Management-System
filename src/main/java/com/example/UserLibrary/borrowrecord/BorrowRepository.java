package com.example.UserLibrary.borrowrecord;


import com.example.UserLibrary.Book.Book;
import com.example.UserLibrary.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BorrowRepository extends JpaRepository<BorrowRecord, Integer> {

    List<BorrowRecord> findByUser(Users user);

    boolean existsByUserAndBook(Users user, Book book);

    @Query("SELECT br.book, COUNT (br.borrowId) AS borrowCount " +
            "FROM BorrowRecord br " +
            "group by br.book " +
            "order by borrowCount desc " )
    List<Book> findMostBorrowedBooks();

    List<BorrowRecord> findByReturnDateIsNullAndReturnDateBefore(Date date);

    @Query("SELECT br.user, COUNT(br.borrowId) AS borrowCount " +
            "FROM BorrowRecord br " +
            "GROUP BY br.user " +
            "ORDER BY borrowCount DESC")
    List<Users> findMostBorrowedUsers();
}

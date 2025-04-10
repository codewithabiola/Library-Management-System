package com.example.UserLibrary.Security;


import com.example.UserLibrary.Book.Book;
import com.example.UserLibrary.Book.BookRepository;
import com.example.UserLibrary.Book.BookService;
import com.example.UserLibrary.borrowrecord.BorrowRecord;
import com.example.UserLibrary.borrowrecord.BorrowRepository;
import com.example.UserLibrary.users.UserRepository;
import com.example.UserLibrary.users.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final BorrowRepository borrowRepository;
    private final BookService bookService;
    private final BookRepository bookRepository;

    public AdminController(UserRepository userRepository, BorrowRepository borrowRepository, BookService bookService, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.borrowRepository = borrowRepository;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/reports/borrowed-books")
    public List<BorrowRecord> getBorrowedBooks() {
        return borrowRepository.findAll();
    }

    @GetMapping("/reports/overdue-books")
    public List<BorrowRecord> getOverdueBooks() {
        Date today = new Date();
        return borrowRepository.findByReturnDateIsNullAndReturnDateBefore(today);
    }

    @GetMapping("/reports/popular-books")
    public List<Book> getPopularBooks() {
        return borrowRepository.findMostBorrowedBooks();
    }

    @GetMapping("/reports/active-users")
    public List<Users> getActiveUsers() {
        return borrowRepository.findMostBorrowedUsers();

    }


    @PostMapping("/books")
    public ResponseEntity<Book> addBook(
            @RequestBody Book book) {
        bookService.addBook(book);
        return ResponseEntity.ok(book);
    }


}

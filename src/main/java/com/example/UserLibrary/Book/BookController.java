package com.example.UserLibrary.Book;


import com.example.UserLibrary.borrowrecord.BorrowRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return ResponseEntity.ok(book);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getBooks();
    }

    @GetMapping(path = "{bookId}")
    public ResponseEntity<Book> getBookDetails(@PathVariable Integer bookId) {
        return ResponseEntity.ok(bookService.getBookdetails(bookId));

    }

    @PutMapping(path = "{bookId}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Integer bookId, @RequestBody Book book
    ){
        bookService.updateBook(bookId, book);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("{bookId}")
    public void deleteBook(@PathVariable Integer bookId) {
        bookService.deleteBook(bookId);
    }

    @GetMapping(path = "/available")
    public List<Book> getAvailableBooks() {
        return bookService.availableCopies();
    }

    @GetMapping(path = "/borrowed")
    public List<BorrowRecord> getBorrowedBooks(
            @RequestBody Integer userId
    ) {
        return bookService.borrowedBooks(userId);
    }


}

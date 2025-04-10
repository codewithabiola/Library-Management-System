package com.example.UserLibrary.Book;


import com.example.UserLibrary.borrowrecord.BorrowRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    public BookController(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

//
//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Book> addBook(@RequestBody Book book) {
//        bookService.addBook(book);
//        return ResponseEntity.ok(book);
//    }

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

    @GetMapping("/search1")
    public ResponseEntity<List<Book>> searchBooksbyTitle(
            @RequestParam String title
            ) {
        List<Book> books = new ArrayList<>();
        if (title !=null){
            books = bookRepository.findByTitleContainingIgnoreCase(title);
        }
        if (books.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(books);
        }

        return ResponseEntity.ok(books);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooksbyAuthor(
            @RequestParam String author
            ) {
        List<Book> books = new ArrayList<>();
        if (author !=null){
            books = bookRepository.findByAuthorContainingIgnoreCase(author);
        }
        if (books.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(books);
        }
        return ResponseEntity.ok(books);
    }


    @GetMapping(path = "/available")
    public List<Book> getAvailableBooks() {
        return bookService.availableCopies();
    }


    @GetMapping(path = "/borrowed")
    public List<BorrowRecord> getBorrowedBooks(
            @RequestBody Long userId
    ) {
        return bookService.borrowedBooks(userId);
    }


}

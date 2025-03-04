package com.example.UserLibrary.Book;

import com.example.UserLibrary.borrowrecord.BorrowRecord;
import com.example.UserLibrary.borrowrecord.BorrowRepository;
import com.example.UserLibrary.users.UserRepository;
import com.example.UserLibrary.users.Users;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowRepository borrowRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository, BorrowRepository borrowRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowRepository = borrowRepository;
    }

    public Book addBook(Book book) {
       Optional<Book> bookOptional = bookRepository.findBookByTitle(book.getTitle());
                if(bookOptional.isPresent()) {
                    throw new IllegalStateException("Book already exists");
                }
                return bookRepository.save(book);
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public Book getBookdetails(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("Book not found"));

        return book;

    }

    public Book updateBook(Integer bookId, Book updatedBook) {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("Book not found"));

        if(updatedBook.getTitle() != null) {
            existingBook.setTitle(updatedBook.getTitle());
        }
        if(updatedBook.getAuthor() != null) {
            existingBook.setAuthor(updatedBook.getAuthor());
        }
        if(updatedBook.getPublishedYear() != null) {
            existingBook.setPublishedYear(updatedBook.getPublishedYear());
        }
        existingBook.setAvailableCopies(updatedBook.getAvailableCopies()); //can get any value

        return bookRepository.save(existingBook);
    }

    public void deleteBook(Integer bookId) {
        boolean exists = bookRepository.existsById(bookId);
        if(!exists) {
            throw new IllegalStateException("Book not found");
        }
       bookRepository.deleteById(bookId);
    }

    public List<Book> availableCopies() {
        List<Book> books = bookRepository.findByAvailableCopiesGreaterThan(0);
        if(books.isEmpty()) {
            throw new IllegalStateException("No available copies found");
        }
        return books;
    }

    public List<BorrowRecord> borrowedBooks(Integer userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<BorrowRecord> borrowRecords = borrowRepository.findByUser(user);
        List<Book> books = new ArrayList<>();
        for (BorrowRecord record : borrowRecords) {
            if(!books.contains(record.getBook())) {
                books.add(record.getBook());
            }
        }
        return borrowRecords;
    }

}

package com.example.UserLibrary.borrowrecord;

import com.example.UserLibrary.Book.Book;
import com.example.UserLibrary.Book.BookRepository;
import com.example.UserLibrary.users.UserRepository;
import com.example.UserLibrary.users.Users;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BorrowService(BorrowRepository borrowRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }


    public void borrowBook(Integer userId, Integer bookId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if(book.getAvailableCopies()<=0){
            throw new IllegalStateException("Copies not available");
        }
        if(borrowRepository.existsByUserAndBook(user,book)){
            throw new IllegalStateException("User has already borrowed book");
        }

        Date borrowDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrowDate);
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        Date returnDate = calendar.getTime(); //convert

        BorrowRecord record = new BorrowRecord(user,book,borrowDate,returnDate,"Borrowed");
        borrowRepository.save(record);
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
    }


    public void returnBook(Integer borrowId) {

        BorrowRecord record = borrowRepository.findById(borrowId)
                        .orElseThrow(() -> new RuntimeException("Record not found"));

        Book book = record.getBook();
        borrowRepository.delete(record);
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
    }


    public List<BorrowRecord> getBorrowedBooks() {
       return borrowRepository.findAll();
    }


    public List<BorrowRecord> getUserBorrowingHistory(Integer userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<BorrowRecord> records = borrowRepository.findByUser(user);
        if(records.isEmpty()){
            throw new IllegalStateException("No borrowing history found");
        }
        return records;
    }

    public List<BorrowRecord> overdueBooks() {

        List<BorrowRecord> records = borrowRepository.findAll();
        List<BorrowRecord> overdueRecords = new ArrayList<>();
        Date today = new Date();

        for(BorrowRecord record : records){
            if(record.getReturnDate() != null && record.getReturnDate().before(today)){
                overdueRecords.add(record);
            }
        }
        if(overdueRecords.isEmpty()){
            throw new IllegalStateException("No overdue books found");
        }
        return overdueRecords;
    }

    public void requestExtension(Integer borrowId, int extensionDays) {
        BorrowRecord borrowRecord = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        Date today = new Date();
        if(borrowRecord.getReturnDate().before(today)){
            throw new IllegalStateException("can't extend, book is already overdue");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrowRecord.getReturnDate());
        calendar.add(Calendar.DAY_OF_MONTH, extensionDays);
        Date newReturnDate = calendar.getTime();

        borrowRecord.setReturnDate(newReturnDate);
        borrowRepository.save(borrowRecord);
    }
}

package com.example.UserLibrary.borrowrecord;


import com.example.UserLibrary.Book.Book;
import com.example.UserLibrary.users.Users;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "borrowrecord")
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "borrowrecord_seq")
    @SequenceGenerator(name = "borrowrecord_seq", sequenceName = "borrowrecord_seq", allocationSize = 1)
    @Column(name = "borrowid")
    private int borrowId;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "bookid",nullable = false)
    private Book book;

    private Date borrowDate;
    private Date returnDate;
    private String status;

    public BorrowRecord() {
    }

    public BorrowRecord(
                        Users user,
                        Book book,
                        Date borrowDate,
                        Date returnDate,
                        String status) {

        this.user = user;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
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

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

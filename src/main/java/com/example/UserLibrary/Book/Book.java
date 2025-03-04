package com.example.UserLibrary.Book;


import com.example.UserLibrary.borrowrecord.BorrowRecord;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;

    private String title;
    private String author;
    private Date publishedYear;
    private int availableCopies;

    @OneToMany
    private List<BorrowRecord> borrowRecords;


    public Book() {
    }

    public Book(int bookId, String title, String author, Date publishedYear, int availableCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.availableCopies = availableCopies;
    }


    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Date publishedYear) {
        this.publishedYear = publishedYear;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    @Override
    public String toString() {
        return "Book{" +
                "BookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishedYear=" + publishedYear +
                ", availableCopies=" + availableCopies +
                '}';
    }
}

package com.example.UserLibrary.Reservation;


import com.example.UserLibrary.Book.Book;
import com.example.UserLibrary.users.Users;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reservationId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "bookId", nullable = false)
    private Book book;

    private Date reservationDate;
    private String details;

    public Reservation() {
    }

    public Reservation(
                       Users user,
                       Book book,
                       Date reservationDate,
                       String details) {

        this.user = user;
        this.book = book;
        this.reservationDate = reservationDate;
        this.details = details;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
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

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", user=" + user +
                ", book=" + book +
                ", reservationDate=" + reservationDate +
                ", details='" + details + '\'' +
                '}';
    }
}

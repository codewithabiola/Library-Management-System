package com.example.UserLibrary.Reservation;

import com.example.UserLibrary.Book.Book;
import com.example.UserLibrary.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    boolean existsByUserAndBook(Users user, Book book);

    boolean findByReservationDate(Date reservationDate);

    List<Reservation> findByUser(Users user);

    List<Reservation> findByBook(Book book);
}

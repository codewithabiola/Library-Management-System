package com.example.UserLibrary.Reservation;

import com.example.UserLibrary.Book.Book;
import com.example.UserLibrary.Book.BookRepository;
import com.example.UserLibrary.users.UserRepository;
import com.example.UserLibrary.users.Users;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void reserveBook(Integer userId, Integer bookId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if(reservationRepository.existsByUserAndBook(user, book)) {
            throw new RuntimeException("Reservation already exists");
        }
        Date reservationDate = new Date();

        Reservation reservation = new Reservation(user,book,reservationDate,null);
        reservationRepository.save(reservation);
    }

    public List<Reservation> getUserReservations(Integer userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Reservation> reservations = reservationRepository.findByUser(users);
        if(reservations.isEmpty()) {
            throw new RuntimeException("No reservations found");
        }
        return reservations;
    }

    public List<Reservation> getBookReservations(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        List<Reservation> reservations = reservationRepository.findByBook(book);
        if(reservations.isEmpty()) {
            throw new RuntimeException("No reservations found");
        }
        return reservations;
    }


    public void cancelReservation(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservationRepository.delete(reservation);
    }
}

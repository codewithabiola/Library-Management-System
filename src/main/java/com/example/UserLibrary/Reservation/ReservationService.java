package com.example.UserLibrary.Reservation;

import com.example.UserLibrary.Book.Book;
import com.example.UserLibrary.Book.BookRepository;
import com.example.UserLibrary.Security.AesUtils;
import com.example.UserLibrary.Security.BookNotAvailableException;
import com.example.UserLibrary.Security.UserNotFoundException;
import com.example.UserLibrary.users.UserRepository;
import com.example.UserLibrary.users.Users;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public void reserveBook(Long userId, Integer bookId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotAvailableException("Book not found"));

        if(reservationRepository.existsByUserAndBook(user, book)) {
            throw new RuntimeException("Reservation already exists");
        }

        String encryptedName = AesUtils.encrypt(user.getName());

        Date reservationDate = new Date();

        Reservation reservation = new Reservation(user,book,reservationDate,"Reserved");
        reservation.setEncryptedName(encryptedName);

        reservationRepository.save(reservation);
    }


    public List<Reservation> getUserReservations(Long userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Reservation> reservations = reservationRepository.findByUser(users);
        if(reservations.isEmpty()) {
            throw new RuntimeException("No reservations found");
        }

        for (Reservation res : reservations) {
            String decryptedName = AesUtils.decrypt(res.getEncryptedName());
            System.out.println("Encrypted name: " + res.getEncryptedName());

            res.setDecryptedName(decryptedName);
            System.out.println("Decrypted name: " + decryptedName);
        }

        return reservations;
    }


    public List<Reservation> getCurrentUserReservations(Long userId) {
        String authEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Users authUsers = userRepository.findUserByEmail(authEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!authUsers.getId().equals(userId)) {
            throw new RuntimeException("You can only view your own reservations");
        }

        List<Reservation> reservations = reservationRepository.findByUser(authUsers);
        if(reservations.isEmpty()) {
            throw new RuntimeException("No reservations found");
        }
        return reservations;
    }


    public List<Reservation> getBookReservations(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotAvailableException("Book not found"));

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

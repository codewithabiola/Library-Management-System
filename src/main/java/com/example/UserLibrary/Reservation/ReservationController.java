package com.example.UserLibrary.Reservation;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/{userId}/{bookId}")
    public void reserveBook(@PathVariable Long userId,
                            @PathVariable Integer bookId ) {
        reservationService.reserveBook(userId, bookId);
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getUserReservations(@PathVariable Long userId) {
        return reservationService.getUserReservations(userId);
    }

    @GetMapping("/user/currentUser/{userId}")
    public List<Reservation> getCurrentUserReservations(@PathVariable Long userId) {
        return reservationService.getCurrentUserReservations(userId);
    }

    @GetMapping("/book/{bookId}")
    public List<Reservation> getBookReservations(@PathVariable Integer bookId) {
        return reservationService.getBookReservations(bookId);
    }

    @DeleteMapping("/{reservationId}")
    public void cancelReservation(@PathVariable Integer reservationId) {
        reservationService.cancelReservation(reservationId);
    }
}

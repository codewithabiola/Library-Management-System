package com.example.UserLibrary.borrowrecord;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/borrow")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping( "{userId}/{bookId}")
    public void borrowBook(@PathVariable("userId") Long userId, @PathVariable("bookId") Integer bookId) {
        borrowService.borrowBook(userId,bookId);
    }

    @PutMapping("/{borrowId}")
    public void returnBook(@PathVariable("borrowId") Integer borrowId) {
        borrowService.returnBook(borrowId);
    }

    @GetMapping("/records")
    public List<BorrowRecord> getRecords() {
        return borrowService.getBorrowedBooks();
    }

    @GetMapping("/user/{userId}")
    public List<BorrowRecord> getBorrowingHistory(
            @PathVariable("userId") Long userId) {
        return borrowService.getUserBorrowingHistory(userId);
    }


    @GetMapping("/overdue")
    public List<BorrowRecord> getOverdueRecords() {
        return borrowService.overdueBooks();
    }

    @PostMapping("/{borrowId}")
    public void requestExtension(
            @PathVariable("borrowId") Integer borrowId,
            @RequestParam Integer extensionDays) {
        borrowService.requestExtension(borrowId, extensionDays);
    }


}

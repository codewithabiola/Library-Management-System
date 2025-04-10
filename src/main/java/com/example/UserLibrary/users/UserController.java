package com.example.UserLibrary.users;


import com.example.UserLibrary.Book.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Users> registerUser(@RequestBody Users user) {
        userService.registerUser(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public List<Users> getUsers() {
       return userService.getUsers();
    }

    @GetMapping(path = "{userId}")
    public Users getUserById(@PathVariable Long userId) {
       return userService.getUsersById(userId);
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<Users> updateUser(
            @PathVariable Long userId, @RequestBody Users user
    ){
        userService.updateUser(userId, user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Users>> searchUserByName(@RequestParam String name) {
        List<Users> users = userRepository.findByNameContainingIgnoreCase(name);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(users);
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("{userId}/borrowed-books")
    public List<Book> getBorrowedByUser(
            @PathVariable Long userId
    ){
        return userService.getBorrowedByUser(userId);
    }


}

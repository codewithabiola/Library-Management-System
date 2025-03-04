package com.example.UserLibrary.users;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
    public Users getUserById(@PathVariable Integer userId) {
       return userService.getUsersById(userId);
    }

    @PutMapping(path = "{userId}")
    public ResponseEntity<Users> updateUser(
            @PathVariable Integer userId, @RequestBody Users user
    ){
        userService.updateUser(userId, user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }


}

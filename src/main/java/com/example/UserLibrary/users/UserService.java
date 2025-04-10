package com.example.UserLibrary.users;

import com.example.UserLibrary.Book.Book;
import com.example.UserLibrary.Security.UserNotFoundException;
import com.example.UserLibrary.borrowrecord.BorrowRecord;
import com.example.UserLibrary.borrowrecord.BorrowRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BorrowRepository borrowRepository;


    public UserService(UserRepository userRepository, BorrowRepository borrowRepository) {
        this.userRepository = userRepository;
        this.borrowRepository = borrowRepository;
    }

    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    public Users registerUser(Users user) {
        Optional<Users> userOptional = userRepository.findUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        userRepository.save(user);
        return user;
    }

    public Users getUsersById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));
    }

    public Users updateUser(Long userId, Users updatedUser) {
        Users existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        if(updatedUser.getName() == null && updatedUser.getEmail()==null && updatedUser.getUserRole()==null) {
            throw new IllegalStateException("Invalid user data");
        }
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setUserRole(updatedUser.getUserRole());
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long userId) {
        boolean userExists = userRepository.existsById(userId);
        if (!userExists) {
            throw new UserNotFoundException("User does not exist");
        }
        userRepository.deleteById(userId);
    }

    public List<Book> getBorrowedByUser(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        List<BorrowRecord> records = borrowRepository.findByUser(user);
        List<Book> books = new ArrayList<>();
        Date today = new Date();

        for (BorrowRecord record : records) {
            if (!record.getReturnDate().before(today)) {
                books.add(record.getBook());
            }
        }
        if (books.isEmpty()) {
            throw new IllegalStateException("No currently borrowed books found for this user");
        }
        return books;

    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


}

package com.example.UserLibrary.users;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    public Users registerUser(Users user) {
        Optional<Users> userOptional = userRepository.findUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("User already exists");
        }
        userRepository.save(user);
        return user;
    }

    public Users getUsersById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User does not exist"));
    }

    public Users updateUser(Integer userId, Users updatedUser) {
        Users existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User does not exist"));

        if(updatedUser.getName() == null || updatedUser.getEmail()==null || updatedUser.getUserRole()==null) {
            throw new IllegalStateException("Invalid user data");
        }
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setUserRole(updatedUser.getUserRole());
        return userRepository.save(existingUser);
    }

    public void deleteUser(Integer userId) {
        boolean userExists = userRepository.existsById(userId);
        if (!userExists) {
            throw new IllegalStateException("User does not exist");
        }
        userRepository.deleteById(userId);
    }

}

package com.example.UserLibrary.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findUserByEmail(String email);
}

package com.example.UserLibrary.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findUserByEmail(String email);

    List<Users> findByNameContainingIgnoreCase(String name);

}

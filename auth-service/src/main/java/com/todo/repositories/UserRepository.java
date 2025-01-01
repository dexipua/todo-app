package com.todo.repositories;

import com.todo.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase
            (String firstName, String lastName, Pageable pageable);
    List<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    Optional<User> findByEmail(String email);
}
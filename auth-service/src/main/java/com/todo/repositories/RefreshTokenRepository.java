package com.todo.repositories;

import com.todo.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
    Optional<RefreshToken> findByUsername(String token);
    void deleteAllByUsername(String username);
}

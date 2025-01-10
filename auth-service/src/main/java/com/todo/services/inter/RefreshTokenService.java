package com.todo.services.inter;

import com.todo.models.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    String createRefreshToken(String username);
    Optional<RefreshToken> findByToken(String token);
    void deleteToken(String token);
    boolean validateRefreshToken(RefreshToken refreshToken);
    void deleteAllByUsername(String username);
}

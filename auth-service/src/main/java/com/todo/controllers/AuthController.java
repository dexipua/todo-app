package com.todo.controllers;

import com.todo.DTOs.auth.AuthResponse;
import com.todo.DTOs.auth.LoginRequest;
import com.todo.DTOs.user.UserRequestCreate;
import com.todo.DTOs.user.UserResponse;
import com.todo.config.JwtUtils;
import com.todo.mappers.UserMapper;
import com.todo.models.User;
import com.todo.services.impl.RefreshTokenServiceImpl;
import com.todo.services.inter.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenServiceImpl refreshTokenService;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestCreate userRequest) throws BadRequestException {
        if(userService.existsByEmail(userRequest.getEmail())){
            throw new BadRequestException("User with such email already exist");
        }

        User user = new User(userRequest.getEmail(),userRequest.getPassword(),
                userRequest.getFirstName(), userRequest.getLastName());

        userService.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        User user = (User) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(user.getUsername());
        refreshTokenService.deleteAllByUsername(user.getUsername());
        refreshTokenService.createRefreshToken(user.getUsername());

        return new AuthResponse(user.getId(), user.getUsername(), jwtToken,
                user.getRole().getAuthority());
    }

    @GetMapping("/getAuth")
    public UserResponse getAuth(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User realUser = userService.findByEmail(user.getEmail());
        return userMapper.fromUserToUserResponse(realUser);
    }
}

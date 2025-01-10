package com.todo.controllers;

import com.todo.DTOs.auth.AuthResponse;
import com.todo.DTOs.auth.LoginRequest;
import com.todo.DTOs.string.RequestEmail;
import com.todo.DTOs.string.ResponseString;
import com.todo.DTOs.user.UserRequestCreate;
import com.todo.DTOs.user.UserResponse;
import com.todo.config.JwtUtils;
import com.todo.mappers.UserMapper;
import com.todo.models.User;
import com.todo.services.impl.RefreshTokenServiceImpl;
import com.todo.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenServiceImpl refreshTokenService;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/checksEmail")
    public boolean checkEmail(@RequestBody RequestEmail requestEmail) {
        return userService.existsByEmail(requestEmail.getValue(), requestEmail.isRegis());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseString registerUser(@Valid @RequestBody UserRequestCreate userRequest){
        User user = new User(userRequest.getEmail(),userRequest.getPassword(),
                userRequest.getFirstName(), userRequest.getLastName());

        userService.save(user);
        return new ResponseString("User registered successfully");
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        User user = (User) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("token", user.getToken());
        claims.put("name", user.getFirstName() + " " + user.getLastName());

        String jwtToken = jwtUtils.generateTokenFromUsername(user.getUsername(), claims);
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

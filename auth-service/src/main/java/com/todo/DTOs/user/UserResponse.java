package com.todo.DTOs.user;


import lombok.Data;

@Data
public class UserResponse {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
}

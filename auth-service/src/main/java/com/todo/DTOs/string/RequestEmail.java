package com.todo.DTOs.string;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestEmail {
    private String value;
    private boolean isRegis;
}

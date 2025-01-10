package com.todo.DTOs.exception;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class ExceptionResponse {
    private List<String> messages;

    public ExceptionResponse(String message) {
       this.messages = List.of(message);
    }

    public ExceptionResponse(List<String> messages) {
        this.messages = messages;
    }

}

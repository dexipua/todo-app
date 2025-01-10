package com.todo.mappers;

import com.todo.DTOs.user.UserResponse;
import com.todo.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "firstName", target = "firstName")
    UserResponse fromUserToUserResponse(User user);
}

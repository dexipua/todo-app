package com.todo.mappers;

import com.todo.DTOs.user.UserRequestCreate;
import com.todo.DTOs.user.UserRequestUpdate;
import com.todo.models.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //@Mapping(target = "id", ignore = true)
    //@Mapping(target = "role", ignore = true)
    User toUser(UserRequestCreate userRequestCreate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromRequest(UserRequestUpdate userRequestUpdate, @MappingTarget User user);

    @InheritInverseConfiguration(name = "toUser")
    UserRequestCreate toUserRequestCreate(User user);
}

package com.todo.services.inter;

import com.todo.DTOs.user.UserRequestUpdate;
import com.todo.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User create(User user);
    User findById(int id);
    User update(int userToUpdateId, UserRequestUpdate userRequest);
    User findByEmail(String email);
}

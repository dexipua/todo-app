package com.todo.services.inter;

import com.todo.DTOs.user.UserRequestUpdate;
import com.todo.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(User user);
    User findById(long id);
    User update(long userToUpdateId, UserRequestUpdate userRequest);
    User findByEmail(String email);
    boolean existsByEmail(String email);
}

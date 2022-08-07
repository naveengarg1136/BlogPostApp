package com.boogieboogie.blog.services;

import com.boogieboogie.blog.payload.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer user_id);
    UserDto getUserById(Integer user_id);
    List<UserDto> getAllUser();
    void deleteUser(Integer user_id);
}

package com.boogieboogie.blog.services.impl;

import com.boogieboogie.blog.exceptions.ResourseNotFoundException;
import com.boogieboogie.blog.model.User;
import com.boogieboogie.blog.payload.UserDto;
import com.boogieboogie.blog.repos.UserRepo;
import com.boogieboogie.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user= this.dtoToUser(userDto);
        User savedUser=this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer user_id) {
        User user= this.userRepo.findById(user_id).orElseThrow(()-> new ResourseNotFoundException("User","id",user_id));
        user.setUser_name(userDto.getUser_name());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        User updatedUser= this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer user_id) {
        User user= this.userRepo.findById(user_id).orElseThrow(()-> new ResourseNotFoundException("User","id",user_id));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User>users= this.userRepo.findAll();
        List<UserDto>userDtos =users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer user_id) {
        User user= this.userRepo.findById(user_id).orElseThrow(()-> new ResourseNotFoundException("User","id",user_id));
        this.userRepo.delete(user);

    }

    @Autowired
    private ModelMapper modelMapper;

    private User dtoToUser(UserDto userDto){
        User user= this.modelMapper.map(userDto,User.class);
//        user.setUser_id(userDto.getUser_id());
//        user.setUser_name(userDto.getUser_name());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
        return user;
    }
    private UserDto userToDto(User user){
        UserDto userDto=this.modelMapper.map(user,UserDto.class);
//        userDto.setEmail(user.getEmail());
//        userDto.setUser_name(user.getUser_name());
//        userDto.setPassword(user.getPassword());
//        userDto.setUser_id(user.getUser_id());
        return userDto;
    }
}

package com.boogieboogie.blog.controllers;


import com.boogieboogie.blog.payload.UserDto;
import com.boogieboogie.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Create user POST
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createdUserDto=this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }
    //update user
    @PutMapping("/{user_id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer user_id){
        UserDto updatedUserDto=this.userService.updateUser(userDto,user_id);
        return ResponseEntity.ok(updatedUserDto);
    }
    // Delete user
    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer user_id){
        this.userService.deleteUser(user_id);
        return new ResponseEntity(Map.of("message", "User deleted successfully"),HttpStatus.OK);
    }

    //Get UserById
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer user_id){
        return ResponseEntity.ok(this.userService.getUserById(user_id));
    }

    //Get All user
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUser());
    }
}

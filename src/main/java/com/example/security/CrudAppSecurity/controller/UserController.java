package com.example.security.CrudAppSecurity.controller;


import com.example.security.CrudAppSecurity.entity.User;
import com.example.security.CrudAppSecurity.entity.UserDto;
import com.example.security.CrudAppSecurity.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService theUserService){
        userService = theUserService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    //private JSONObject jsonObject;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public List<User> findAll(){
        return userService.findAll();
    }
    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable int userId){

        User theUser = userService.findById(userId);

        if (theUser == null){
            throw new RuntimeException("user id not found: " + userId);
        }

        return theUser;
    }
    @PostMapping("/users")
    public User addUser(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();


        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and Password are required: ");
        }

        User theUser = new User();
        theUser.setUsername(username);
        theUser.setPassword(passwordEncoder.encode(password));

        User dbUser = userService.save(theUser);

        return dbUser;
    }


    @PutMapping("/users")
    public User updateUser(@RequestBody User updatedUser) {
        User existingUser = userService.findById(updatedUser.getId());

        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        existingUser.setUsername(updatedUser.getUsername());


        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        User dbUser = userService.save(existingUser);

        return dbUser;
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable int userId){
        User tempUser = userService.findById(userId);

                if(tempUser==null){
            throw  new RuntimeException("UserId not found: " + userId);
        }
        userService.deleteById(userId);

        return "Deleted user id- " + userId;
    }






    @GetMapping("/query")
    public List<User> finduserusingQuery(){
        return userService.finduserusingQuery();
    }














}


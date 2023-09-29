package com.example.security.CrudAppSecurity.service;

import com.example.security.CrudAppSecurity.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int theId);

    User save(User theUser);

    void deleteById(int theId);

    List<User> finduserusingQuery();


}

package com.example.security.CrudAppSecurity.entity;

import java.util.List;

public interface UserDao {

    List<User> findAll();
    User findById(int theId);

    User save(User theUser);

    void deleteById(int theId);

}

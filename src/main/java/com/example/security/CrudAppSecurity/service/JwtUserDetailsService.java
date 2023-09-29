package com.example.security.CrudAppSecurity.service;

import com.example.security.CrudAppSecurity.entity.User;
import com.example.security.CrudAppSecurity.entity.UserDto;
import com.example.security.CrudAppSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository user;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDao = user.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(userDao.getUsername(), userDao.getPassword(),
                new ArrayList<>());
    }

    public User save(UserDto userDao) {
        User newUser = new User();
        newUser.setUsername(userDao.getUsername());
        newUser.setPassword(bcryptEncoder.encode(userDao.getPassword()));
        return user.save(newUser);
    }
}
package com.example.security.CrudAppSecurity.service;

import com.example.security.CrudAppSecurity.entity.User;
import com.example.security.CrudAppSecurity.entity.UserDao;
import com.example.security.CrudAppSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserDao theUserDao, PasswordEncoder passwordEncoder) {
        this.userDao = theUserDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(int theId) {
        return userDao.findById(theId);
    }


    @Transactional
    @Override
    public User save(User theUser) {
        if (theUser.getId() != 0) {
            User dbUser = userDao.findById(theUser.getId());
            if (dbUser != null) {
                if (!passwordEncoder.matches(theUser.getPassword(), dbUser.getPassword())) {
                    theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));
                }
            }
        } else {
            theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));
        }

        return userDao.save(theUser);
    }


    @Transactional
    @Override
    public void deleteById(int theId) {
        userDao.deleteById(theId);
    }

    @Override
    public List<User> finduserusingQuery() {
        return userRepository.findUsersUsingQuery();
    }


}


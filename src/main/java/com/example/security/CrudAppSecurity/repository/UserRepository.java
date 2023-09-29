package com.example.security.CrudAppSecurity.repository;


import com.example.security.CrudAppSecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);


    @Query(value = "SELECT * FROM user", nativeQuery = true)
    List<User> findUsersUsingQuery();


}

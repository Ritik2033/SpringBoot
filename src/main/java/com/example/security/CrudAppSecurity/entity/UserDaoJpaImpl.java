package com.example.security.CrudAppSecurity.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoJpaImpl implements UserDao{
    private EntityManager entityManager;
    private PasswordEncoder passwordEncoder;



    @Autowired

    public UserDaoJpaImpl(EntityManager theEntityManager,PasswordEncoder passwordEncoder){
        entityManager=theEntityManager;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public List<User> findAll(){

        TypedQuery<User> theQuery = entityManager.createQuery("from User", User.class);



        List<User> users = theQuery.getResultList();
        return users;
    }

    @Override
    public User findById(int theId) {

        User theUser = entityManager.find(User.class,theId);
        return theUser;
    }

    @Override
    public User save(User theUser) {
        if (theUser.getId() == 0) {

            theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));
            entityManager.persist(theUser);
        } else {

            User dbUser = entityManager.find(User.class, theUser.getId());
            if (dbUser != null) {
                dbUser.setUsername(theUser.getUsername());
                if (!passwordEncoder.matches(theUser.getPassword(), dbUser.getPassword())) {
                    dbUser.setPassword(passwordEncoder.encode(theUser.getPassword()));
                }
                entityManager.merge(dbUser);
            }
        }

        return theUser;
    }




    @Override
    public void deleteById(int theId) {

        User theUser = entityManager.find(User.class, theId);
        entityManager.remove(theUser);
    }
}

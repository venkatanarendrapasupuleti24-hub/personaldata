package com.personal.expense.service;

import com.personal.expense.model.User;
import com.personal.expense.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user) {
        userRepository.save(user);
    }
    public boolean emailExists(String email) {

        return userRepository
                .findByEmail(email) != null;
    }
    public User loginUser(
            String email,
            String password) {

        return userRepository
                .findByEmailAndPassword(
                        email,
                        password
                );
    }
}
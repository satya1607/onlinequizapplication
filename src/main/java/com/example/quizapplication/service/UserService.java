package com.example.quizapplication.service;

import java.util.Optional;

import com.example.quizapplication.entity.User;

public interface UserService {
    void createUser(User user);
    Boolean hasUserWithEmail(String email);
    User login(String email);
    void createAdminUser();
//    User login(User user);
}

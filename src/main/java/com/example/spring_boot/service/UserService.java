package com.example.spring_boot.service;

import com.example.spring_boot.models.User;

import java.util.List;

public interface UserService {
    void save(User user);
    void delete(Long id);
    List<User> findAll();
    User findByUsername(String username);
    User getUserById(Long id);
}
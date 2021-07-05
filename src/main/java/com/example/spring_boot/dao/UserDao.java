package com.example.spring_boot.dao;

import com.example.spring_boot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User getUserById(Long id);
}

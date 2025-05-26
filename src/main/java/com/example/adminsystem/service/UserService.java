package com.example.adminsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.adminsystem.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    // IService already provides common CRUD methods.
    // Add custom service methods here if needed.
    // For example:
    // User findByUsername(String username);
    // List<User> findActiveUsers();
}

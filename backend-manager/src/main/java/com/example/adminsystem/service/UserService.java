package com.example.adminsystem.service;

import com.example.adminsystem.dto.UserRegistrationDto;
import com.example.adminsystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    User register(UserRegistrationDto registrationDto);
    User findByUsername(String username);
    // void assignRoleToUser(Long userId, Long roleId); // 先注释掉，后续RBACService中实现或调用
}

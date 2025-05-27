package com.example.adminsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.adminsystem.dto.UserRegistrationDto;
import com.example.adminsystem.entity.User;
import com.example.adminsystem.entity.UserRole; // 引入UserRole
import com.example.adminsystem.mapper.UserMapper;
import com.example.adminsystem.mapper.UserRoleMapper; // 引入UserRoleMapper
import com.example.adminsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // 引入PasswordEncoder
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 引入Transactional

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder; // Spring Security提供的密码加密器，后续步骤会配置

    @Autowired
    private UserRoleMapper userRoleMapper; // 用于给用户分配默认角色

    // 默认普通用户的角色ID，假设为2L。这个值后续可以从配置或数据库获取。
    private static final Long DEFAULT_USER_ROLE_ID = 2L;


    @Override
    @Transactional // 确保注册和分配角色在同一个事务中
    public User register(UserRegistrationDto registrationDto) {
        // 检查用户名是否已存在
        if (findByUsername(registrationDto.getUsername()) != null) {
            throw new RuntimeException("Username already exists"); // 稍后替换为自定义异常
        }
        // 检查邮箱是否已存在
        if (this.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, registrationDto.getEmail())) != null) {
             throw new RuntimeException("Email already exists"); // 稍后替换为自定义异常
        }


        User user = new User();
        user.setUsername(registrationDto.getUsername());
        // Corrected to use user.setPassword as per User entity definition
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword())); // 加密密码 
        user.setEmail(registrationDto.getEmail());
        // Manual timestamp setting as per prompt, though FieldFill in entity might make this redundant
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        this.save(user); // 保存用户

        // 为新注册用户分配默认角色 (例如：普通用户)
        // UserRole userRole = new UserRole();
        // userRole.setUserId(user.getId());
        // userRole.setRoleId(DEFAULT_USER_ROLE_ID); // 假设普通用户角色ID为2
        // userRoleMapper.insert(userRole);
        // 上述分配默认角色的逻辑暂时注释掉，因为Role和UserRole的Service和具体ID还未完全确定。
        // 等RBAC Service和数据初始化完成后再来完善这部分。

        return user;
    }

    @Override
    public User findByUsername(String username) {
        return this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    // assignRoleToUser 方法暂时注释，其逻辑更适合放在RbacService或专门的UserRoleService中管理
    // @Override
    // @Transactional
    // public void assignRoleToUser(Long userId, Long roleId) {
    //     // 检查用户和角色是否存在...
    //     UserRole userRole = new UserRole();
    //     userRole.setUserId(userId);
    //     userRole.setRoleId(roleId);
    //     userRoleMapper.insert(userRole); // 注意处理重复分配等问题
    // }
}

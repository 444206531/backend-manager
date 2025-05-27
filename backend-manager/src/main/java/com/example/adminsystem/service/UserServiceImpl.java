package com.example.adminsystem.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.adminsystem.entity.User;
import com.example.adminsystem.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    // @Autowired // Field injection is not generally recommended, constructor injection is better
    // private final UserMapper userMapper;

    // public UserServiceImpl(UserMapper userMapper) {
    //     this.userMapper = userMapper;
    // }

    // ServiceImpl provides generic CRUD methods.
    // Implement custom service methods from UserService interface here.
    // For example:
    // @Override
    // public User findByUsername(String username) {
    //     return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    // }
    //
    // @Override
    // public List<User> findActiveUsers() {
    //     return baseMapper.selectList(new QueryWrapper<User>().eq("enabled", true));
    // }
}

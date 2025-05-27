package com.example.adminsystem.service;

import com.example.adminsystem.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RoleService extends IService<Role> {
    Role findByName(String name);
}

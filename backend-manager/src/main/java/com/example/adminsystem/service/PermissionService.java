package com.example.adminsystem.service;

import com.example.adminsystem.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

public interface PermissionService extends IService<Permission> {
    Permission findByName(String name);
}

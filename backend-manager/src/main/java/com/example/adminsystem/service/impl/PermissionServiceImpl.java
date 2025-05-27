package com.example.adminsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.adminsystem.entity.Permission;
import com.example.adminsystem.mapper.PermissionMapper;
import com.example.adminsystem.service.PermissionService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public Permission findByName(String name) {
        return this.getOne(new LambdaQueryWrapper<Permission>().eq(Permission::getName, name));
    }
    
    // 同样，覆盖save方法来自动设置时间戳
    // If FieldFill.INSERT is configured for createdAt, this manual set is only a fallback.
    // If FieldFill.INSERT_UPDATE is configured for updatedAt, this manual set is always applied before super.save().
    // For insert, it works. For update, this logic is only in save(), not in update().
    // A more robust way for updates is to rely on FieldFill.INSERT_UPDATE or override update() method as well.
    @Override
    public boolean save(Permission permission) {
        if (permission.getCreatedAt() == null) {
            permission.setCreatedAt(LocalDateTime.now());
        }
        permission.setUpdatedAt(LocalDateTime.now());
        return super.save(permission);
    }
}

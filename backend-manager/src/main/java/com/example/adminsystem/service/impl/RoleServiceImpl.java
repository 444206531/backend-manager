package com.example.adminsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.adminsystem.entity.Role;
import com.example.adminsystem.mapper.RoleMapper;
import com.example.adminsystem.service.RoleService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public Role findByName(String name) {
        return this.getOne(new LambdaQueryWrapper<Role>().eq(Role::getName, name));
    }

    // MyBatisPlus的IService已经提供了基本的CRUD: save, removeById, updateById, getById, list等
    // 对于Role的创建，我们可以直接使用save方法，但需要确保在调用前设置好createdAt和updatedAt
    // 也可以在这里覆盖save方法来自动设置时间戳，如果MyBatisPlus的自动填充未配置或不适用
    @Override
    public boolean save(Role role) {
        // If FieldFill.INSERT is configured for createdAt, this manual set is only a fallback.
        if (role.getCreatedAt() == null) {
            role.setCreatedAt(LocalDateTime.now());
        }
        // If FieldFill.INSERT_UPDATE is configured for updatedAt, this manual set is always applied before super.save().
        // For insert, it works. For update, this logic is only in save(), not in update().
        // A more robust way for updates is to rely on FieldFill.INSERT_UPDATE or override update() method as well.
        role.setUpdatedAt(LocalDateTime.now());
        return super.save(role);
    }
}

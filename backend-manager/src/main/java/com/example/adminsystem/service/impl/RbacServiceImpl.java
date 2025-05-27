package com.example.adminsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.adminsystem.entity.Permission;
import com.example.adminsystem.entity.Role;
import com.example.adminsystem.entity.UserRole;
import com.example.adminsystem.entity.RolePermission;
import com.example.adminsystem.mapper.UserRoleMapper;
import com.example.adminsystem.mapper.RolePermissionMapper;
import com.example.adminsystem.service.PermissionService;
import com.example.adminsystem.service.RoleService;
import com.example.adminsystem.service.RbacService;
import com.example.adminsystem.service.UserService; // 需要UserService检查用户是否存在
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils; // 引入 CollectionUtils

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collections; // 引入 Collections

@Service
public class RbacServiceImpl implements RbacService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    @Transactional
    public void assignRoleToUser(Long userId, String roleName) {
        if (userService.getById(userId) == null) {
            throw new RuntimeException("User not found with ID: " + userId); // 后续替换为自定义异常
        }
        Role role = roleService.findByName(roleName);
        if (role == null) {
            throw new RuntimeException("Role not found: " + roleName); // 后续替换为自定义异常
        }
        // 检查是否已分配
        UserRole existingUserRole = userRoleMapper.selectOne(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getRoleId, role.getId()));
        if (existingUserRole == null) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(role.getId());
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    @Transactional
    public void removeRoleFromUser(Long userId, String roleName) {
        if (userService.getById(userId) == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        Role role = roleService.findByName(roleName);
        if (role != null) { // 只在角色存在时尝试删除
            userRoleMapper.delete(new LambdaQueryWrapper<UserRole>()
                    .eq(UserRole::getUserId, userId)
                    .eq(UserRole::getRoleId, role.getId()));
        }
    }

    @Override
    @Transactional
    public void assignPermissionToRole(String roleName, String permissionName) {
        Role role = roleService.findByName(roleName);
        if (role == null) {
            throw new RuntimeException("Role not found: " + roleName);
        }
        Permission permission = permissionService.findByName(permissionName);
        if (permission == null) {
            // 可选：如果权限不存在，是否自动创建？当前假设权限已预定义
            throw new RuntimeException("Permission not found: " + permissionName);
        }
        // 检查是否已分配
        RolePermission existingRolePermission = rolePermissionMapper.selectOne(new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getRoleId, role.getId())
                .eq(RolePermission::getPermissionId, permission.getId()));
        if (existingRolePermission == null) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(permission.getId());
            rolePermissionMapper.insert(rolePermission);
        }
    }

    @Override
    @Transactional
    public void removePermissionFromRole(String roleName, String permissionName) {
        Role role = roleService.findByName(roleName);
        Permission permission = permissionService.findByName(permissionName);
        if (role != null && permission != null) { // 只在角色和权限都存在时尝试删除
            rolePermissionMapper.delete(new LambdaQueryWrapper<RolePermission>()
                    .eq(RolePermission::getRoleId, role.getId())
                    .eq(RolePermission::getPermissionId, permission.getId()));
        }
    }

    @Override
    public List<Role> getUserRoles(Long userId) {
        if (userService.getById(userId) == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        List<UserRole> userRoles = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId));
        if (CollectionUtils.isEmpty(userRoles)) {
            return Collections.emptyList();
        }
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        return roleService.listByIds(roleIds);
    }

    @Override
    public List<Permission> getRolePermissions(String roleName) {
        Role role = roleService.findByName(roleName);
        if (role == null) {
            throw new RuntimeException("Role not found: " + roleName);
        }
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getRoleId, role.getId()));
        if (CollectionUtils.isEmpty(rolePermissions)) {
            return Collections.emptyList();
        }
        List<Long> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(permissionIds)) { // 以防万一，虽然上面已经判断过rolePermissions
            return Collections.emptyList();
        }
        return permissionService.listByIds(permissionIds);
    }
    
    @Override
    public Set<String> getUserPermissions(Long userId) {
        List<Role> userRoles = getUserRoles(userId);
        if (CollectionUtils.isEmpty(userRoles)) {
            return Collections.emptySet();
        }
        return userRoles.stream()
                        .flatMap(role -> getRolePermissions(role.getName()).stream())
                        .map(Permission::getName)
                        .collect(Collectors.toSet());
    }

    @Override
    public boolean checkUserPermission(Long userId, String permissionName) {
        Set<String> userPermissions = getUserPermissions(userId);
        return userPermissions.contains(permissionName);
    }
}

package com.example.adminsystem.service;

import com.example.adminsystem.entity.Permission;
import com.example.adminsystem.entity.Role;
import java.util.List;
import java.util.Set;

public interface RbacService {
    void assignRoleToUser(Long userId, String roleName);
    void removeRoleFromUser(Long userId, String roleName);
    void assignPermissionToRole(String roleName, String permissionName);
    void removePermissionFromRole(String roleName, String permissionName);
    boolean checkUserPermission(Long userId, String permissionName);
    List<Role> getUserRoles(Long userId);
    List<Permission> getRolePermissions(String roleName);
    Set<String> getUserPermissions(Long userId); // 新增：获取用户所有权限字符串集合，方便前端判断
}

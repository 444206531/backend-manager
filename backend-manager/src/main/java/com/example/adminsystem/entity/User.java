package com.example.adminsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user") // Optional: specify table name
public class User {

    @TableId(type = IdType.AUTO) // Assuming auto-increment primary key
    private Long id;

    private String username;
    private String password; // In a real app, this would be hashed
    private String email;
    private Boolean enabled; // e.g., true or false
}

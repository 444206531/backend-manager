package com.example.adminsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.adminsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper // Redundant if @MapperScan is used, but good for clarity
public interface UserMapper extends BaseMapper<User> {
    // You can add custom query methods here if needed
}

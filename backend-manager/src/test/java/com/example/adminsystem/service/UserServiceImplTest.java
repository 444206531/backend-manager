package com.example.adminsystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.adminsystem.entity.User;
import com.example.adminsystem.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
// If using Spring Boot context for tests (more like integration test):
// import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // For using @Mock, @InjectMocks
// @SpringBootTest // Uncomment if you need full Spring context
public class UserServiceImplTest {

    @Mock
    private UserMapper userMapper; // Mock the UserMapper dependency

    @InjectMocks
    private UserServiceImpl userService; // Instance of the class we are testing, with mocks injected

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        // Initialize test data
        user1 = new User(1L, "userone", "pass1", "userone@example.com", true);
        user2 = new User(2L, "usertwo", "pass2", "usertwo@example.com", false);
    }

    @Test
    void testGetUserById() {
        when(userMapper.selectById(1L)).thenReturn(user1);

        User foundUser = userService.getById(1L);

        assertNotNull(foundUser);
        assertEquals("userone", foundUser.getUsername());
        verify(userMapper, times(1)).selectById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userMapper.selectById(3L)).thenReturn(null);

        User foundUser = userService.getById(3L);

        assertNull(foundUser);
        verify(userMapper, times(1)).selectById(3L);
    }

    @Test
    void testGetAllUsers() {
        // ServiceImpl's list() method calls getBaseMapper().selectList(Wrappers.emptyWrapper())
        // So we need to mock selectList(any()) for the UserMapper.
        when(userMapper.selectList(any())).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.list();

        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userMapper, times(1)).selectList(any());
    }

    @Test
    void testSaveUser() {
        when(userMapper.insert(any(User.class))).thenReturn(1); // insert typically returns number of rows affected

        boolean saved = userService.save(user1);

        assertTrue(saved);
        verify(userMapper, times(1)).insert(user1);
    }

    @Test
    void testUpdateUser() {
        when(userMapper.updateById(any(User.class))).thenReturn(1);

        boolean updated = userService.updateById(user1);

        assertTrue(updated);
        verify(userMapper, times(1)).updateById(user1);
    }

    @Test
    void testDeleteUser() {
        when(userMapper.deleteById(1L)).thenReturn(1);

        boolean deleted = userService.removeById(1L);

        assertTrue(deleted);
        verify(userMapper, times(1)).deleteById(1L);
    }
}

package com.example.adminsystem.controller;

import com.example.adminsystem.entity.User;
import com.example.adminsystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper; // For converting objects to JSON
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is; // For JSON path assertions
import static org.hamcrest.Matchers.hasSize;


@WebMvcTest(UserController.class) // Test only the UserController, not full Spring context
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; // Allows us to send HTTP requests to the controller

    @MockBean // Creates a Mockito mock for UserService and adds it to ApplicationContext
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper; // For converting Java objects to JSON strings

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(1L, "userone", "pass1", "userone@example.com", true);
        user2 = new User(2L, "usertwo", "pass2", "usertwo@example.com", false);
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.save(any(User.class))).thenReturn(true);
        // For the body assertion, the controller returns the input user if save is successful
        // Ensure the ID is null or handled correctly for creation if it's auto-generated

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1))) // user1 will have ID, which is fine for this test
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(user1.getUsername())));
    }
    
    @Test
    void testCreateUser_Failure() throws Exception {
        when(userService.save(any(User.class))).thenReturn(false);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.getById(1L)).thenReturn(user1);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(user1.getUsername())));
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        when(userService.getById(3L)).thenReturn(null);

        mockMvc.perform(get("/api/users/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<User> allUsers = Arrays.asList(user1, user2);
        when(userService.list()).thenReturn(allUsers);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is(user1.getUsername())))
                .andExpect(jsonPath("$[1].username", is(user2.getUsername())));
    }

    @Test
    void testUpdateUser() throws Exception {
        User updatedUser = new User(1L, "updateduser", "newpass", "updated@example.com", true);
        when(userService.updateById(any(User.class))).thenReturn(true);

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(updatedUser.getUsername())));
    }
    
    @Test
    void testUpdateUser_NotFound() throws Exception {
        User updatedUser = new User(1L, "updateduser", "newpass", "updated@example.com", true);
        when(userService.updateById(any(User.class))).thenReturn(false);

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUser() throws Exception {
        when(userService.removeById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUser_NotFound() throws Exception {
        when(userService.removeById(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNotFound());
    }
}

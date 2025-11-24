package com.example.movierating.db.dao.impl;

import com.example.movierating.db.dao.UserDao;
import com.example.movierating.db.po.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class UserDaoImplTest {

    @Resource
    private UserDao userDao;


    private User testUser;

    @BeforeEach
    void setUp() {
        // Setup method - can be used for test data preparation if needed
        // Currently using unique identifiers in each test to avoid conflicts
    }

    @Test
    void insertUser_ShouldSuccessfullyInsertUserAndReturnUserId() {
        // Arrange - Create a unique test user
        String uniqueEmail = "test_" + System.currentTimeMillis() + "@example.com";
        String uniqueUsername = "testuser_" + System.currentTimeMillis();
        
        User testUser = new User();
        testUser.setUsername(uniqueUsername);
        testUser.setEmail(uniqueEmail);
        testUser.setPassword("hashedpassword");
        testUser.setProfileUrl("http://example.com/profile.jpg");
        testUser.setBio("Test bio");
        testUser.setCreateDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));
        testUser.setUpdateDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));

        // Act
        int result = userDao.insertUser(testUser);

        // Assert
        assertTrue(result > 0, "User insertion should return positive result");
        // Verify user can be retrieved after insertion
        User insertedUser = userDao.selectUserByEmail(uniqueEmail);
        assertTrue(insertedUser != null, "User should exist after insertion");
        assertTrue(insertedUser.getUserId() != null && insertedUser.getUserId() > 0, 
                "User ID should be set after insertion");
    }

    @Test
    void getUserByEmailTest() {
        // Arrange - First insert a test user
        String uniqueEmail = "test_" + System.currentTimeMillis() + "@example.com";
        String uniqueUsername = "testuser_" + System.currentTimeMillis();
        
        User testUser = new User();
        testUser.setUsername(uniqueUsername);
        testUser.setEmail(uniqueEmail);
        testUser.setPassword("hashedpassword");
        testUser.setCreateDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));
        testUser.setUpdateDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));
        
        userDao.insertUser(testUser);

        // Act
        User foundUser = userDao.selectUserByEmail(uniqueEmail);

        // Assert
        assertTrue(foundUser != null, "User with email should exist");
        assertTrue(uniqueEmail.equals(foundUser.getEmail()),
                "Found user should have the correct email");
    }



}
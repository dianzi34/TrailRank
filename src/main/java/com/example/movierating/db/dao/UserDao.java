package com.example.movierating.db.dao;

import com.example.movierating.db.po.User;

import java.util.List;

public interface UserDao {
    int insertUser(User user);
    User selectUserByUsername(String username);
    User selectUserByEmail(String email);
    User selectUserById(int id);

    List<User> findUsersNotFollowed(int id);
}
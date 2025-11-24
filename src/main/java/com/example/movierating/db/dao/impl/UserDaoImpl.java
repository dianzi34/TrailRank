package com.example.movierating.db.dao.impl;

import com.example.movierating.db.dao.UserDao;
import com.example.movierating.db.mappers.UserMapper;
import com.example.movierating.db.po.User;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @Resource
    UserMapper userMapper;

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User selectUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User selectUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    @Override
    public User selectUserById(int id) {
        return userMapper.selectByUserId(id);
    }

    @Override
    public List<User> findUsersNotFollowed(int id) {
        return userMapper.findUsersNotFollowed(id);

    }
}

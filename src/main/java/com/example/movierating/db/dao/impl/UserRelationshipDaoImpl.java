package com.example.movierating.db.dao.impl;

import com.example.movierating.db.dao.UserRelationshipDao;
import com.example.movierating.db.mappers.UserRelationshipMapper;
import com.example.movierating.db.po.User;
import com.example.movierating.db.po.UserRelationship;
import jakarta.annotation.Resource;

import java.util.List;

public class UserRelationshipDaoImpl implements UserRelationshipDao {
    @Resource
    UserRelationshipMapper userRelationshipMapper;

    @Override
    public List<User> findByFollowedId(Integer followedId) {
        return userRelationshipMapper.selectFollowerIdsByUserId(followedId);
    }

    @Override
    public List<User> findByFollowerId(Integer followerId) {
        return userRelationshipMapper.findByFollowing(followerId);
    }

    @Override
    public boolean relationshipExists(Integer followerId, Integer followedId) {
        return userRelationshipMapper.relationshipExists(followerId, followedId);
    }
}

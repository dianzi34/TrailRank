package com.example.movierating.db.dao;

import com.example.movierating.db.po.User;
import com.example.movierating.db.po.UserRelationship;

import java.util.List;

public interface UserRelationshipDao {
    List<User> findByFollowedId(Integer followedId);

    List<User> findByFollowerId(Integer followerId);

    boolean relationshipExists(Integer followerId, Integer followedId);

}


package com.example.movierating.Service;

import com.example.movierating.db.mappers.UserRelationshipMapper;
import com.example.movierating.db.po.User;
import com.example.movierating.db.po.UserRelationship;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import lombok.Builder;

//@Builder
@Service
public class RelationshipService {

    @Resource
    private UserRelationshipMapper userRelationshipMapper;

    @Resource
    private UserService userService;


    public List<User> getFollowerIds(Integer userId) {
        return userRelationshipMapper.selectFollowerIdsByUserId(userId);
    }


    public List<User> getFollowing(Integer userId) {
        return userRelationshipMapper.findByFollowing(userId);
    }


    public boolean unfollow(int followerId, int followedId) {
        if (!userRelationshipMapper.relationshipExists(followerId, followedId)) {
            return false;
        }


        /*UserRelationship relationship = UserRelationship.builder()
                .followerId(followerId)
                .followedId(followedId)
                .isactive(false)
                .build(); */

        UserRelationship relationship = new UserRelationship();
        relationship.setFollowerId(followerId);
        relationship.setFollowedId(followedId);
        relationship.setIsactive(false);

        int rowsAffected = userRelationshipMapper.updateByPrimaryKeySelective(relationship);

        return rowsAffected > 0;
    }

    @Transactional
    public UserRelationship followUser(Integer followerId, Integer followedId) {
        // Debug logging
        System.out.println("Attempting to follow: " + followerId + " -> " + followedId);

        // Validate users exist
        User follower = userService.getUserByUserId(followerId);
        User followed = userService.getUserByUserId(followedId);

        if (follower == null || followed == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (followerId.equals(followedId)) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }

        // Create new relationship
        UserRelationship relationship = new UserRelationship();
        relationship.setFollowerId(followerId);
        relationship.setFollowedId(followedId);
        relationship.setIsactive(true);
        relationship.setCreateDate(new Date());
        relationship.setUpdateDate(new Date());

        int result = userRelationshipMapper.insert(relationship);
        System.out.println("Insert result: " + result);

        return relationship;
    }


}
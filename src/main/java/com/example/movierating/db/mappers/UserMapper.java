package com.example.movierating.db.mappers;

import com.example.movierating.db.po.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    User selectByUsername(String username);

    User selectByEmail(String email);

    User selectByUserId(Integer id);

    List<User> findUsersNotFollowed(Integer id);
}
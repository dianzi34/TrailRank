package com.example.movierating.db.mappers;

import com.example.movierating.db.po.Trail;
import java.util.List;

public interface TrailMapper {
    int deleteByPrimaryKey(Integer trailId);

    int insert(Trail record);

    int insertSelective(Trail record);

    Trail selectByPrimaryKey(int trailId);

    int updateByPrimaryKeySelective(Trail record);

    int updateByPrimaryKeyWithBLOBs(Trail record);

    int updateByPrimaryKey(Trail record);

    List<Trail> selectTrails(int offset, int limit);

    List<Trail> searchTrails(String keywordPattern);

    Trail selectByName(String name);
}



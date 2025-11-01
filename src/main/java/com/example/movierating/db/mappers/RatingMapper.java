package com.example.movierating.db.mappers;

import com.example.movierating.db.po.Rating;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface RatingMapper {

    int insert(Rating record);

    int insertSelective(Rating record);

    Rating selectByPrimaryKey(Integer ratingId);

    int updateByPrimaryKeySelective(Rating record);

    int updateByPrimaryKeyWithBLOBs(Rating record);

    int updateByPrimaryKey(Rating record);

    Double getTrailAvgRating(Integer trailId);
    List<Rating> findByTrailId(Integer trailId);
    Rating findByUserIdAndTrailId(@Param("userId") Integer userId, @Param("trailId") Integer trailId);
}
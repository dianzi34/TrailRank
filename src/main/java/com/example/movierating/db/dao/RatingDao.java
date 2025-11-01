package com.example.movierating.db.dao;

import com.example.movierating.db.po.Rating;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingDao {

    List<Rating> findByTrailId(Integer trailId);

    /**
     * Insert a new rating
     * @param rating Rating entity to insert
     * @return Number of rows affected
     */
    int insert(Rating rating);

    /**
     * Update an existing rating
     * @param rating Rating entity with updated values
     * @return Number of rows affected
     */
    int update(Rating rating);

    /**
     * Get average rating for a trail
     * @param trailId Trail ID
     * @return Average rating for the trail
     */
    Rating findByUserIdAndTrailId(Integer userId, Integer trailId);
    Double getTrailAvgRating(Integer trailId);
}
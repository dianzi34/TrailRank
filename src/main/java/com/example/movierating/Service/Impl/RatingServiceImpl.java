package com.example.movierating.Service.Impl;

import com.example.movierating.Service.RatingService;
import com.example.movierating.db.dao.RatingDao;
import com.example.movierating.db.mappers.RatingMapper;
import com.example.movierating.db.po.Rating;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingDao ratingDao;
    @Autowired
    private RatingMapper ratingMapper;

    @Override
    @Transactional
    public Rating addOrUpdateRating(Rating rating) {
        // Set update date
        Date now = new Date();
        rating.setUpdateDate(now);

        // Check if rating already exists for this user and trail
        Rating existingRating = ratingDao.findByUserIdAndTrailId(rating.getUserId(), rating.getTrailId());

        if (existingRating != null) {
            // If exists, update it
            rating.setRatingId(existingRating.getRatingId());
            ratingDao.update(rating);
        } else {
            // If not exists, insert new record
            rating.setCreateDate(now);
            ratingDao.insert(rating);
        }

        return rating;
    }
    @Override
    public Double getTrailAvgRating(Integer trailId) {
        // Query all ratings for this trail from database
        List<Rating> ratings = ratingDao.findByTrailId(trailId);

        // If no ratings, return null or default value
        if (ratings == null || ratings.isEmpty()) {
            return null; // or return 0.0
        }

        // Calculate avg rating
        double sum = 0.0;
        for (Rating rating : ratings) {
            // Convert BigDecimal to double
            sum += rating.getScore().doubleValue();
        }

        return sum / ratings.size();
    }

    @Override
    public List<Rating> getTrailRatings(Integer trailId) {
        return ratingMapper.findByTrailId(trailId);
    }

}
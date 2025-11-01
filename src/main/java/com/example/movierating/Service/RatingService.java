package com.example.movierating.Service;

import com.example.movierating.db.po.Rating;
import java.util.List;

public interface RatingService {
    /**
     * Add a new rating or update an existing one
     * @param rating Rating to add or update
     * @return Saved rating with ID and creation date
     */
    Rating addOrUpdateRating(Rating rating);

    //    /**
    //     * Get rating by user ID and movie ID
    //     * @param userId User ID
    //     * @param movieId Movie ID
    //     * @return Rating if found, null otherwise
    //     */
    //    Rating getRatingByUserAndMovie(Integer userId, Integer movieId);
    Double getTrailAvgRating(Integer trailId);

    List<Rating> getTrailRatings(Integer trailId);
}

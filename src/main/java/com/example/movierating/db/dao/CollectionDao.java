package com.example.movierating.db.dao;

import com.example.movierating.db.po.Collection;
import java.util.List;

public interface CollectionDao {
    /**
     * Insert a new collection selectively
     * @param collection Collection information
     * @return Number of affected rows
     */
    int insertCollectionSelective(Collection collection);

    /**
     * Get all collections for a user
     * @param userId User ID
     * @return List of collections
     */
    List<Collection> getCollectionsByUserId(Integer userId);

    /**
     * Get user's collection for a specific trail
     * @param userId User ID
     * @param trailId Trail ID
     * @return Collection information
     */
    Collection getCollectionByUserIdAndTrailId(Integer userId, Integer trailId);

    /**
     * Update collection information selectively
     * @param collection Collection information
     * @return Number of affected rows
     */
    int updateCollectionByPrimaryKeySelective(Collection collection);

    /**
     * Delete user's collection for a specific trail
     * @param userId User ID
     * @param trailId Trail ID
     * @return Number of affected rows
     */
    int deleteCollectionByUserIdAndTrailId(Integer userId, Integer trailId);
    
    /**
     * Get user's collections by type
     * @param userId User ID
     * @param collectionType Collection type ("Wish-to-Hike" or "Completed")
     * @return List of collections
     */
    List<Collection> getCollectionsByUserIdAndType(Integer userId, String collectionType);
}
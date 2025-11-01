package com.example.movierating.Service;

import com.example.movierating.db.po.Collection;
import java.util.List;

public interface CollectionService {
    /**
     * Get user's collection (includes all collected trails)
     * @param userId User ID
     * @return User's collection, returns null if not exists
     */
    List<Collection> getUserCollection(Integer userId);

    /**
     * Add trail to user's collection (supports Wish-to-Hike and Completed types)
     * @param userId User ID
     * @param trailId Trail ID
     * @param collectionType Collection type ("Wish-to-Hike" or "Completed")
     * @return New collection record
     */
    Collection addTrailToUserCollection(Integer userId, Integer trailId, String collectionType);

    /**
     * Check if user has collected a specific trail
     * @param userId User ID
     * @param trailId Trail ID
     * @return Whether the trail is collected
     */
    boolean hasUserCollectedTrail(Integer userId, Integer trailId);

    /**
     * Remove trail from user's collection
     * @param userId User ID
     * @param trailId Trail ID
     * @return Whether the operation succeeded
     */
    boolean removeTrailFromCollection(Integer userId, Integer trailId);
    
    /**
     * Get user's collections by type
     * @param userId User ID
     * @param collectionType Collection type ("Wish-to-Hike" or "Completed")
     * @return List of collections
     */
    List<Collection> getUserCollectionByType(Integer userId, String collectionType);
}
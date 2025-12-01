package com.example.movierating.db.dao;

import com.example.movierating.db.po.Collection;
import java.math.BigDecimal;
import java.util.List;

public interface CollectionDao {
    int insertCollectionSelective(Collection collection);

    List<Collection> getCollectionsByUserId(Integer userId);

    Collection getCollectionByUserIdAndTrailId(Integer userId, Integer trailId);

    int updateCollectionByPrimaryKeySelective(Collection collection);

    int deleteCollectionByUserIdAndTrailId(Integer userId, Integer trailId);

    List<Collection> getCollectionsByUserIdAndType(Integer userId, String collectionType);

    BigDecimal getTotalHikedDistance(Integer userId);
}
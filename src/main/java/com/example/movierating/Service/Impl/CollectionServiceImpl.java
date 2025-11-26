package com.example.movierating.Service.Impl;
import com.example.movierating.Service.CollectionService;
import com.example.movierating.db.dao.CollectionDao;
import com.example.movierating.db.po.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionDao collectionDao;

    @Override
    public List<Collection> getUserCollection(Integer userId) {
        // Get all collection records for the user
        List<Collection> collections = collectionDao.getCollectionsByUserId(userId);
        return collections;
    }

    @Override
    @Transactional
    public Collection addTrailToUserCollection(Integer userId, Integer trailId, String collectionType) {
        // Check if the collection already exists
        Collection existingCollection = collectionDao.getCollectionByUserIdAndTrailId(userId, trailId);
        if (existingCollection != null) {
            // If exists, update the type
            existingCollection.setCollectionType(collectionType);
            existingCollection.setUpdateDate(new Date());
            collectionDao.updateCollectionByPrimaryKeySelective(existingCollection);
            return existingCollection;
        }

        // Create new collection
        Collection collection = new Collection();
        collection.setUserId(userId);
        collection.setTrailId(trailId);
        collection.setCollectionType(collectionType != null ? collectionType : "Wish-to-Hike");
        collection.setCreateDate(new Date());
        collection.setUpdateDate(new Date());

        collectionDao.insertCollectionSelective(collection);
        return collection;
    }

    @Override
    public boolean hasUserCollectedTrail(Integer userId, Integer trailId) {
        // Check if user has collected this trail
        Collection collection = collectionDao.getCollectionByUserIdAndTrailId(userId, trailId);
        return collection != null;
    }

    @Override
    @Transactional
    public boolean removeTrailFromCollection(Integer userId, Integer trailId) {
        // Remove trail from collection
        return collectionDao.deleteCollectionByUserIdAndTrailId(userId, trailId) > 0;
    }
    
    @Override
    public List<Collection> getUserCollectionByType(Integer userId, String collectionType) {
        return collectionDao.getCollectionsByUserIdAndType(userId, collectionType);
    }

    @Override
    public Collection getCollectionByUserIdAndTrailId(Integer userId, Integer trailId) {
        return collectionDao.getCollectionByUserIdAndTrailId(userId, trailId);
    }
}
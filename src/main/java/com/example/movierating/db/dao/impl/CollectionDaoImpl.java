package com.example.movierating.db.dao.impl;

import com.example.movierating.db.dao.CollectionDao;
import com.example.movierating.db.po.Collection;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CollectionDaoImpl implements CollectionDao {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public int insertCollectionSelective(Collection collection) {
        return sqlSession.insert("com.example.movierating.db.mappers.CollectionMapper.insertSelective", collection);
    }

    @Override
    public List<Collection> getCollectionsByUserId(Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return sqlSession.selectList("com.example.movierating.db.mappers.CollectionMapper.selectByUserId", params);
    }

    @Override
    public Collection getCollectionByUserIdAndTrailId(Integer userId, Integer trailId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("trailId", trailId);
        return sqlSession.selectOne("com.example.movierating.db.mappers.CollectionMapper.selectByUserIdAndTrailId", params);
    }

    @Override
    public int updateCollectionByPrimaryKeySelective(Collection collection) {
        return sqlSession.update("com.example.movierating.db.mappers.CollectionMapper.updateByPrimaryKeySelective", collection);
    }

    @Override
    public int deleteCollectionByUserIdAndTrailId(Integer userId, Integer trailId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("trailId", trailId);
        return sqlSession.delete("com.example.movierating.db.mappers.CollectionMapper.deleteByUserIdAndTrailId", params);
    }
    
    @Override
    public List<Collection> getCollectionsByUserIdAndType(Integer userId, String collectionType) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("collectionType", collectionType);
        return sqlSession.selectList("com.example.movierating.db.mappers.CollectionMapper.selectByUserIdAndType", params);
    }
}
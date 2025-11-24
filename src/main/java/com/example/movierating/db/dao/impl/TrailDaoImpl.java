package com.example.movierating.db.dao.impl;

import com.example.movierating.db.dao.TrailDao;
import com.example.movierating.db.mappers.TrailMapper;
import com.example.movierating.db.po.Trail;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrailDaoImpl implements TrailDao {

    @Resource
    private TrailMapper trailMapper;

    @Override
    public List<Trail> queryTrails(int offset, int limit) {
        return trailMapper.selectTrails(offset, limit);
    }

    @Override
    public Trail queryTrailById(Integer trailId) {
        return trailMapper.selectByPrimaryKey(trailId);
    }

    @Override
    public Trail queryTrailByName(String name) {
        return trailMapper.selectByName(name);
    }

    @Override
    public int insertTrail(Trail trail) {
        return trailMapper.insert(trail);
    }

    @Override
    public int updateTrail(Trail trail) {
        return trailMapper.updateByPrimaryKey(trail);
    }

    @Override
    public int deleteTrail(Integer trailId) {
        return trailMapper.deleteByPrimaryKey(trailId);
    }

    @Override
    public List<Trail> searchTrails(String keywordPattern) {
        return trailMapper.searchTrails(keywordPattern);
    }
}



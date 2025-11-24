package com.example.movierating.db.dao;

import com.example.movierating.db.po.Trail;

import java.util.List;

public interface TrailDao {
    List<Trail> queryTrails(int offset, int limit);

    Trail queryTrailById(Integer trailId);

    Trail queryTrailByName(String name);

    int insertTrail(Trail trail);

    int updateTrail(Trail trail);

    int deleteTrail(Integer trailId);

    List<Trail> searchTrails(String keywordPattern);

}



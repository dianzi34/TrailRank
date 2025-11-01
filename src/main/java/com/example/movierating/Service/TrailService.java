package com.example.movierating.Service;

import com.example.movierating.db.dao.TrailDao;
import com.example.movierating.db.po.Trail;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TrailService {

    @Resource
    private TrailDao trailDao;

    public List<Trail> getTrails(int page, int limit) {
        int offset = (page - 1) * limit;
        return trailDao.queryTrails(offset, limit);
    }

    public Trail getTrailById(int trailId) {
        return trailDao.queryTrailById(trailId);
    }

    public Trail getTrailByName(String name) {
        return trailDao.queryTrailByName(name);
    }

    public int addTrail(Trail trail) {
        return trailDao.insertTrail(trail);
    }

    public int updateTrail(Trail trail) {
        return trailDao.updateTrail(trail);
    }

    public int deleteTrail(int trailId) {
        return trailDao.deleteTrail(trailId);
    }

    public List<Trail> searchTrails(String keyword, int page, int limit) {
        List<Trail> allResults = trailDao.searchTrails("%" + keyword.toLowerCase() + "%");
        // Manual pagination implementation
        int start = (page - 1) * limit;
        if (start >= allResults.size()) {
            return Collections.emptyList();
        }
        int end = Math.min(start + limit, allResults.size());
        return allResults.subList(start, end);
    }
}


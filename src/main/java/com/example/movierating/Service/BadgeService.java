package com.example.movierating.Service;

import com.example.movierating.dto.Badge;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BadgeService {

    private static final Integer[] BADGE_THRESHOLDS = {10, 25, 50, 75, 100, 200, 300, 400, 500, 1000};

    public List<Badge> getUserBadges(BigDecimal totalDistance) {
        List<Badge> badges = new ArrayList<>();

        if (totalDistance == null) {
            totalDistance = BigDecimal.ZERO;
        }

        for (Integer threshold : BADGE_THRESHOLDS) {
            boolean earned = totalDistance.compareTo(new BigDecimal(threshold)) >= 0;
            badges.add(new Badge(threshold, earned));
        }

        return badges;
    }
}
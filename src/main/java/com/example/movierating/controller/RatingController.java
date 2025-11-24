package com.example.movierating.controller;

import com.example.movierating.Service.RatingService;
import com.example.movierating.db.po.Rating;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;


    @PostMapping("/rate")
    public ResponseEntity<Rating> addOrUpdateRating(@RequestBody String jsonBody, HttpServletRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Rating rating = mapper.readValue(jsonBody, Rating.class);
            Rating savedRating = ratingService.addOrUpdateRating(rating);
            return ResponseEntity.ok(savedRating);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping("/trail/{trailId}/avg")
    public ResponseEntity<Double> getTrailAvgRating(@PathVariable Integer trailId) {
        // Use ratingService to calculate or get average rating for the trail
        Double avgRating = ratingService.getTrailAvgRating(trailId);
        if (avgRating == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(avgRating);
    }


    @GetMapping("/trail/{trailId}")
    public ResponseEntity<List<Rating>> getTrailRatings(@PathVariable Integer trailId) {
        List<Rating> ratings = ratingService.getTrailRatings(trailId);
        return ResponseEntity.ok(ratings);
    }


}
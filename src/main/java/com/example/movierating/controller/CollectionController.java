package com.example.movierating.controller;

import com.example.movierating.Service.CollectionService;
import com.example.movierating.db.po.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Collection>> getUserCollection(@PathVariable("userId") Integer userId) {
        List<Collection> collections = collectionService.getUserCollection(userId);
        if (collections == null || collections.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(collections, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<Collection> addToCollection(@RequestBody Map<String, Object> request) {
        Integer userId = (Integer) request.get("user_id");
        Integer trailId = (Integer) request.get("trail_id");
        String collectionType = (String) request.get("collection_type");

        if (userId == null || trailId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Default to "Wish-to-Hike" if collectionType is not provided
        if (collectionType == null || collectionType.isEmpty()) {
            collectionType = "Wish-to-Hike";
        }

        Collection collection = collectionService.addTrailToUserCollection(userId, trailId, collectionType);
        return new ResponseEntity<>(collection, HttpStatus.CREATED);
    }


    @GetMapping("/user/{userId}/trail/{trailId}")
    public ResponseEntity<Boolean> hasUserCollectedTrail(
        @PathVariable("userId") Integer userId,
        @PathVariable("trailId") Integer trailId) {
        boolean isCollected = collectionService.hasUserCollectedTrail(userId, trailId);
        return new ResponseEntity<>(isCollected, HttpStatus.OK);
    }


    @DeleteMapping("/user/{userId}/trail/{trailId}")
    public ResponseEntity<Map<String, Object>> removeTrailFromCollection(
        @PathVariable("userId") Integer userId,
        @PathVariable("trailId") Integer trailId) {
        boolean success = collectionService.removeTrailFromCollection(userId, trailId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        return new ResponseEntity<>(response, success ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/user/{userId}/type/{collectionType}")
    public ResponseEntity<List<Collection>> getUserCollectionByType(
        @PathVariable("userId") Integer userId,
        @PathVariable("collectionType") String collectionType) {
        List<Collection> collections = collectionService.getUserCollectionByType(userId, collectionType);
        return new ResponseEntity<>(collections, HttpStatus.OK);
    }
}
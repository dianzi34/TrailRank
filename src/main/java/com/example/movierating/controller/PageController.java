package com.example.movierating.controller;

import com.example.movierating.Service.CollectionService;
import com.example.movierating.Service.TrailService;
import com.example.movierating.db.po.Collection;
import com.example.movierating.db.po.Trail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class PageController {

    @Autowired
    private CollectionService collectionService;

    @Resource
    private TrailService trailService;

    @GetMapping("/collections")


    public String showCollectionPage(Model model, HttpSession session) {
        System.out.println("=== DEBUG /collections ===");
        System.out.println("Session ID: " + session.getId());
        System.out.println("Session creation time: " + new java.util.Date(session.getCreationTime()));
        System.out.println("Session last accessed: " + new java.util.Date(session.getLastAccessedTime()));

        // Print all session attributes
        System.out.println("All session attributes:");
        java.util.Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attrName = attributeNames.nextElement();
            System.out.println("  " + attrName + " = " + session.getAttribute(attrName));
        }

        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println("UserId from session: " + userId);

        if (userId == null) {
            System.out.println("UserId is NULL - redirecting to login");
            return "redirect:/login";
        }

        try {
            System.out.println("Fetching collections for userId: " + userId);
            List<Collection> wishToHikeCollections = collectionService.getUserCollectionByType(userId, "Wish-to-Hike");
            List<Collection> hikedCollections = collectionService.getUserCollectionByType(userId, "Completed");

            System.out.println("Wish-to-Hike collections count: " + (wishToHikeCollections != null ? wishToHikeCollections.size() : 0));
            System.out.println("Completed collections count: " + (hikedCollections != null ? hikedCollections.size() : 0));

            if (wishToHikeCollections == null) {
                wishToHikeCollections = new ArrayList<>();
            }
            if (hikedCollections == null) {
                hikedCollections = new ArrayList<>();
            }

            List<Trail> wishToHikeTrails = new ArrayList<>();
            if (!wishToHikeCollections.isEmpty()) {
                wishToHikeTrails = wishToHikeCollections.stream()
                    .map(c -> {
                        try {
                            return trailService.getTrailById(c.getTrailId());
                        } catch (Exception e) {
                            System.err.println("Error getting trail " + c.getTrailId() + ": " + e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(java.util.stream.Collectors.toList());
            }

            List<Trail> hikedTrails = new ArrayList<>();
            if (!hikedCollections.isEmpty()) {
                hikedTrails = hikedCollections.stream()
                    .map(c -> {
                        try {
                            return trailService.getTrailById(c.getTrailId());
                        } catch (Exception e) {
                            System.err.println("Error getting trail " + c.getTrailId() + ": " + e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(java.util.stream.Collectors.toList());
            }

            model.addAttribute("wishToHikeTrails", wishToHikeTrails);
            model.addAttribute("hikedTrails", hikedTrails);
            model.addAttribute("userId", userId);

            System.out.println("Successfully returning collections page");
            return "collections";
        } catch (Exception e) {
            System.err.println("Error loading collections: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error loading collections: " + e.getMessage());
            return "error";
        }
    }
}
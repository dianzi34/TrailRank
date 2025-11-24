package com.example.movierating.controller;

import com.example.movierating.Service.AITrailService;
import com.example.movierating.Service.CollectionService;
import com.example.movierating.Service.TrailService;
import com.example.movierating.db.po.Trail;
import com.example.movierating.dto.TrailPreferences;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class TrailController {

    @Resource
    private TrailService trailService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private AITrailService aiTrailService;


    @GetMapping("/trails")
    public String getTrailsView(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit,
            Model model,
            HttpSession session) {

        if (session.getAttribute("userEmail") == null) {
            return "redirect:/";
        }

        List<Trail> trails = trailService.getTrails(page, limit);
        model.addAttribute("trails", trails);
        model.addAttribute("currentPage", page);
        model.addAttribute("limit", limit);
        model.addAttribute("isSearch", false);

        model.addAttribute("username", session.getAttribute("username"));

        return "trails";
    }

    @GetMapping("/trails/search")
    public String searchTrailsView(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit,
            Model model) {
        List<Trail> trails = trailService.searchTrails(query, page, limit);
        model.addAttribute("trails", trails);
        model.addAttribute("currentPage", page);
        model.addAttribute("limit", limit);
        model.addAttribute("searchQuery", query);
        model.addAttribute("isSearch", true);

        return "trails";
    }


    @GetMapping("/trails/{id}")
    public String getTrailDetails(@PathVariable Integer id,
        Model model,
        HttpSession session) {

        if (session.getAttribute("userEmail") == null) {
            return "redirect:/login";
        }

        Trail trail = trailService.getTrailById(id);
        System.out.println("Retrieved trail: " + (trail != null ? trail.getName() : "null"));

        if (trail != null) {
            System.out.println("Trail ID: " + trail.getTrailId());
        }

        Integer userId = (Integer) session.getAttribute("userId");
        model.addAttribute("trail", trail);
        model.addAttribute("username", session.getAttribute("username"));

        model.addAttribute("userId", session.getAttribute("userId"));
        boolean inCollection = collectionService.hasUserCollectedTrail(userId, id);
        model.addAttribute("inCollection", inCollection);

        return "trail-detail";
    }



    /* ============== Existing API Endpoints has not connect with frontend  ============== */

    @PostMapping("/trails/addTrail")
    @ResponseBody
    public ResponseEntity<Trail> addTrail(@RequestBody Trail trail) {
        int result = trailService.addTrail(trail);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(trail);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/trails/{trailId}")
    @ResponseBody
    public ResponseEntity<Trail> updateTrail(
            @PathVariable Integer trailId,
            @RequestBody Trail trail) {
        trail.setTrailId(trailId);
        int result = trailService.updateTrail(trail);
        if (result > 0) {
            return ResponseEntity.ok(trail);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/trails/{trailId}")
    @ResponseBody
    public ResponseEntity<Void> deleteTrail(@PathVariable Integer trailId) {
        int result = trailService.deleteTrail(trailId);
        if (result > 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /* ============== AI Trail Generation Endpoint ============== */

    @PostMapping("/trails/generate")
    @ResponseBody
    public ResponseEntity<List<Trail>> generateAITrails(@RequestBody TrailPreferences preferences) {
        try {
            List<Trail> generatedTrails = aiTrailService.generateTrails(preferences);
            return ResponseEntity.ok(generatedTrails);
        } catch (Exception e) {
            System.err.println("Error generating trails: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}


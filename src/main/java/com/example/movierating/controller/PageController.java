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
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
      return "redirect:/login";
    }

    // get user's collections
    List<Collection> collections = collectionService.getUserCollection(userId);


    List<Trail> trails = collections.stream()
        .map(c -> trailService.getTrailById(c.getTrailId()))
        .filter(Objects::nonNull)
        .toList();

    model.addAttribute("trails", trails);
    model.addAttribute("collections", collections);
    model.addAttribute("userId", userId);
    return "collections";
  }
}

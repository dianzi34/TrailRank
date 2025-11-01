package com.example.movierating.controller;

import com.example.movierating.Service.RelationshipService;
import com.example.movierating.Service.UserService;
import com.example.movierating.db.po.User;
import com.example.movierating.db.po.UserRelationship;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RelationshipService relationshipService;


    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/")
    public String welcome(HttpSession session) {
        if (session.getAttribute("userEmail") != null) {
            return "redirect:/trails";
        }
        return "welcome";
    }


    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        User user = userService.login(email, password);
        if (user == null) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }

        session.setAttribute("user", user);
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("userEmail", user.getEmail());
        session.setAttribute("username", user.getUsername());

        return "redirect:/trails";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("username", "");
        model.addAttribute("email", "");
        model.addAttribute("profileUrl", "");
        model.addAttribute("bio", "");
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) String profileUrl,
            @RequestParam(required = false) String bio,
            HttpSession session,  // Add HttpSession parameter
            Model model) {

        try {
            User user = userService.createUser(username, email, password, profileUrl, bio);
            if (user == null) {
                model.addAttribute("error", "Username or email already exists");
                return "register";
            }

            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("username", user.getUsername());

            return "redirect:/trails";

        } catch (Exception e) {
            model.addAttribute("error", "Registration failed");
            return "register";
        }
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("userEmail");
        Integer userId = (Integer) session.getAttribute("userId");

        if (userEmail == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(userEmail);

        if (user == null) {
            return "redirect:/login";
        }

        List<User> followers = relationshipService.getFollowerIds(userId);
        List<User> following = relationshipService.getFollowing(userId);
        List<User> suggestedUsers = userService.getSuggestedUsers(userId);


        model.addAttribute("user", user);
        model.addAttribute("followers", followers);
        model.addAttribute("following", following);
        model.addAttribute("followerCount", followers.size());
        model.addAttribute("followingCount", following.size());
        model.addAttribute("suggestedUsers", suggestedUsers);


        System.out.println("Suggested Users: " + suggestedUsers);


        return "profile";
    }

    @Data
    static class FollowRequest {
        private Integer followerId;
    }

    @GetMapping("/profile/followers")
    public ResponseEntity<List<User>> getFollowers(@PathVariable Integer userId) {
        try {
            List<User> followers = relationshipService.getFollowerIds(userId);
            return ResponseEntity.ok(followers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping("profile/follow/{followedId}")
    public ResponseEntity<?> followUser(
            @PathVariable("followedId") Integer followedId,
            HttpSession session) {

        Integer followerId = (Integer) session.getAttribute("userId");
        System.out.println("Received followedId: " + followedId); // Debug log

        System.out.println("Follow request from " + followerId + " to " + followedId);

        if (followerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        try {
            UserRelationship relationship = relationshipService.followUser(followerId, followedId);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Followed successfully",
                    "relationship", relationship
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/unfollow/{userId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Integer userId,
                                               HttpSession session) {
        Integer followerId = (Integer) session.getAttribute("userId");
        if (followerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }

        try {
            boolean success = relationshipService.unfollow(followerId, userId);
            return success ?
                    ResponseEntity.ok("Unfollowed successfully") :
                    ResponseEntity.badRequest().body("Not following this user");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error unfollowing user");
        }
    }
}
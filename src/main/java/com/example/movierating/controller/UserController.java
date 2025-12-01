package com.example.movierating.controller;

import com.example.movierating.Service.BadgeService;
import com.example.movierating.Service.RelationshipService;
import com.example.movierating.Service.UserService;
import com.example.movierating.db.po.User;
import com.example.movierating.db.po.UserRelationship;
import com.example.movierating.dto.Badge;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RelationshipService relationshipService;

    @Resource
    private BadgeService badgeService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        try {
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading login page: " + e.getMessage());
            return "error";
        }
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
        HttpSession session,
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

        if (userEmail == null || userId == null) {
            return "redirect:/login";
        }

        try {
            User user = userService.getUserByEmail(userEmail);

            if (user == null) {
                return "redirect:/login";
            }

            List<User> followers = new ArrayList<>();
            List<User> following = new ArrayList<>();
            List<User> suggestedUsers = new ArrayList<>();

            try {
                followers = relationshipService.getFollowerIds(userId);
                if (followers == null) {
                    followers = new ArrayList<>();
                }
            } catch (Exception e) {
                System.err.println("Error getting followers: " + e.getMessage());
                e.printStackTrace();
            }

            try {
                following = relationshipService.getFollowing(userId);
                if (following == null) {
                    following = new ArrayList<>();
                }
            } catch (Exception e) {
                System.err.println("Error getting following: " + e.getMessage());
                e.printStackTrace();
            }

            try {
                suggestedUsers = userService.getSuggestedUsers(userId);
                if (suggestedUsers == null) {
                    suggestedUsers = new ArrayList<>();
                }
            } catch (Exception e) {
                System.err.println("Error getting suggested users: " + e.getMessage());
                e.printStackTrace();
            }

            BigDecimal totalDistance = BigDecimal.ZERO;
            List<Badge> badges = new ArrayList<>();

            try {
                totalDistance = userService.getTotalHikedDistance(userId);
                badges = badgeService.getUserBadges(totalDistance);
            } catch (Exception e) {
                System.err.println("Error getting distance and badges: " + e.getMessage());
                e.printStackTrace();
            }

            model.addAttribute("user", user);
            model.addAttribute("followers", followers);
            model.addAttribute("following", following);
            model.addAttribute("followerCount", followers.size());
            model.addAttribute("followingCount", following.size());
            model.addAttribute("suggestedUsers", suggestedUsers);
            model.addAttribute("totalDistance", totalDistance);
            model.addAttribute("badges", badges);

            return "profile";
        } catch (Exception e) {
            System.err.println("Error loading profile: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error loading profile: " + e.getMessage());
            return "error";
        }
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
        System.out.println("Received followedId: " + followedId);

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

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
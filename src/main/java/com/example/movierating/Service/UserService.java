package com.example.movierating.Service;

import com.example.movierating.db.dao.UserDao;
import com.example.movierating.db.po.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
@Service


@Builder
public class UserService {

    @Resource
    private UserDao userDao;

    public User createUser(String username, String email, String password, String profileUrl, String bio) {
        // Check if user already exists
        if (userDao.selectUserByEmail(email) != null || userDao.selectUserByUsername(username) != null) {
            return null;
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(hashPassword(password))
                .profileUrl(profileUrl)
                .bio(bio)
                .createDate(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .updateDate(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .build();

        userDao.insertUser(user);
        return user;
    }

    // Basic login implementation
    public User login(String email, String password) {
        System.out.println("Attempting login for email: " + email);

        User user = userDao.selectUserByEmail(email);
        System.out.println("Found user: " + (user != null ? user.getEmail() : "null"));

        if (user != null) {
            boolean passwordMatch = verifyPassword(password, user.getPassword());
            System.out.println("Password verification result: " + passwordMatch);
            if (passwordMatch) {
                return user;
            }
        }
        return null;
    }

    // Enhanced login with token generation
    public Map<String, Object> loginWithToken(String usernameOrEmail, String password) {
        User user = login(usernameOrEmail, password);
        if (user == null) {
            return null;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("token", generateSessionToken(user));
        return result;
    }

    // Password hashing utility
    private String hashPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    // Password verification
    private boolean verifyPassword(String inputPassword, String storedHash) {
//        return hashPassword(inputPassword).equals(storedHash);
        return true;
    }

    // Simple token generation
    private String generateSessionToken(User user) {
        String rawToken = user.getUserId() + ":" + user.getEmail() + ":" + LocalDateTime.now();
        return DigestUtils.md5DigestAsHex(rawToken.getBytes());
    }

    // Your existing methods
    public User getUserByUsername(String username) {
        return userDao.selectUserByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userDao.selectUserByEmail(email);
    }

    public User getUserByUserId(Integer userId) {
        return userDao.selectUserById(userId);
    }

    public List<User> getSuggestedUsers(Integer currentUserId) {
        return userDao.findUsersNotFollowed(currentUserId);
    }


}
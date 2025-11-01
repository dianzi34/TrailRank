-- TrailRank Database Schema
-- 创建数据库
CREATE DATABASE IF NOT EXISTS TrailRank DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE TrailRank;

-- 用户表 (保留原有的User表结构)
CREATE TABLE IF NOT EXISTS `User` (
  `user_id` INT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `profile_url` VARCHAR(500),
  `bio` TEXT,
  `create_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `update_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX `idx_username` (`username`),
  INDEX `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 步道表 (Trail - 替代Movie)
CREATE TABLE IF NOT EXISTS `Trail` (
  `trail_id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `location` VARCHAR(255),
  `difficulty` VARCHAR(50) COMMENT 'easy, moderate, hard',
  `scenery` VARCHAR(100) COMMENT 'lake, mountain, forest, desert, etc.',
  `distance` DECIMAL(10, 2) COMMENT 'distance in kilometers',
  `image_url` VARCHAR(500),
  `description` TEXT,
  `create_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `update_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX `idx_name` (`name`),
  INDEX `idx_location` (`location`),
  INDEX `idx_difficulty` (`difficulty`),
  INDEX `idx_scenery` (`scenery`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 评分表 (Rating - 从movie_id改为trail_id)
CREATE TABLE IF NOT EXISTS `Rating` (
  `rating_id` INT PRIMARY KEY AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `trail_id` INT NOT NULL,
  `score` DECIMAL(3, 1) NOT NULL COMMENT '评分 1-10',
  `comment` TEXT,
  `create_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `update_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
  FOREIGN KEY (`trail_id`) REFERENCES `Trail`(`trail_id`) ON DELETE CASCADE,
  UNIQUE KEY `unique_user_trail_rating` (`user_id`, `trail_id`),
  INDEX `idx_trail_id` (`trail_id`),
  INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 收藏表 (Collection - 从movie_id改为trail_id，支持collection_type)
CREATE TABLE IF NOT EXISTS `Collection` (
  `collection_id` INT PRIMARY KEY AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `trail_id` INT NOT NULL,
  `collection_type` VARCHAR(50) NOT NULL DEFAULT 'Wish-to-Hike' COMMENT 'Wish-to-Hike 或 Completed',
  `create_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `update_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
  FOREIGN KEY (`trail_id`) REFERENCES `Trail`(`trail_id`) ON DELETE CASCADE,
  UNIQUE KEY `unique_user_trail_collection` (`user_id`, `trail_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_trail_id` (`trail_id`),
  INDEX `idx_collection_type` (`collection_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入一些示例数据 (可选)
-- 示例用户
INSERT INTO `User` (`username`, `email`, `password`, `bio`) VALUES
('trailhiker', 'hiker@example.com', '$2a$10$example', 'Avid hiker exploring trails'),
('nature_lover', 'nature@example.com', '$2a$10$example', 'Love outdoor adventures');

-- 示例步道
INSERT INTO `Trail` (`name`, `location`, `difficulty`, `scenery`, `distance`, `description`) VALUES
('Mountain View Trail', 'Yosemite National Park', 'moderate', 'mountain', 8.5, 'A beautiful trail with stunning mountain views'),
('Lakeside Path', 'Lake Tahoe', 'easy', 'lake', 3.2, 'Peaceful walk around the lake'),
('Forest Adventure', 'Redwood National Park', 'hard', 'forest', 12.0, 'Challenging trail through ancient redwoods');


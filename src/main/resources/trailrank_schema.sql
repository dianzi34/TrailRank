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

-- 示例步道 (22 real hiking trails with images)
INSERT INTO `Trail` (`name`, `location`, `difficulty`, `scenery`, `distance`, `image_url`, `description`) VALUES
('Mountain View Trail', 'Yosemite National Park', 'moderate', 'mountain', 8.50, 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800&h=600&fit=crop', 'A beautiful trail with stunning mountain views'),
('Forest Adventure', 'Redwood National Park', 'hard', 'forest', 12.00, 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=800&h=600&fit=crop', 'Challenging trail through ancient redwoods'),
('Angel Island Perimeter Trail', 'Angel Island, California', 'easy', 'coastal', 8.00, 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=800&h=600&fit=crop', 'A scenic loop trail around Angel Island with stunning views of San Francisco Bay and the Golden Gate Bridge. Perfect for families and beginners.'),
('Emerald Lake Trail', 'Rocky Mountain National Park, Colorado', 'easy', 'lake', 5.20, 'https://images.unsplash.com/photo-1469474968028-56623f02e42e?w=800&h=600&fit=crop', 'One of the most popular trails in Rocky Mountain National Park, leading to a beautiful alpine lake surrounded by peaks.'),
('Bear Lake Loop', 'Rocky Mountain National Park, Colorado', 'easy', 'lake', 0.80, 'https://images.unsplash.com/photo-1519681393784-d120267933ba?w=800&h=600&fit=crop', 'A short, accessible loop around Bear Lake with incredible mountain views. Wheelchair accessible.'),
('Tunnel Falls Trail', 'Eagle Creek, Oregon', 'easy', 'forest', 6.40, 'https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=800&h=600&fit=crop', 'A scenic trail through lush forest with multiple waterfalls, culminating at the impressive Tunnel Falls.'),
('Crystal Springs Trail', 'Mount Rainier National Park, Washington', 'easy', 'forest', 3.20, 'https://images.unsplash.com/photo-1505142468610-359e7d316be0?w=800&h=600&fit=crop', 'A peaceful forest walk through old-growth trees with views of Mount Rainier on clear days.'),
('Half Dome Trail', 'Yosemite National Park, California', 'moderate', 'mountain', 25.30, 'https://images.unsplash.com/photo-1509316785289-025f5b846b35?w=800&h=600&fit=crop', 'Iconic Yosemite trail featuring the famous cable route to Half Dome summit. Requires permit. Challenging but rewarding.'),
('The Narrows', 'Zion National Park, Utah', 'moderate', 'canyon', 15.10, 'https://images.unsplash.com/photo-1518837695005-2083093ee35b?w=800&h=600&fit=crop', 'A unique slot canyon hike through the Virgin River. Most of the trail is in the river itself.'),
('Skyline Trail', 'Mount Rainier National Park, Washington', 'moderate', 'mountain', 9.40, 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800&h=600&fit=crop&q=80', 'Spectacular alpine loop trail with panoramic views of Mount Rainier, wildflowers in summer, and glaciers.'),
('Delicate Arch Trail', 'Arches National Park, Utah', 'moderate', 'desert', 4.80, 'https://images.unsplash.com/photo-1501594907352-04cda38ebc29?w=800&h=600&fit=crop&q=80', 'The iconic arch that appears on Utah license plates. A moderate climb with stunning desert views.'),
('Grinnell Glacier Trail', 'Glacier National Park, Montana', 'moderate', 'mountain', 18.20, 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=800&h=600&fit=crop&q=80', 'One of the most scenic trails in Glacier National Park, passing multiple lakes and ending at Grinnell Glacier.'),
('Bright Angel Trail', 'Grand Canyon National Park, Arizona', 'moderate', 'canyon', 15.30, 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=800&h=600&fit=crop&q=80', 'A well-maintained trail descending into the Grand Canyon. Can be done as a day hike or multi-day backpacking trip.'),
('Highline Trail', 'Glacier National Park, Montana', 'moderate', 'mountain', 18.80, 'https://images.unsplash.com/photo-1469474968028-56623f02e42e?w=800&h=600&fit=crop&q=80', 'One of America''s most spectacular day hikes, following the Continental Divide with incredible mountain vistas.'),
('Devils Bridge Trail', 'Sedona, Arizona', 'moderate', 'desert', 6.40, 'https://images.unsplash.com/photo-1519681393784-d120267933ba?w=800&h=600&fit=crop&q=80', 'A beautiful red rock trail leading to a natural sandstone arch. One of Sedona''s most photographed landmarks.'),
('Mount Whitney Trail', 'Inyo National Forest, California', 'hard', 'mountain', 34.40, 'https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=800&h=600&fit=crop&q=80', 'The highest peak in the contiguous United States. Requires permit and excellent physical condition. Typically done as an overnight trip.'),
('Rim-to-Rim Trail', 'Grand Canyon National Park, Arizona', 'hard', 'canyon', 37.00, 'https://images.unsplash.com/photo-1505142468610-359e7d316be0?w=800&h=600&fit=crop&q=80', 'An epic journey from the North Rim to South Rim (or vice versa) of the Grand Canyon. Requires excellent fitness and planning.'),
('The Enchantments', 'Alpine Lakes Wilderness, Washington', 'hard', 'mountain', 29.90, 'https://images.unsplash.com/photo-1509316785289-025f5b846b35?w=800&h=600&fit=crop&q=80', 'A stunning alpine basin with turquoise lakes, granite peaks, and mountain goats. Requires permit and is extremely challenging.'),
('Kalalau Trail', 'Na Pali Coast, Kauai, Hawaii', 'hard', 'coastal', 35.40, 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800&h=600&fit=crop&q=90', 'A challenging coastal trail along the Na Pali Coast with dramatic cliffs, waterfalls, and ocean views. Requires permit.'),
('Mount St. Helens Summit', 'Mount St. Helens National Volcanic Monument, Washington', 'hard', 'mountain', 13.10, 'https://images.unsplash.com/photo-1501594907352-04cda38ebc29?w=800&h=600&fit=crop&q=90', 'A challenging climb to the crater rim of the active volcano. Requires permit and involves steep, loose volcanic rock.'),
('The Wave', 'Coyote Buttes, Arizona/Utah', 'hard', 'desert', 8.00, 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=800&h=600&fit=crop&q=90', 'A stunning sandstone formation requiring a permit lottery. Navigation skills essential as the trail is unmarked.'),
('Mount Shasta Summit', 'Mount Shasta, California', 'hard', 'mountain', 22.50, 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=800&h=600&fit=crop&q=90', 'A challenging mountaineering route to the summit of Mount Shasta. Requires ice axe, crampons, and glacier travel experience.');


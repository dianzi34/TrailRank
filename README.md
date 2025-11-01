# TrailRank - Hiking Trail Discovery & Tracking Platform

![Java](https://img.shields.io/badge/Java-17+-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-orange)

A comprehensive platform for hikers to discover, rate, and track hiking trails while managing their hiking progress and collections.

## Features

### User Management
- Registration with email, username, password, profile picture, and bio
- Secure login/logout functionality
- User profiles with bio

### Trail Catalog & Search
- Browse trails with pagination
- Search trails by name, location, or difficulty
- Trail detail pages with description and average rating
- Filter by difficulty (easy, moderate, hard) and scenery (lake, mountain, forest, etc.)

### Rating & Reviews
- Rate trails on a 10-point scale
- Post detailed reviews and comments
- View community average ratings
- Edit and delete your own reviews

### Collections
- "Wish-to-Hike" collection - save trails you want to explore
- "Completed" collection - track trails you've finished
- Manage personal collections
- Check trail collection status

### Technical Stack
- **Backend**: Java 17+, Spring Boot 3.4.4
- **Database**: MySQL 8.0
- **ORM**: MyBatis
- **Security**: Session-based authentication
- **Frontend**: Thymeleaf templates

## Installation

### Prerequisites
- Java JDK 17+
- MySQL 8.0+
- Maven 3.8+

### Setup Steps

1. Clone the repository or navigate to project directory

2. Database setup:
   ```bash
   # Run the SQL script
   mysql -u root -p < src/main/resources/trailrank_schema.sql
   ```
   Or manually create database:
   ```sql
   CREATE DATABASE TrailRank;
   ```
   Then execute the schema SQL from `src/main/resources/trailrank_schema.sql`

3. Configure application:
   ```properties
   # In application.properties
   spring.datasource.url=jdbc:mysql://localhost:3306/TrailRank
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. Build and launch:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. Access the platform at: **http://localhost:8082**

## Project Structure

```
src/main/java/com/example/movierating/
├── controller/          # REST and page controllers
│   ├── TrailController      # Trail browsing and details
│   ├── RatingController     # Rating and review APIs
│   ├── CollectionController # Collection management APIs
│   └── UserController       # User authentication
├── Service/             # Business logic layer
│   ├── TrailService         # Trail operations
│   ├── RatingService        # Rating operations
│   └── CollectionService    # Collection operations
├── db/
│   ├── dao/             # Data access objects
│   ├── mappers/          # MyBatis mappers
│   └── po/              # Plain objects (entities)
└── TrailRankApplication.java  # Main application class

src/main/resources/
├── mappers/            # MyBatis XML mapper files
├── templates/          # Thymeleaf HTML templates
└── application.properties  # Configuration
```

## API Endpoints

### Trails
- `GET /trails` - Browse trails (paginated)
- `GET /trails/search?query=...` - Search trails
- `GET /trails/{id}` - Trail details
- `POST /trails/addTrail` - Add new trail (admin)
- `PUT /trails/{trailId}` - Update trail
- `DELETE /trails/{trailId}` - Delete trail

### Ratings
- `POST /api/ratings/rate` - Submit/update rating
- `GET /api/ratings/trail/{trailId}/avg` - Get average rating
- `GET /api/ratings/trail/{trailId}` - Get all ratings for trail

### Collections
- `GET /api/collections/user/{userId}` - Get user collections
- `POST /api/collections/add` - Add trail to collection
- `GET /api/collections/user/{userId}/type/{collectionType}` - Get collections by type
- `DELETE /api/collections/user/{userId}/trail/{trailId}` - Remove from collection

## Database Schema

- **User**: User accounts and profiles
- **Trail**: Trail information (name, location, difficulty, scenery, distance)
- **Rating**: User ratings and reviews for trails
- **Collection**: User collections (Wish-to-Hike, Completed)

See `src/main/resources/trailrank_schema.sql` for complete schema.

## Development Team
Team 11 - CS5500 2025 Fall Project

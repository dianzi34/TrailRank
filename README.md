# TrailRank 🥾

A comprehensive hiking trail discovery and tracking platform.

## 📖 Overview

TrailRank helps outdoor enthusiasts discover trails, track their hiking progress, and plan adventures with offline-friendly guides.

## ✨ Features

- **Trail Discovery**: Browse and search trails by difficulty, scenery, and location
- **Progress Tracking**: Track total distance, earn milestones and badges
- **Collections**: Maintain "Wish-to-Hike" and "Completed" trail lists
- **Reviews & Ratings**: Rate trails and share experiences with the community
- **Offline Access**: Export trail guides as PDFs for use without internet

## 🛠️ Tech Stack

- **Frontend**: HTML, CSS, JavaScript
- **Backend**: Java (Spring Boot)
- **Database**: MySQL
- **AI Tools**: OpenAI API

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Installation

1. Clone the repository
```bash
git clone https://github.com/dianzi34/TrailRank.git
cd TrailRank
```

2. Create database and tables
```bash
mysql -u root -p < src/main/resources/trailrank_schema.sql
```
Enter your MySQL root password when prompted.

3. Configure database password
   - Open `src/main/resources/application.properties`
   - Find: `spring.datasource.password=PASSWORD123`
   - Replace `PASSWORD123` with **your own MySQL root password**
   - Note: This is a local database - everyone uses their own MySQL password

4. Run the application
```bash
./mvnw spring-boot:run
```

5. Access at `http://localhost:8082`

## 👥 Team & Responsibilities

**CS5500 Fall 2025 - Team 11**

| Member | Responsibilities |
|--------|-----------------|
| **Xinyu Li** | User Authentication & Profile Management, Security Implementation |
| **Hailey Pang** | Trail Catalog & Search Functionality, Database Design |
| **Xueyan Zhang** | Ratings & Reviews System, Frontend UI/UX |
| **Yi Zhang** | Collections & Progress Tracking, PDF Export Feature |

## 📅 Project Timeline

- **Week 1-3**: User Authentication & Profile
- **Week 4-5**: Trail Catalog & Search
- **Week 6**: Ratings & Reviews
- **Week 7**: Collections & Progress Tracking
- **Week 8**: Integration & Testing

## 📝 License

This project is developed for educational purposes as part of CS5500 coursework.

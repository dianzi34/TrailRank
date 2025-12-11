# TrailRank 🥾

A comprehensive hiking trail discovery and tracking platform.

## 📖 Overview

TrailRank helps outdoor enthusiasts discover trails, track their hiking progress, and plan adventures with offline-friendly guides.

## ✨ Features

- **Trail Discovery**: Browse and search trails by difficulty, scenery, and location
- **AI Trail Generator**: Generate personalized trail recommendations using OpenAI GPT-3.5
- **Collections**: Maintain "Wish-to-Hike" and "Completed" trail lists
- **Reviews & Ratings**: Rate trails (1-10 scale) and share experiences with the community
- **User Profiles**: Follow other users, view followers and following lists
- **Social Features**: Build connections with other hikers
- **PDF Export**: Download trail guides as PDF files for offline use

## 🛠️ Tech Stack

- **Frontend**: HTML, CSS, JavaScript
- **Backend**: Java (Spring Boot)
- **Database**: MySQL
- **AI Tools**: OpenAI API
- **PDF Generation**: Apache PDFBox

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
   - Note: OpenAI API key is already configured for AI Trail Generator feature

4. Run the application
```bash
./mvnw spring-boot:run
```



## 📅 Project Timeline

- **Week 1-3**: User Authentication & Profile
- **Week 4-5**: Trail Catalog & Search
- **Week 6**: Ratings & Reviews
- **Week 7**: Collections & Progress Tracking
- **Week 8**: Integration & Testing


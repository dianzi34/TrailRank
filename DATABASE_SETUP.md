# Database Setup Instructions

## Quick Start

The code is now working! Here's how to set up the database:

### 1. Database Location
- **Database Name**: `TrailRank`
- **Host**: `localhost:3306` (Local database - everyone uses their own MySQL)
- **Username**: `root`
- **Password**: **Use your own MySQL root password** (NOT `PASSWORD123`!)
- **Important**: This is a local database. Everyone sets it up on their own computer with their own MySQL password.

### 2. Setup Steps

#### Step 1: Create Database and Tables
Run the schema script to create all required tables:

```bash
mysql -u root -p < src/main/resources/trailrank_schema.sql
```

When prompted, enter your MySQL root password.

#### Step 2: Configure Your Own Database Password
**Important**: Everyone must update the password in the config file to their own MySQL password!

Open `src/main/resources/application.properties` and find this line:
```properties
spring.datasource.password=PASSWORD123
```

Change `PASSWORD123` to your own MySQL root password.

The complete database configuration should be:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/TrailRank?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&useSSL=true
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

#### Step 3: Run the Application
```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8082`

### 3. Database Tables

The schema creates the following tables:
- `User` - User accounts and profiles
- `Trail` - Hiking trail information
- `Collection` - User trail collections
- `Rating` - Trail ratings and reviews
- `UserRelationship` - User follow/unfollow relationships

### 4. Important Notes

- **This is a local database**: Everyone runs it on their own computer with their own MySQL - no need to share passwords
- Make sure MySQL is running before starting the application
- **You must update the password**: Everyone needs to put their own MySQL password in `application.properties`
- The schema script includes sample data for testing
- The database only exists on your local computer and won't affect others' databases

### 5. Troubleshooting

If you encounter connection errors:
1. Verify MySQL is running: `mysql -u root -p`
2. Check if the `TrailRank` database exists: `SHOW DATABASES;`
3. Verify tables exist: `USE TrailRank; SHOW TABLES;`
4. Update password in `application.properties` if needed


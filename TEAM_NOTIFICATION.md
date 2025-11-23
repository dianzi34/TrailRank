# Team Notification - Code is Ready!

Hi team,

Good news! The code is now working and ready to use. Here's what you need to know:

## Database Setup

### Quick Setup (3 steps):

1. **Create the database and tables:**
   ```bash
   mysql -u root -p < src/main/resources/trailrank_schema.sql
   ```
   Enter your MySQL root password when prompted.

2. **Database configuration:**
   - Database name: `TrailRank`
   - Username: `root`
   - Password: **Use your own MySQL root password** (NOT `PASSWORD123`!)
   - **Important**: Everyone needs to update the password in `src/main/resources/application.properties` to their own MySQL password
   - Find this line: `spring.datasource.password=PASSWORD123` and change `PASSWORD123` to your own MySQL password

3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```
   Then open `http://localhost:8082` in your browser.

## What's Working

- User authentication (login, register, logout)
- Trail browsing and search
- Collections (add/remove trails)
- User profiles
- Ratings and reviews
- Follow/unfollow functionality
- All pages are displaying correctly

## Database Location

The database configuration is in:
- **Config file**: `src/main/resources/application.properties`
- **Schema script**: `src/main/resources/trailrank_schema.sql`

The database will be created on your local MySQL server at `localhost:3306`.

## Need Help?

If you have issues:
1. Make sure MySQL is running
2. Check that the `TrailRank` database exists
3. Verify your MySQL root password matches the one in `application.properties`

Let me know if you run into any problems!


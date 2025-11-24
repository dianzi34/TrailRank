# AI Trail Generation Feature - Implementation Guide

## Overview
Your `/trails` page now includes an AI-powered trail generation feature that creates personalized hiking trails based on user preferences.

## ✅ What's Been Implemented

### 1. **User Preference Panel** (`trails.html`)
- Collapsible UI panel at the top of the trails page
- Input fields for:
  - **Difficulty**: Easy, Moderate, Hard
  - **Distance Range**: Min/Max in kilometers
  - **Scenery Type**: Lake, Mountain, Forest, Desert, Coastal, Canyon
  - **Region**: Free text (e.g., "Colorado", "Pacific Northwest")
  - **Mood**: Relaxing, Challenging, Scenic, Adventure
  - **Additional Details**: Free text for specific features

### 2. **Backend Services**

#### TrailPreferences DTO (`dto/TrailPreferences.java`)
- Data transfer object for collecting user preferences
- All fields are optional for flexibility

#### AITrailService (`Service/AITrailService.java`)
- **Smart Mode Switching**:
  - Uses OpenAI GPT-3.5-turbo if API key is configured
  - Falls back to intelligent mock data if API key is missing
- **Mock Data Generator**: Creates realistic trails based on preferences
- **Image Mapping**: Uses Unsplash URLs for category-based images

#### TrailController Update
- New endpoint: `POST /trails/generate`
- Accepts `TrailPreferences` JSON
- Returns list of AI-generated `Trail` objects

### 3. **Category-Based Images**
- Uses Unsplash for consistent, beautiful trail images
- Categories:
  - Lake: `https://source.unsplash.com/800x1200/?lake,hiking`
  - Mountain: `https://source.unsplash.com/800x1200/?mountain,hiking`
  - Forest: `https://source.unsplash.com/800x1200/?forest,hiking`
  - Desert: `https://source.unsplash.com/800x1200/?desert,hiking`
  - Coastal: `https://source.unsplash.com/800x1200/?coastal,beach`
  - Canyon: `https://source.unsplash.com/800x1200/?canyon,hiking`

### 4. **Frontend JavaScript**
- AJAX call to `/trails/generate` endpoint
- Dynamic trail card generation
- Loading indicators
- Mode switching (AI vs Database trails)
- Form reset functionality

## 🚀 How to Use

### For Testing (Without OpenAI API)
1. Start your Spring Boot application
2. Navigate to `/trails` page
3. Click "Customize" button to expand the AI panel
4. Set your preferences
5. Click "Generate AI Trails"
6. The system will use mock data to generate 8 trails

### To Enable Real AI (Optional)

#### Step 1: Get OpenAI API Key
1. Go to https://platform.openai.com/
2. Sign up or log in
3. Navigate to API Keys section
4. Create a new API key

#### Step 2: Configure API Key
Edit `src/main/resources/application.properties`:

```properties
# OpenAI API Configuration
openai.api.key=your-actual-api-key-here
```

#### Step 3: Restart Application
```bash
./mvnw spring-boot:run
```

Now the system will use real AI to generate creative, personalized trails!

## 📋 Features

### User Experience
- ✅ Collapsible preference panel
- ✅ Real-time loading indicators
- ✅ Switch between AI and database trails
- ✅ Beautiful category-based images
- ✅ Responsive design

### Technical Features
- ✅ RESTful API endpoint
- ✅ JSON data exchange
- ✅ Graceful fallback to mock data
- ✅ Error handling
- ✅ Bootstrap UI components
- ✅ Font Awesome icons

## 🔧 API Endpoint Details

### POST `/trails/generate`

**Request Body:**
```json
{
  "difficulty": "moderate",
  "minDistance": 2.0,
  "maxDistance": 15.0,
  "sceneryType": "mountain",
  "region": "Colorado",
  "mood": "scenic",
  "description": "waterfalls and wildlife"
}
```

**Response:**
```json
[
  {
    "name": "Emerald Lake Loop",
    "location": "Lake Tahoe, CA",
    "difficulty": "moderate",
    "distance": 6.1,
    "scenery": "lake",
    "description": "Easy lakeside trail with stunning views",
    "imageUrl": "https://source.unsplash.com/800x1200/?lake,hiking"
  }
]
```

## 🎨 Customization Options

### Change Number of Generated Trails
In `AITrailService.java`, find the prompt building section:
```java
prompt.append("Generate 8 realistic hiking trails...");
```
Change `8` to your desired number.

### Add More Scenery Categories
1. Update the `getCategoryImage()` method in `AITrailService.java`
2. Add new options to the scenery dropdown in `trails.html`

### Use Local Images Instead of Unsplash
1. Add image files to `src/main/resources/static/images/category/`
2. Update `getCategoryImage()` method:
```java
imageMap.put("lake", "/images/category/lake.jpg");
```

## 💡 Pro Tips

1. **Mock Data Testing**: The mock data generator is quite sophisticated and respects user preferences. It's perfect for development and demo purposes.

2. **API Costs**: OpenAI API calls cost money. For development, the mock data is free and works great!

3. **Caching**: Consider adding a session-based cache to store generated trails so users don't regenerate on every page refresh.

4. **Save to Database**: You could add a "Save Trail" button to let users save AI-generated trails to your database.

5. **User Sessions**: Store generated trails in session storage to persist across page interactions.

## 🐛 Troubleshooting

### Issue: No trails generated
- **Check**: Console logs for errors
- **Solution**: System will automatically fall back to mock data

### Issue: Images not loading
- **Check**: Network tab in browser dev tools
- **Solution**: Unsplash URLs require internet connection

### Issue: API errors
- **Check**: `openai.api.key` in `application.properties`
- **Solution**: Leave empty to use mock data, or add valid API key

## 📝 Next Steps (Optional Enhancements)

1. **Save Trails**: Add ability to save AI-generated trails to database
2. **Collections**: Let users add AI trails to their collections
3. **Ratings**: Enable rating/reviewing of AI trails (with session storage)
4. **Share**: Add social sharing for AI-generated trail recommendations
5. **History**: Store user's generation history
6. **Favorites**: Let users favorite certain generated trails

## 🎉 Success!

Your trails page now has a fully functional AI trail generation system that:
- ✅ Collects user preferences through a beautiful UI
- ✅ Generates personalized trails (AI or mock data)
- ✅ Displays results with category-appropriate images
- ✅ Allows switching between AI and database trails
- ✅ Provides excellent user experience

Enjoy your new AI-powered trail discovery feature! 🏔️


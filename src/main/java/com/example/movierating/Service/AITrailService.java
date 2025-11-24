package com.example.movierating.Service;

import com.example.movierating.db.po.Trail;
import com.example.movierating.dto.TrailPreferences;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AITrailService {

    @Value("${openai.api.key:}")
    private String openaiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Generate AI trails based on user preferences
     */
    public List<Trail> generateTrails(TrailPreferences preferences) {
        // If OpenAI API key is not configured, use mock data
        if (openaiApiKey == null || openaiApiKey.isEmpty()) {
            return generateMockTrails(preferences);
        }

        try {
            String prompt = buildPrompt(preferences);
            String aiResponse = callOpenAI(prompt);
            return parseAIResponse(aiResponse);
        } catch (Exception e) {
            System.err.println("Error calling OpenAI API: " + e.getMessage());
            // Fallback to mock data
            return generateMockTrails(preferences);
        }
    }

    /**
     * Build the AI prompt based on user preferences
     */
    private String buildPrompt(TrailPreferences preferences) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate 8 realistic hiking trails with the following preferences:\n");
        
        if (preferences.getDifficulty() != null && !preferences.getDifficulty().isEmpty()) {
            prompt.append("- Difficulty: ").append(preferences.getDifficulty()).append("\n");
        }
        
        if (preferences.getMinDistance() != null && preferences.getMaxDistance() != null) {
            prompt.append("- Distance: between ").append(preferences.getMinDistance())
                  .append(" and ").append(preferences.getMaxDistance()).append(" km\n");
        }
        
        if (preferences.getSceneryType() != null && !preferences.getSceneryType().isEmpty()) {
            prompt.append("- Scenery type: ").append(preferences.getSceneryType()).append("\n");
        }
        
        if (preferences.getRegion() != null && !preferences.getRegion().isEmpty()) {
            prompt.append("- Region: ").append(preferences.getRegion()).append("\n");
        }
        
        if (preferences.getMood() != null && !preferences.getMood().isEmpty()) {
            prompt.append("- Mood: ").append(preferences.getMood()).append("\n");
        }
        
        if (preferences.getDescription() != null && !preferences.getDescription().isEmpty()) {
            prompt.append("- Additional details: ").append(preferences.getDescription()).append("\n");
        }

        prompt.append("\nFor each trail, provide:\n");
        prompt.append("- name: creative trail name\n");
        prompt.append("- location: specific location\n");
        prompt.append("- difficulty: easy, moderate, or hard\n");
        prompt.append("- distance: numeric value in km\n");
        prompt.append("- scenery: ONE of (lake, mountain, forest, desert, coastal, canyon)\n");
        prompt.append("- description: brief 2-3 sentence description\n");
        prompt.append("\nReturn as JSON array with these exact field names.");

        return prompt.toString();
    }

    /**
     * Call OpenAI API
     */
    private String callOpenAI(String prompt) throws Exception {
        String url = "https://api.openai.com/v1/chat/completions";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", Arrays.asList(
            Map.of("role", "user", "content", prompt)
        ));
        requestBody.put("temperature", 0.8);
        requestBody.put("max_tokens", 2000);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        
        JsonNode root = objectMapper.readTree(response.getBody());
        return root.path("choices").get(0).path("message").path("content").asText();
    }

    /**
     * Parse AI response into Trail objects
     */
    private List<Trail> parseAIResponse(String aiResponse) {
        try {
            // Extract JSON from response (AI might wrap it in markdown)
            String json = aiResponse;
            if (json.contains("```json")) {
                json = json.substring(json.indexOf("["), json.lastIndexOf("]") + 1);
            }
            
            JsonNode trails = objectMapper.readTree(json);
            List<Trail> result = new ArrayList<>();
            
            for (JsonNode node : trails) {
                Trail trail = new Trail();
                trail.setName(node.path("name").asText());
                trail.setLocation(node.path("location").asText());
                trail.setDifficulty(node.path("difficulty").asText().toLowerCase());
                trail.setDistance(new BigDecimal(node.path("distance").asDouble()));
                trail.setScenery(node.path("scenery").asText().toLowerCase());
                trail.setDescription(node.path("description").asText());
                trail.setImageUrl(getCategoryImage(trail.getScenery()));
                result.add(trail);
            }
            
            return result;
        } catch (Exception e) {
            System.err.println("Error parsing AI response: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Generate mock trails for testing (when API key is not available)
     */
    private List<Trail> generateMockTrails(TrailPreferences preferences) {
        List<Trail> trails = new ArrayList<>();
        Random random = new Random();
        
        String[] sceneryTypes = {"lake", "mountain", "forest", "desert", "coastal", "canyon"};
        String preferredScenery = (preferences.getSceneryType() != null && !preferences.getSceneryType().isEmpty()) 
                                  ? preferences.getSceneryType() : sceneryTypes[random.nextInt(sceneryTypes.length)];
        
        String preferredDifficulty = (preferences.getDifficulty() != null && !preferences.getDifficulty().isEmpty())
                                     ? preferences.getDifficulty() : "moderate";
        
        double minDist = preferences.getMinDistance() != null ? preferences.getMinDistance() : 2.0;
        double maxDist = preferences.getMaxDistance() != null ? preferences.getMaxDistance() : 15.0;
        
        String[][] trailData = {
            {"Emerald Lake Loop", "Lake Tahoe, CA", "Easy lakeside trail with stunning views"},
            {"Summit Peak Trail", "Rocky Mountains, CO", "Challenging ascent with panoramic vistas"},
            {"Whispering Pines Path", "Olympic National Forest, WA", "Peaceful forest walk among ancient trees"},
            {"Desert Sunset Ridge", "Joshua Tree, CA", "Dramatic desert landscape at golden hour"},
            {"Coastal Cliff Walk", "Big Sur, CA", "Breathtaking ocean views along dramatic cliffs"},
            {"Canyon Echo Trail", "Grand Canyon, AZ", "Historic route through stunning red rock formations"},
            {"Alpine Meadow Loop", "Swiss Alps, Switzerland", "Wildflower meadows with mountain backdrop"},
            {"Misty Falls Trail", "Yosemite, CA", "Waterfall hike through lush vegetation"}
        };
        
        for (int i = 0; i < 8 && i < trailData.length; i++) {
            Trail trail = new Trail();
            trail.setName(trailData[i][0]);
            trail.setLocation(trailData[i][1]);
            trail.setDifficulty(preferredDifficulty);
            
            // Generate distance within range
            double distance = minDist + (maxDist - minDist) * random.nextDouble();
            trail.setDistance(BigDecimal.valueOf(Math.round(distance * 10.0) / 10.0));
            
            // Assign scenery with preference
            String scenery = (i % 3 == 0) ? preferredScenery : sceneryTypes[random.nextInt(sceneryTypes.length)];
            trail.setScenery(scenery);
            trail.setDescription(trailData[i][2]);
            trail.setImageUrl(getCategoryImage(scenery));
            
            trails.add(trail);
        }
        
        return trails;
    }

    /**
     * Map scenery category to image path
     * Uses Unsplash as fallback for better visual experience
     */
    private String getCategoryImage(String scenery) {
        if (scenery == null) scenery = "mountain";
        scenery = scenery.toLowerCase().trim();
        
        // Use Unsplash for consistent, beautiful images
        Map<String, String> imageMap = new HashMap<>();
        imageMap.put("lake", "https://source.unsplash.com/800x1200/?lake,hiking");
        imageMap.put("mountain", "https://source.unsplash.com/800x1200/?mountain,hiking");
        imageMap.put("forest", "https://source.unsplash.com/800x1200/?forest,hiking");
        imageMap.put("desert", "https://source.unsplash.com/800x1200/?desert,hiking");
        imageMap.put("coastal", "https://source.unsplash.com/800x1200/?coastal,beach");
        imageMap.put("canyon", "https://source.unsplash.com/800x1200/?canyon,hiking");
        
        return imageMap.getOrDefault(scenery, "https://source.unsplash.com/800x1200/?mountain,hiking");
    }
}


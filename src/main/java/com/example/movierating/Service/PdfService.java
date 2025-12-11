package com.example.movierating.Service;

import com.example.movierating.db.po.Trail;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;

@Service
public class PdfService {

    private static final float MARGIN = 50;
    private static final float PAGE_WIDTH = PDRectangle.A4.getWidth();
    private static final float PAGE_HEIGHT = PDRectangle.A4.getHeight();
    private static final float CONTENT_WIDTH = PAGE_WIDTH - 2 * MARGIN;

    public ByteArrayOutputStream generateTrailPdf(Trail trail) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            float yPosition = PAGE_HEIGHT - MARGIN;
            
            yPosition = addTitle(contentStream, trail.getName(), yPosition);
            yPosition -= 20;

            if (trail.getImageUrl() != null && !trail.getImageUrl().isEmpty()) {
                try {
                    yPosition = addImage(contentStream, trail.getImageUrl(), yPosition, document);
                    yPosition -= 15;
                } catch (Exception e) {
                    // Image loading failed, continue without image
                }
            }

            yPosition = addSection(contentStream, "Trail Information", yPosition);
            yPosition = addInfoLine(contentStream, "Location:", trail.getLocation(), yPosition);
            yPosition = addInfoLine(contentStream, "Difficulty:", formatDifficulty(trail.getDifficulty()), yPosition);
            yPosition = addInfoLine(contentStream, "Distance:", 
                    trail.getDistance() != null ? trail.getDistance() + " km" : "N/A", yPosition);
            yPosition = addInfoLine(contentStream, "Scenery:", trail.getScenery(), yPosition);
            yPosition -= 10;

            if (trail.getDescription() != null && !trail.getDescription().isEmpty()) {
                yPosition = addSection(contentStream, "Description", yPosition);
                yPosition = addWrappedText(contentStream, trail.getDescription(), yPosition);
                yPosition -= 5;
            }

            yPosition = addSection(contentStream, "Trail Map", yPosition);
            yPosition = addTrailMap(contentStream, trail, yPosition, document);

            addFooter(contentStream, trail);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            document.save(outputStream);
        } finally {
            document.close();
        }
        return outputStream;
    }

    private float addTitle(PDPageContentStream contentStream, String title, float yPosition) throws IOException {
        PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
        contentStream.setFont(font, 24);
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, yPosition);
        contentStream.showText(truncateText(title, 60));
        contentStream.endText();
        return yPosition - 30;
    }

    private float addSection(PDPageContentStream contentStream, String sectionTitle, float yPosition) throws IOException {
        if (yPosition < MARGIN + 50) {
            return yPosition;
        }
        
        PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
        contentStream.setFont(font, 14);
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, yPosition);
        contentStream.showText(sectionTitle);
        contentStream.endText();
        return yPosition - 18;
    }

    private float addInfoLine(PDPageContentStream contentStream, String label, String value, float yPosition) throws IOException {
        if (yPosition < MARGIN + 30) {
            return yPosition;
        }

        PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        contentStream.setFont(font, 11);
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, yPosition);
        contentStream.showText(label + " " + (value != null ? value : "N/A"));
        contentStream.endText();
        return yPosition - 18;
    }

    private float addWrappedText(PDPageContentStream contentStream, String text, float yPosition) throws IOException {
        PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        contentStream.setFont(font, 10);
        
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        float lineHeight = 14;
        float currentY = yPosition;

        for (String word : words) {
            String testLine = line.length() > 0 ? line + " " + word : word;
            float width = testLine.length() * 6;

            if (width > CONTENT_WIDTH && line.length() > 0) {
                contentStream.beginText();
                contentStream.newLineAtOffset(MARGIN, currentY);
                contentStream.showText(line.toString());
                contentStream.endText();
                line = new StringBuilder(word);
                currentY -= lineHeight;
                
                if (currentY < MARGIN + 20) {
                    break;
                }
            } else {
                line.append(line.length() > 0 ? " " + word : word);
            }
        }

        if (line.length() > 0 && currentY >= MARGIN + 20) {
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, currentY);
            contentStream.showText(line.toString());
            contentStream.endText();
        }

        return currentY - lineHeight;
    }

    private float addImage(PDPageContentStream contentStream, String imageUrl, float yPosition, PDDocument document) throws IOException {
        try {
            URL url = new URL(imageUrl);
            try (InputStream imageStream = url.openStream()) {
                PDImageXObject image = PDImageXObject.createFromByteArray(
                    document, imageStream.readAllBytes(), "trail-image");
                
                float imageWidth = Math.min(CONTENT_WIDTH, 400);
                float imageHeight = (image.getHeight() / image.getWidth()) * imageWidth;
                
                if (yPosition - imageHeight < MARGIN) {
                    return yPosition;
                }

                contentStream.drawImage(image, MARGIN, yPosition - imageHeight, imageWidth, imageHeight);
                return yPosition - imageHeight;
            }
        } catch (Exception e) {
            return yPosition;
        }
    }

    private float addTrailMap(PDPageContentStream contentStream, Trail trail, float yPosition, PDDocument document) throws IOException {
        if (yPosition < MARGIN + 100) {
            return yPosition;
        }

        float mapHeight = 250;
        float availableSpace = yPosition - MARGIN;
        float mapTopY = yPosition - 5;
        float mapY = mapTopY - mapHeight;
        
        if (mapY < MARGIN + 20) {
            mapY = MARGIN + 20;
            mapTopY = mapY + mapHeight;
        }
        
        String mapImagePath = getMapImagePath(trail);
        Exception lastException = null;
        
        try {
            byte[] imageBytes = null;
            String loadedFrom = null;
            
            ClassPathResource resource1 = new ClassPathResource(mapImagePath);
            if (resource1.exists()) {
                try (InputStream is = resource1.getInputStream()) {
                    imageBytes = is.readAllBytes();
                    loadedFrom = "ClassPathResource";
                }
            }
            
            if (imageBytes == null) {
                String pathWithoutStatic = mapImagePath.replaceFirst("^static/", "");
                ClassPathResource resource2 = new ClassPathResource(pathWithoutStatic);
                if (resource2.exists()) {
                    try (InputStream is = resource2.getInputStream()) {
                        imageBytes = is.readAllBytes();
                        loadedFrom = "ClassPathResource";
                    }
                }
            }
            
            if (imageBytes == null) {
                String projectRoot = System.getProperty("user.dir");
                String filePath = projectRoot + "/src/main/resources/" + mapImagePath;
                java.io.File file = new java.io.File(filePath);
                if (file.exists()) {
                    imageBytes = java.nio.file.Files.readAllBytes(file.toPath());
                    loadedFrom = "FileSystem";
                }
            }
            
            if (imageBytes == null) {
                String projectRoot = System.getProperty("user.dir");
                String filePath = projectRoot + "/target/classes/" + mapImagePath;
                java.io.File file = new java.io.File(filePath);
                if (file.exists()) {
                    imageBytes = java.nio.file.Files.readAllBytes(file.toPath());
                    loadedFrom = "target/classes";
                }
            }
            
            if (imageBytes != null && imageBytes.length > 0) {
                PDImageXObject mapImage = PDImageXObject.createFromByteArray(
                    document, imageBytes, "trail-map");
                
                float imageWidth = CONTENT_WIDTH;
                float imageHeight = (mapImage.getHeight() / (float)mapImage.getWidth()) * imageWidth;
                
                if (imageHeight > mapHeight) {
                    float scale = mapHeight / imageHeight;
                    imageHeight = mapHeight;
                    imageWidth = imageWidth * scale;
                } else if (imageHeight < mapHeight * 0.8f) {
                    float scale = Math.min(1.2f, mapHeight / imageHeight);
                    imageHeight = imageHeight * scale;
                    imageWidth = imageWidth * scale;
                }
                
                float imageX = MARGIN;
                if (imageWidth < CONTENT_WIDTH) {
                    imageX = MARGIN + (CONTENT_WIDTH - imageWidth) / 2;
                }
                
                float finalY = mapY;
                float imageTopY = finalY + imageHeight;
                
                if (finalY < MARGIN) {
                    float availableHeight = mapTopY - MARGIN;
                    if (availableHeight < 50) {
                        throw new IOException("Not enough vertical space for image");
                    }
                    float scale = availableHeight / imageHeight;
                    imageHeight = availableHeight;
                    imageWidth = imageWidth * scale;
                    finalY = MARGIN;
                }

                contentStream.drawImage(mapImage, imageX, finalY, imageWidth, imageHeight);
                return finalY - 10;
            } else {
                lastException = new IOException("Map image not found: " + mapImagePath);
            }
        } catch (Exception e) {
            lastException = e;
        }
        
        float result = addSimpleMapBox(contentStream, mapY, mapHeight);
        return result;
    }

    private String getMapImagePath(Trail trail) {
        if (trail.getMapImage() != null && !trail.getMapImage().isEmpty()) {
            String path = trail.getMapImage();
            if (path.startsWith("static/")) {
                return path;
            } else if (path.startsWith("/images/")) {
                return "static" + path;
            } else if (path.startsWith("images/")) {
                return "static/" + path;
            }
            return path;
        }

        int totalMaps = 12;
        int trailId = trail.getTrailId() != null ? trail.getTrailId() : 1;
        int mapNumber = ((trailId * 7) % totalMaps) + 1;
        return "static/images/maps/map" + mapNumber + ".jpg";
    }

    private float addSimpleMapBox(PDPageContentStream contentStream, float mapY, float mapHeight) throws IOException {
        contentStream.setNonStrokingColor(0.95f, 0.95f, 0.95f);
        contentStream.addRect(MARGIN, mapY - mapHeight, CONTENT_WIDTH, mapHeight);
        contentStream.fill();
        
        contentStream.setStrokingColor(0.6f, 0.6f, 0.6f);
        contentStream.setLineWidth(1);
        contentStream.addRect(MARGIN, mapY - mapHeight, CONTENT_WIDTH, mapHeight);
        contentStream.stroke();

        contentStream.setStrokingColor(0.8f, 0.8f, 0.8f);
        contentStream.setLineWidth(0.5f);
        for (int i = 1; i < 4; i++) {
            float x = MARGIN + (CONTENT_WIDTH / 4) * i;
            contentStream.moveTo(x, mapY - mapHeight);
            contentStream.lineTo(x, mapY);
            contentStream.stroke();
        }
        for (int i = 1; i < 4; i++) {
            float y = mapY - mapHeight + (mapHeight / 4) * i;
            contentStream.moveTo(MARGIN, y);
            contentStream.lineTo(MARGIN + CONTENT_WIDTH, y);
            contentStream.stroke();
        }
        
        float centerX = MARGIN + CONTENT_WIDTH / 2;
        float centerY = mapY - mapHeight / 2;
        float radius = 15;
        contentStream.setStrokingColor(0.4f, 0.4f, 0.4f);
        contentStream.setLineWidth(1);
        
        contentStream.moveTo(centerX + radius, centerY);
        for (int i = 0; i <= 360; i += 10) {
            double angle = Math.toRadians(i);
            float x = centerX + (float)(radius * Math.cos(angle));
            float y = centerY + (float)(radius * Math.sin(angle));
            contentStream.lineTo(x, y);
        }
        contentStream.stroke();
        
        contentStream.moveTo(centerX, centerY);
        contentStream.lineTo(centerX, centerY + 10);
        contentStream.stroke();

        return mapY - mapHeight - 10;
    }

    private void addFooter(PDPageContentStream contentStream, Trail trail) throws IOException {
        PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        contentStream.setFont(font, 8);
        contentStream.setNonStrokingColor(0.5f, 0.5f, 0.5f);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = trail.getCreateDate() != null ? sdf.format(trail.getCreateDate()) : "N/A";
        
        String footer = "Generated by TrailRank | " + dateStr + " | For offline use";
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, MARGIN - 10);
        contentStream.showText(footer);
        contentStream.endText();
        contentStream.setNonStrokingColor(0, 0, 0);
    }

    private String formatDifficulty(String difficulty) {
        if (difficulty == null) return "N/A";
        return difficulty.substring(0, 1).toUpperCase() + difficulty.substring(1).toLowerCase();
    }

    private String truncateText(String text, int maxLength) {
        if (text == null) return "";
        return text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
    }
}

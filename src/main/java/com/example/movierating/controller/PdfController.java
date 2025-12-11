package com.example.movierating.controller;

import com.example.movierating.Service.PdfService;
import com.example.movierating.Service.TrailService;
import com.example.movierating.db.po.Trail;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;

@Controller
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private TrailService trailService;

    @GetMapping("/trails/{trailId}")
    public ResponseEntity<ByteArrayResource> downloadTrailPdf(
            @PathVariable("trailId") Integer trailId,
            HttpSession session) {

        if (session.getAttribute("userEmail") == null) {
            return ResponseEntity.status(401).build();
        }

        try {
            Trail trail = trailService.getTrailById(trailId);
            if (trail == null) {
                return ResponseEntity.notFound().build();
            }

            ByteArrayOutputStream pdfOutputStream = pdfService.generateTrailPdf(trail);
            byte[] pdfBytes = pdfOutputStream.toByteArray();

            ByteArrayResource resource = new ByteArrayResource(pdfBytes);

            HttpHeaders headers = new HttpHeaders();
            String filename = sanitizeFilename(trail.getName()) + "_trail_guide.pdf";
            headers.add(HttpHeaders.CONTENT_DISPOSITION, 
                    "attachment; filename=\"" + filename + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(pdfBytes.length)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private String sanitizeFilename(String filename) {
        if (filename == null) {
            return "trail";
        }
        return filename.replaceAll("[^a-zA-Z0-9._-]", "_").substring(0, Math.min(50, filename.length()));
    }
}

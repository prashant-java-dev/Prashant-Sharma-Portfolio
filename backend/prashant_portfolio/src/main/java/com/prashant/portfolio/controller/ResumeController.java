package com.prashant.portfolio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling resume operations.
 * Provides preview and download endpoints for the resume PDF.
 * Resume: Prashant_Sharma_Software_Developer_Resume.pdf
 */
@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private static final Logger logger = LoggerFactory.getLogger(ResumeController.class);
    private static final String RESUME_PATH = "resume/Prashant_Sharma_Software_Developer_Resume.pdf";
    private static final String RESUME_FILENAME = "Prashant_Sharma_Software_Developer_Resume.pdf";

    /**
     * Endpoint to preview resume in browser.
     * @return Resume PDF as inline content
     */
    @GetMapping("/view")
    public ResponseEntity<Resource> viewResume() {
        logger.info("Resume view requested");
        return buildResumeResponse("inline");
    }

    /**
     * Endpoint to download resume.
     * @return Resume PDF as downloadable attachment
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadResume() {
        logger.info("Resume download requested");
        return buildResumeResponse("attachment");
    }

    /**
     * Builds the resume response with specified disposition.
     */
    private ResponseEntity<Resource> buildResumeResponse(String disposition) {
        try {
            ClassPathResource resource = new ClassPathResource(RESUME_PATH);

            // If exact path not found, try to find any PDF in the resume folder
            if (!resource.exists()) {
                logger.warn("Resume file not found at exact path: {}. Searching in resume/ folder...", RESUME_PATH);
                try {
                    Resource folder = new ClassPathResource("resume/");
                    if (folder.exists() && folder.getFile().isDirectory()) {
                        java.io.File[] files = folder.getFile().listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
                        if (files != null && files.length > 0) {
                            String foundPath = "resume/" + files[0].getName();
                            logger.info("Found alternative resume file: {}", foundPath);
                            resource = new ClassPathResource(foundPath);
                        }
                    }
                } catch (Exception e) {
                    logger.error("Error searching for alternative resume file", e);
                }
            }

            if (!resource.exists()) {
                logger.error("No resume PDF file found in classpath: resume/");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            String filename = resource.getFilename();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            disposition + "; filename=\"" + filename + "\"")
                    .body(resource);

        } catch (Exception e) {
            logger.error("Error serving resume file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


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

            if (!resource.exists()) {
                logger.error("Resume file not found at path: {}", RESUME_PATH);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            disposition + "; filename=\"" + RESUME_FILENAME + "\"")
                    .body(resource);

        } catch (Exception e) {
            logger.error("Error serving resume file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


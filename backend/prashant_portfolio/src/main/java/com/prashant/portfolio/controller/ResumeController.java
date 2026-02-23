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
 * Controller to manage Resume distribution.
 * 
 * IMPLEMENTATION DETAILS:
 * - Resource Handling: Uses ClassPathResource to serve files bundled within the JAR, 
 *   ensuring the application is portable across environments (Local, Railway, etc.).
 * - Content Disposition: Dynamically switches between 'inline' (preview) and 'attachment' (download).
 */
@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private static final Logger logger = LoggerFactory.getLogger(ResumeController.class);
    
    // Path within src/main/resources
    private static final String RESUME_RESOURCE_PATH = "resume/Prashant_Sharma_Software_Developer_Resume.pdf";
    private static final String DOWNLOAD_FILENAME = "Prashant_Sharma_Software_Developer_Resume.pdf";

    /**
     * Preview the resume directly in the browser tab.
     */
    @GetMapping("/view")
    public ResponseEntity<Resource> viewResume() {
        return buildResumeResponse("inline");
    }

    /**
     * Trigger a direct browser download of the resume.
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadResume() {
        return buildResumeResponse("attachment");
    }

    /**
     * Helper method to centralize resource loading logic.
     * Demonstrates DRY (Don't Repeat Yourself) principle.
     */
    private ResponseEntity<Resource> buildResumeResponse(String disposition) {
        try {
            Resource resource = new ClassPathResource(RESUME_RESOURCE_PATH);

            if (!resource.exists()) {
                logger.error("Configuration Error: Resume file missing at classpath:{}", RESUME_RESOURCE_PATH);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                            String.format("%s; filename=\"%s\"", disposition, DOWNLOAD_FILENAME))
                    .body(resource);

        } catch (Exception e) {
            logger.error("Internal Error: Could not process resume request - {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

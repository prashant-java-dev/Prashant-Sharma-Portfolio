package com.prashant.portfolio.controller;

import com.prashant.portfolio.dto.ApiResponse;
import com.prashant.portfolio.dto.ContactRequestDto;
import com.prashant.portfolio.service.EmailService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling contact form submissions.
 */
@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    private final EmailService emailService;

    // Constructor injection (best practice)
    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Handles contact form submission.
     * @param request Contact form data with validation
     * @return Standardized API response
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> sendMessage(@Valid @RequestBody ContactRequestDto request) {
        logger.info("Received contact form submission from: {}", request.getEmail());
        
        // These run in background (Async)
        emailService.sendNotificationToOwner(request);
        emailService.sendAcknowledgmentToUser(request);
        
        return ResponseEntity.ok(
            ApiResponse.success("Message sent successfully!")
        );
    }
}


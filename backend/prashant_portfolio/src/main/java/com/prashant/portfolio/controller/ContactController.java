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
 * REST Controller to handle contact form communications.
 * 
 * DESIGN PRINCIPLES:
 * 1. Dependency Injection: Uses constructor-based injection for better testability (cleaner than @Autowired).
 * 2. Separation of Concerns: Delegates heavy business logic (email sending) to EmailService.
 * 3. Input Validation: Uses @Valid with Jakarta Validation to ensure data integrity before processing.
 * 4. Standardized Responses: Wraps all returns in a custom ApiResponse for consistent API consumer experience.
 */
@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);
    private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * POST /api/contact
     * Processes inquiry messages from the portfolio frontend.
     * 
     * OPTIMIZATION: Email sending is invoked asynchronously to ensure 
     * a responsive 1-2 second feedback loop for the frontend user.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> sendMessage(@Valid @RequestBody ContactRequestDto request) {
        logger.info("Processing contact request from: {}", request.getEmail());
        
        // Trigger non-blocking email operations
        emailService.sendNotificationToOwner(request);
        emailService.sendAcknowledgmentToUser(request);
        
        return ResponseEntity.ok(ApiResponse.success("Message received. I'll get back to you soon!"));
    }
}

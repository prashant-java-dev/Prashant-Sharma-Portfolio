package com.prashant.portfolio.service;

import com.prashant.portfolio.config.EmailConfig;
import com.prashant.portfolio.dto.ContactRequestDto;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service layer for handling Email Communications.
 * 
 * CORE FEATURES:
 * - Asynchronous Processing: Utilizes @Async to prevent SMTP latency from blocking the main thread.
 * - SMTP Integration: Configured for Gmail SMTP with StartTLS for secure transport.
 * - UX Centric: Implements 'Reply-To' headers so recruiters can reply directly to the sender's email.
 */
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    private final EmailConfig emailConfig;

    public EmailService(JavaMailSender mailSender, EmailConfig emailConfig) {
        this.mailSender = mailSender;
        this.emailConfig = emailConfig;
        
        // Audit log for startup verification
        logger.info("EmailService initialized with system sender: {}", maskEmail(emailConfig.getFromEmail()));
    }

    /**
     * Sends a detailed notification to the portfolio owner.
     * Includes sender metadata and message content.
     */
    @Async
    public void sendNotificationToOwner(ContactRequestDto request) {
        logger.info("Attempting to send owner notification for sender: {}", request.getEmail());
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setFrom(new InternetAddress(emailConfig.getFromEmail(), "Portfolio-Bot"));
            helper.setReplyTo(new InternetAddress(request.getEmail(), request.getName()));
            helper.setTo(emailConfig.getToEmail());
            helper.setSubject("📬 New Message: " + request.getName());

            String body = String.format(
                "--- NEW INQUIRY RECEIVED ---\n" +
                "Name    : %s\n" +
                "Email   : %s\n" +
                "Subject : %s\n" +
                "---------------------------\n\n" +
                "MESSAGE:\n%s\n\n" +
                "---------------------------\n" +
                "Reply directly to this email to contact the sender.",
                request.getName(), request.getEmail(), request.getSubject(), request.getMessage()
            );

            helper.setText(body);
            mailSender.send(mimeMessage);
            logger.info("SUCCESS: Notification sent to owner inbox.");
        } catch (Exception e) {
            logger.error("FAILURE: Error in owner notification - {}", e.getMessage());
        }
    }

    /**
     * Sends a professional acknowledgment email to the user.
     * Confirms receipt and sets expectations for a follow-up.
     */
    @Async
    public void sendAcknowledgmentToUser(ContactRequestDto request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setFrom(new InternetAddress(emailConfig.getFromEmail(), "Prashant Sharma"));
            helper.setReplyTo(new InternetAddress(emailConfig.getToEmail()));
            helper.setTo(request.getEmail());
            helper.setSubject("Thank you for reaching out, " + request.getName() + "!");

            String body = String.format(
                "Hi %s,\n\n" +
                "This is a confirmation that I've received your message regarding '%s'.\n" +
                "I appreciate you taking the time to visit my portfolio.\n\n" +
                "I will review your message and get back to you at this email address within 24-48 hours.\n\n" +
                "Best Regards,\n" +
                "Prashant Sharma\n" +
                "Software Developer\n" +
                "GitHub: https://github.com/prashant-java-dev",
                request.getName(), request.getSubject()
            );

            helper.setText(body);
            mailSender.send(mimeMessage);
            logger.info("SUCCESS: Acknowledgment sent to user: {}", request.getEmail());
        } catch (Exception e) {
            logger.error("FAILURE: User acknowledgment failed - {}", e.getMessage());
        }
    }

    /**
     * Security helper to mask sensitive debug info in logs.
     */
    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) return "****";
        return email.replaceAll("(^.{2})(.*)(@.*)", "$1****$3");
    }
}

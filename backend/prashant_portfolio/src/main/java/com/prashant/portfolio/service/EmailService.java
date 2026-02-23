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
 * Service for handling email operations.
 */
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final EmailConfig emailConfig;

    public EmailService(JavaMailSender mailSender, EmailConfig emailConfig) {
        this.mailSender = mailSender;
        this.emailConfig = emailConfig;
    }

    /**
     * Sends a notification email to Prashant (Owner).
     * The subject and body clearly show the sender's email.
     */
    @Async
    public void sendNotificationToOwner(ContactRequestDto request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            // Display sender's name in the "From" display part for better visibility
            helper.setFrom(new InternetAddress(emailConfig.getFromEmail(), "Portfolio: " + request.getName()));
            
            // Critical: Set Reply-To so clicking "Reply" in your inbox goes to the USER
            helper.setReplyTo(new InternetAddress(request.getEmail(), request.getName()));
            
            helper.setTo(emailConfig.getToEmail());
            
            // Include user's email directly in subject for quick view
            helper.setSubject("📬 message from: " + request.getEmail());

            String body = "Hello Prashant,\n\n" +
                    "You have received a new inquiry from your portfolio website.\n\n" +
                    "--- SENDER DETAILS ---\n" +
                    "Name    : " + request.getName() + "\n" +
                    "Email   : " + request.getEmail() + "\n" +
                    "Subject : " + request.getSubject() + "\n" +
                    "-----------------------\n\n" +
                    "MESSAGE CONTENT:\n" + request.getMessage() + "\n\n" +
                    "-----------------------\n" +
                    "ACTION REQUIRED:\n" +
                    "You can reply to this message directly from your email client.";

            helper.setText(body);
            mailSender.send(mimeMessage);
            logger.info("Notification email sent to owner for: {}", request.getEmail());

        } catch (Exception e) {
            logger.error("Error sending notification to owner", e);
        }
    }

    /**
     * Sends a professional "Thank You" email to the user.
     */
    @Async
    public void sendAcknowledgmentToUser(ContactRequestDto request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setFrom(new InternetAddress(emailConfig.getFromEmail(), "Prashant Sharma"));
            helper.setTo(request.getEmail());
            helper.setSubject("Thank you for your message, " + request.getName() + "!");

            String body = "Hi " + request.getName() + ",\n\n" +
                    "Thank you for reaching out through my Developer Portfolio! This is an automated confirmation that I have successfully received your inquiry regarding '" + request.getSubject() + "'.\n\n" +
                    "I have read your message and I'm very interested in what you Shared. I will review the details and get back to you at this email address (" + request.getEmail() + ") within the next 24-48 hours.\n\n" +
                    "In the meantime, feel free to check out my latest projects on GitHub: https://github.com/prashant-java-dev\n\n" +
                    "Best Regards,\n" +
                    "Prashant Sharma\n" +
                    "Software Developer\n" +
                    "Moradabad, India";

            helper.setText(body);
            mailSender.send(mimeMessage);
            logger.info("Acknowledgment email sent to user: {}", request.getEmail());

        } catch (Exception e) {
            logger.error("Error sending acknowledgment to user", e);
        }
    }
}

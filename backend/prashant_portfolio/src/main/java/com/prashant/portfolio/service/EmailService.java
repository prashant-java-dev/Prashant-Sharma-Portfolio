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
        logger.info("EmailService initialized with FROM: {} and TO: {}", 
            maskEmail(emailConfig.getFromEmail()), maskEmail(emailConfig.getToEmail()));
    }

    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) return "****";
        return email.replaceAll("(^.{2})(.*)(@.*)", "$1****$3");
    }

    /**
     * Sends a notification email to Prashant (Owner).
     * The subject and body clearly show the sender's email.
     */
    @Async
    public void sendNotificationToOwner(ContactRequestDto request) {
        logger.info("Starting to send notification email to owner for: {}", request.getEmail());
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setFrom(new InternetAddress(emailConfig.getFromEmail(), "Portfolio: " + request.getName()));
            helper.setReplyTo(new InternetAddress(request.getEmail(), request.getName()));
            helper.setTo(emailConfig.getToEmail());
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
            logger.info("SUCCESS: Notification email sent to owner for: {}", request.getEmail());

        } catch (Exception e) {
            logger.error("CRITICAL ERROR: Failed to send notification email to owner. Reason: {}", e.getMessage(), e);
        }
    }

    /**
     * Sends a professional acknowledgment email to the user.
     */
    @Async
    public void sendAcknowledgmentToUser(ContactRequestDto request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            // Appearance in User's Inbox
            helper.setFrom(new InternetAddress(emailConfig.getFromEmail(), "Prashant Sharma"));
            
            // Critical: If user clicks "Reply", it goes to Prashant's real email
            helper.setReplyTo(new InternetAddress(emailConfig.getToEmail(), "Prashant Sharma"));
            
            helper.setTo(request.getEmail());
            helper.setSubject("Notification: Message Received on Prashant's Portfolio");

            String dateStr = new java.text.SimpleDateFormat("dd MMM yyyy, HH:mm").format(new java.util.Date());
            
            String body = "--------------------------------------------------\n" +
                    " PORTFOLIO NOTIFICATION \n" +
                    "--------------------------------------------------\n" +
                    "Date      : " + dateStr + "\n" +
                    "Subject   : " + request.getSubject() + "\n" +
                    "mailed-by : portfolio-system.up.railway.app\n" +
                    "Signed-by : prashant-sharma.com\n" +
                    "--------------------------------------------------\n\n" +
                    "Hi " + request.getName() + ",\n\n" +
                    "Thank you for reaching out! I have received your message through my portfolio website. Here is a summary of your inquiry:\n\n" +
                    "\"" + request.getMessage() + "\"\n\n" +
                    "I will review this and get back to you as soon as possible. You can reply directly to this email if you have more details to share.\n\n" +
                    "Best Regards,\n" +
                    "Prashant Sharma\n" +
                    "Software Developer\n" +
                    "https://github.com/prashant-java-dev";

            helper.setText(body);
            mailSender.send(mimeMessage);
            logger.info("Acknowledgment email sent to user: {}", request.getEmail());

        } catch (Exception e) {
            logger.error("Error sending acknowledgment to user", e);
        }
    }
}

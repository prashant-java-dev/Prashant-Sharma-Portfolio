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
     */
    @Async
    public void sendNotificationToOwner(ContactRequestDto request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            // Sent FROM your system email (Authenticated)
            // But we set the personal name to the SENDER'S name
            helper.setFrom(new InternetAddress(emailConfig.getFromEmail(), "Portfolio: " + request.getName()));
            
            // ✅ CRITICAL: Set Reply-To to the user's email
            // When you click 'Reply' in Gmail, it will go to the user.
            helper.setReplyTo(new InternetAddress(request.getEmail(), request.getName()));
            
            helper.setTo(emailConfig.getToEmail());
            helper.setSubject("📬 New Message: " + request.getSubject());

            String htmlBody = String.format(
                "<div style='font-family: sans-serif; line-height: 1.6; color: #333; max-width: 600px; margin: 0 auto; border: 1px solid #eee; padding: 20px; border-radius: 10px;'>" +
                "<h2 style='color: #FF0000; border-bottom: 2px solid #FF0000; padding-bottom: 10px;'>New Portfolio Message</h2>" +
                "<p><strong>From:</strong> %s &lt;%s&gt;</p>" +
                "<p><strong>Subject:</strong> %s</p>" +
                "<div style='background: #f9f9f9; padding: 15px; border-radius: 5px; margin-top: 20px; white-space: pre-wrap;'>" +
                "%s" +
                "</div>" +
                "<hr style='margin-top: 30px; border: 0; border-top: 1px solid #eee;' />" +
                "<p style='font-size: 0.8em; color: #888;'>This email was sent from your portfolio website. You can reply directly to this email to contact the sender.</p>" +
                "</div>",
                request.getName(), request.getEmail(), request.getSubject(), request.getMessage()
            );

            helper.setText(htmlBody, true);
            mailSender.send(mimeMessage);
            logger.info("Notification email sent to owner for: {}", request.getEmail());

        } catch (Exception e) {
            logger.error("Error sending notification to owner", e);
        }
    }

    /**
     * Sends a "Thank You" email to the user.
     */
    @Async
    public void sendAcknowledgmentToUser(ContactRequestDto request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(new InternetAddress(emailConfig.getFromEmail(), "Prashant Sharma"));
            helper.setTo(request.getEmail());
            helper.setSubject("Thanks for reaching out!");

            String htmlBody = String.format(
                "<div style='font-family: sans-serif; line-height: 1.6; color: #333; padding: 20px;'>" +
                "<h3>Hi %s,</h3>" +
                "<p>Thank you for contacting me through my portfolio. I have received your message regarding <strong>\"%s\"</strong>.</p>" +
                "<p>I will review your message and get back to you as soon as possible.</p>" +
                "<br/>" +
                "<p>Best Regards,</p>" +
                "<p><strong>Prashant Sharma</strong><br/>Software Developer</p>" +
                "</div>",
                request.getName(), request.getSubject()
            );

            helper.setText(htmlBody, true);
            mailSender.send(mimeMessage);
            logger.info("Acknowledgment email sent to user: {}", request.getEmail());

        } catch (Exception e) {
            logger.error("Error sending acknowledgment to user", e);
        }
    }
}

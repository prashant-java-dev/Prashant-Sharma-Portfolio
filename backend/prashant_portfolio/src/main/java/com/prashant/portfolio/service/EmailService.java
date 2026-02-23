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
     * @Async makes it run in background so user doesn't wait.
     */
    @Async
    public void sendNotificationToOwner(ContactRequestDto request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setFrom(emailConfig.getFromEmail(), "Portfolio Website");
            helper.setReplyTo(new InternetAddress(request.getEmail(), request.getName()));
            helper.setTo(emailConfig.getToEmail());
            helper.setSubject("📬 New Message: " + request.getName() + " | " + request.getSubject());

            String body = "Hello Prashant,\n\n" +
                    "You have a new message from your portfolio website:\n\n" +
                    "-----------------------------------\n" +
                    "Name    : " + request.getName() + "\n" +
                    "Email   : " + request.getEmail() + "\n" +
                    "Subject : " + request.getSubject() + "\n" +
                    "-----------------------------------\n\n" +
                    "Message:\n" + request.getMessage() + "\n\n" +
                    "-----------------------------------\n" +
                    "You can reply directly to this email to contact the sender.";

            helper.setText(body);
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
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setFrom(emailConfig.getFromEmail(), "Prashant Sharma");
            helper.setTo(request.getEmail());
            helper.setSubject("Thanks for reaching out!");

            String body = "Hi " + request.getName() + ",\n\n" +
                    "Thank you for contacting me through my portfolio. " +
                    "I have received your message and will get back to you soon.\n\n" +
                    "Best Regards,\nPrashant Sharma";

            helper.setText(body);
            mailSender.send(mimeMessage);
            logger.info("Acknowledgment email sent to user: {}", request.getEmail());

        } catch (Exception e) {
            logger.error("Error sending acknowledgment to user", e);
        }
    }
}

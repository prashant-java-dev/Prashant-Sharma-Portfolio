package com.prashant.portfolio.service;

import com.prashant.portfolio.config.EmailConfig;
import com.prashant.portfolio.dto.ContactRequestDto;
import com.prashant.portfolio.exception.BusinessException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service for handling email operations.
 * Sends contact form submissions via email.
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
     * Sends a contact form email asynchronously.
     * To: Prashant (Owner)
     */
    @Async
    public void sendContactEmail(ContactRequestDto request) {
        try {
            // 1. Send notification to Prashant (Owner)
            MimeMessage ownerMessage = mailSender.createMimeMessage();
            MimeMessageHelper ownerHelper = new MimeMessageHelper(ownerMessage, false, "UTF-8");
            ownerHelper.setFrom(emailConfig.getFromEmail());
            ownerHelper.setReplyTo(new InternetAddress(request.getEmail(), request.getName()));
            ownerHelper.setTo(emailConfig.getToEmail());
            ownerHelper.setSubject("📬 Portfolio: " + request.getName() + " | " + request.getSubject());
            ownerHelper.setText(buildOwnerEmailBody(request), false);
            mailSender.send(ownerMessage);
            
            logger.info("Notification email sent to owner for submission from: {}", request.getEmail());

            // 2. Send acknowledgement to the User (Sender)
            sendAcknowledgementEmail(request);

        } catch (Exception e) {
            logger.error("Error in email process for: {}", request.getEmail(), e);
        }
    }

    /**
     * Sends an acknowledgement email to the person who filled the form.
     */
    @Async
    private void sendAcknowledgementEmail(ContactRequestDto request) {
        try {
            MimeMessage userMessage = mailSender.createMimeMessage();
            MimeMessageHelper userHelper = new MimeMessageHelper(userMessage, false, "UTF-8");
            
            userHelper.setFrom(emailConfig.getFromEmail(), "Prashant Sharma");
            userHelper.setTo(request.getEmail());
            userHelper.setSubject("Thank you for contacting me!");
            
            String body = String.format(
                "Hi %s,\n\n" +
                "Thank you for reaching out via my portfolio website! I have received your message regarding '%s'.\n\n" +
                "I will review your message and get back to you as soon as possible.\n\n" +
                "Best Regards,\n" +
                "Prashant Sharma\n" +
                "Software Developer",
                request.getName(),
                request.getSubject()
            );
            
            userHelper.setText(body, false);
            mailSender.send(userMessage);
            logger.info("Acknowledgement email sent to user: {}", request.getEmail());
        } catch (Exception e) {
            logger.error("Failed to send acknowledgement email to: {}", request.getEmail(), e);
        }
    }

    /**
     * Builds a clear, well-formatted email body for the owner.
     */
    private String buildOwnerEmailBody(ContactRequestDto request) {
        return String.format(
            "============================================\n" +
            "  NEW MESSAGE FROM YOUR PORTFOLIO WEBSITE  \n" +
            "============================================\n\n" +
            "  Name    : %s\n" +
            "  Email   : %s\n" +
            "  Subject : %s\n\n" +
            "--------------------------------------------\n" +
            "  Message :\n\n" +
            "  %s\n\n" +
            "--------------------------------------------\n" +
            "  Reply directly to this email to respond\n" +
            "  to %s at %s\n" +
            "============================================\n",
            request.getName(),
            request.getEmail(),
            request.getSubject(),
            request.getMessage(),
            request.getName(),
            request.getEmail()
        );
    }
}

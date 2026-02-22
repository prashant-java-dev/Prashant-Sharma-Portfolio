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
 * Reply-To is set to the user's email so Prashant can directly reply to them.
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
     * Sends a contact form email.
     * From    : system email (required by Gmail SMTP)
     * Reply-To: user's email (so Prashant's reply goes directly to the sender)
     */
    @Async
    public void sendContactEmail(ContactRequestDto request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            // From must be the authenticated Gmail account
            helper.setFrom(emailConfig.getFromEmail());

            // ✅ Reply-To = user's email — clicking "Reply" will open user's email
            helper.setReplyTo(new InternetAddress(request.getEmail(), request.getName()));

            // Send to Prashant's inbox
            helper.setTo(emailConfig.getToEmail());

            // Subject clearly shows who sent it
            helper.setSubject("📬 " + request.getName() + " | " + request.getSubject());

            // Email body
            helper.setText(buildEmailBody(request), false);

            mailSender.send(mimeMessage);
            logger.info("Contact email sent. Reply-To set to: {}", request.getEmail());

        } catch (MessagingException e) {
            logger.error("Failed to send contact email from: {}", request.getEmail(), e);
            throw new BusinessException("Failed to send email. Please try again later.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Builds a clear, well-formatted email body.
     */
    private String buildEmailBody(ContactRequestDto request) {
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

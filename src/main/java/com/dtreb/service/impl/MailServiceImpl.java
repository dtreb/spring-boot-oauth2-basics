package com.dtreb.service.impl;

import com.dtreb.service.IMailService;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.util.ConfigLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * {@link IMailService} implementation.
 *
 * @author dtreb
 */
@Service
public class MailServiceImpl implements IMailService {

    private static final String MAIL_CONFIG_FILE = "mail.properties";

    @Override
    public void sendActivationEmail(String recipientEmail, String activationKey) throws IOException, URISyntaxException {
        ConfigLoader.loadProperties(MAIL_CONFIG_FILE, true);
        Email email = new EmailBuilder()
                .to(recipientEmail, recipientEmail)
                .subject(ACTIVATION_MAIL_SUBJECT)
                .textHTML(readTemplate(ACTIVATION_MAIL_TEMPLATE)
                        .replace("{recipientEmail}", recipientEmail)
                        .replace("{activationKey}", activationKey))
                .build();
        new Mailer().sendMail(email);
    }

    @Override
    public void sendResetPasswordEmail(String recipientEmail, String resetPasswordKey) throws IOException, URISyntaxException {
        ConfigLoader.loadProperties(MAIL_CONFIG_FILE, true);
        Email email = new EmailBuilder()
                .to(recipientEmail, recipientEmail)
                .subject(RESET_PASSWORD_MAIL_SUBJECT)
                .textHTML(readTemplate(RESET_PASSWORD_MAIL_TEMPLATE)
                        .replace("{recipientEmail}", recipientEmail)
                        .replace("{resetPasswordKey}", resetPasswordKey))
                .build();
        new Mailer().sendMail(email);
    }

    private String readTemplate(String templateFileName) throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getResource(templateFileName).toURI());
        return new String(Files.readAllBytes(path));
    }
}

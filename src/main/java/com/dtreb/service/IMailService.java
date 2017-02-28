package com.dtreb.service;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Allows to send registration related emails.
 *
 * @author dtreb
 */
public interface IMailService {

    String ACTIVATION_MAIL_SUBJECT = "Activation";
    String RESET_PASSWORD_MAIL_SUBJECT = "Reset Password";
    String ACTIVATION_MAIL_TEMPLATE = "/mail/activation.html";
    String RESET_PASSWORD_MAIL_TEMPLATE = "/mail/reset_password.html";

    /**
     * Sends activation email to the newly registered user.
     * @param recipientEmail newly registered user email
     * @param activationKey activation key generated during registration
     * @throws IOException mail service exception
     * @throws URISyntaxException mail service exception
     */
    void sendActivationEmail(String recipientEmail, String activationKey) throws IOException, URISyntaxException;

    /**
     * Sends reset password email that contains link to the static reset password page.
     * @param recipientEmail registered user email
     * @param resetPasswordKey reset password key generated on user request
     * @throws IOException mail service exception
     * @throws URISyntaxException mail service exception
     */
    void sendResetPasswordEmail(String recipientEmail, String resetPasswordKey) throws IOException, URISyntaxException;
}

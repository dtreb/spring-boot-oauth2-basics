package com.dtreb.service;

import com.dtreb.domain.User;
import com.dtreb.exception.UserAlreadyExistsException;
import com.dtreb.exception.UserNotFoundException;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Provides useful method for the registration workflow and general user related tools.
 *
 * @author dtreb
 */
public interface IUserService {

    /**
     * Registers new user. Requires further activation to be able to login with this user.
     * @param login unique username
     * @param password password
     * @param email email
     * @return created {@link User} entity
     * @throws UserAlreadyExistsException specified login is already used by another user
     * @throws IOException mail service exception
     * @throws URISyntaxException mail service exception
     */
    User registerUser(String login, String password, String email) throws UserAlreadyExistsException, IOException, URISyntaxException;

    /**
     * Retrieves security related user details for the current user.
     * @return {@link org.springframework.security.core.userdetails.User}
     */
    org.springframework.security.core.userdetails.User getUserDetailsUser();

    /**
     * Retrieves current user info.
     * @return {@link User}
     */
    User getCurrentUser();

    /**
     * Activates registered user and allows to use credentials to access application.
     * @param email registered user email
     * @param activationKey activation key generated during registration
     * @return activated {@link User}
     * @throws UserNotFoundException can not find correspondent registered user
     */
    User activateUser(String email, String activationKey) throws UserNotFoundException;

    /**
     * Sends reset password email to the specified user and generates key that can be used for the new password setup.
     * @param email registered user email
     * @return {@link User} with generated reset password key
     * @throws UserNotFoundException can not find correspondent registered user
     * @throws IOException mail service exception
     * @throws URISyntaxException mail service exception
     */
    User lostPassword(String email) throws UserNotFoundException, IOException, URISyntaxException;

    /**
     * Resets password of the registered user using key generated in {@link IUserService#lostPassword(String)}.
     * @param email registered user email
     * @param newPassword new password
     * @param resetPasswordKey reset password key
     * @return {@link User} with updated password
     * @throws UserNotFoundException can not find correspondent registered user
     */
    User resetPassword(String email, String newPassword, String resetPasswordKey) throws UserNotFoundException;
}

package com.dtreb.controller;

import com.dtreb.domain.User;
import com.dtreb.exception.UserAlreadyExistsException;
import com.dtreb.exception.UserNotFoundException;
import com.dtreb.service.IUserService;
import com.dtreb.util.DTOUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Registration workflow related controller.
 *
 * @author dtreb
 */
@RestController
@Validated
public class RegisterController {

    @Autowired
    private IUserService userService;

    /**
     * Registers new user. User requires activation in order to be able to login.
     * @param login new user login (unique value)
     * @param password new user password
     * @param email new user email
     * @return registered {@link User}
     * @throws UserAlreadyExistsException specified login is already used by another user
     * @throws IOException mail service exception
     * @throws URISyntaxException mail service exception
     */
    @RequestMapping(path = "register", method = RequestMethod.POST)
    public User register(@NotBlank @Length(max = 50) String login,
                         @NotBlank String password,
                         @NotBlank @Email @Length(max = 50) String email)
            throws UserAlreadyExistsException, IOException, URISyntaxException {
        return DTOUtils.stripUser(getUserService().registerUser(login, password, email));
    }

    /**
     * Activates registered user.
     * @param email user email
     * @param activationKey activation key assigned during registration
     * @return activated {@link User}
     * @throws UserNotFoundException can not find correspondent registered user
     */
    @RequestMapping(path = "activate")
    public String activate(@NotBlank @Email @Length(max = 50) String email,
                           @NotBlank String activationKey) throws UserNotFoundException {
        getUserService().activateUser(email, activationKey);
        return "Thanks for activation! You can login now.";
    }

    /**
     * Sends reset password email for the user by email. Assigns reset password key.
     * @param email user email
     * @throws UserNotFoundException can not find correspondent registered user
     * @throws IOException mail service exception
     * @throws URISyntaxException mail service exception
     */
    @RequestMapping(path = "lostPassword")
    public void lostPassword(@NotBlank @Email @Length(max = 50) String email)
            throws UserNotFoundException, IOException, URISyntaxException {
        User user = getUserService().lostPassword(email);
        user.getResetPasswordKey();
    }

    /**
     * Reset password of the existing user.
     * @param email existing user email
     * @param newPassword new password
     * @param resetPasswordKey reset password key assigned during previous reset password call
     * @return ok message to be shown in UI
     * @throws UserNotFoundException can not find correspondent registered user
     */
    @RequestMapping(path = "resetPassword")
    public String resetPassword(@NotBlank @Email @Length(max = 50) String email,
                                @NotBlank String newPassword,
                                @NotBlank String resetPasswordKey) throws UserNotFoundException {
        getUserService().resetPassword(email, newPassword, resetPasswordKey);
        return "Your password has been updated. Try to login now.";
    }

    public IUserService getUserService() {
        return userService;
    }
}

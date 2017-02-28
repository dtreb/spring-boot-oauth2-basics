package com.dtreb.service.impl;

import com.dtreb.domain.Authority;
import com.dtreb.domain.Role;
import com.dtreb.domain.User;
import com.dtreb.repository.AuthorityRepository;
import com.dtreb.repository.UserRepository;
import com.dtreb.service.IMailService;
import com.dtreb.util.SecurityUtils;
import com.dtreb.exception.UserAlreadyExistsException;
import com.dtreb.exception.UserNotFoundException;
import com.dtreb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link IUserService} implementation.
 *
 * @author dtreb
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private IMailService mailService;

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public org.springframework.security.core.userdetails.User getUserDetailsUser() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public User getCurrentUser() {
        return getUserRepository().findByUsernameCaseInsensitive(getUserDetailsUser().getUsername());
    }

    @Override
    public User registerUser(String login, String password, String email) throws UserAlreadyExistsException, IOException, URISyntaxException {
        if ((getUserRepository().findByUsernameCaseInsensitive(login) != null) &&
            (getUserRepository().findByEmail(email) != null)) {
            throw new UserAlreadyExistsException("User with '" + login + "' login or '"
                    + email + "' email already exists.");
        }
        Set<Authority> authorities = new HashSet();
        authorities.add(getAuthorityRepository().findByName(Role.ROLE_USER.toString()));

        User user = new User()
                .setEmail(email)
                .setActivationKey(SecurityUtils.generateKey())
                .setActivated(true)
                .setAuthorities(authorities)
                .setPassword(getPasswordEncoder().encode(password))
                .setUsername(login);

        getMailService().sendActivationEmail(user.getEmail(), user.getActivationKey());

        return getUserRepository().save(user);
    }

    @Override
    public User activateUser(String email, String activationKey) throws UserNotFoundException {
        User user = getUserRepository().findByEmailAndActivationKey(email, activationKey);
        if (user == null) {
            throw new UserNotFoundException("Can't find user with '" + email + "' email " +
                    "and '" + activationKey + "' activation key.");
        }
        return getUserRepository().save(user.setActivated(true));
    }

    @Override
    public User lostPassword(String email) throws UserNotFoundException, IOException, URISyntaxException {
        User user = getUserRepository().findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("Can't find user with '" + email + "' email.");
        }
        user.setResetPasswordKey(getPasswordEncoder().encode(SecurityUtils.generateKey()));

        getMailService().sendResetPasswordEmail(user.getEmail(), user.getResetPasswordKey());

        return getUserRepository().save(user);
    }

    @Override
    public User resetPassword(String email, String newPassword, String resetPasswordKey) throws UserNotFoundException {
        User user = getUserRepository().findByEmailAndResetPasswordKey(email, resetPasswordKey);
        if (user == null) {
            throw new UserNotFoundException("Can't find user with '" + email + "' email " +
                    "and '" + resetPasswordKey + "' reset password key.");
        }
        return getUserRepository().save(
                user.setPassword(getPasswordEncoder().encode(newPassword))
                    .setResetPasswordKey(null));
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public AuthorityRepository getAuthorityRepository() {
        return authorityRepository;
    }

    public IMailService getMailService() {
        return mailService;
    }
}

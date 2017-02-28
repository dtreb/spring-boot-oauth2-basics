package com.dtreb.util;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

/**
 * Provides useful tools to work with Spring security layer.
 *
 * @author dtreb
 */
public class SecurityUtils {

    /**
     * Generate unique key to be used as activation or reset password key etc.
     * @return unique key
     */
    public static String generateKey() {
        return UUID.randomUUID().toString();
    }

    /**
     * Returns currently logged-in user name out of the security context.
     * @return currently logged-in user name
     */
    public static Optional<String> getCurrentUserName() {
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            return Optional.of(user.getUsername());
        }
        return Optional.empty();
    }
}

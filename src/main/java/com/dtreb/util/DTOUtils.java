package com.dtreb.util;

import com.dtreb.domain.User;

/**
 * Provides useful tools to works with responses entities.
 *
 * @author dtreb
 */
public class DTOUtils {

    /**
     * Strips security related data of the {@link User} entity.
     * @param user {@link User}
     * @return updated {@link User} entity
     */
    public static User stripUser(User user) {
        if (user != null) {
            user.setActivationKey(null);
            user.setPassword(null);
            user.setResetPasswordKey(null);
        }
        return user;
    }
}

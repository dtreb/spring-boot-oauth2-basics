package com.dtreb.controller;

import com.dtreb.domain.User;
import com.dtreb.service.IUserService;
import com.dtreb.util.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * User related controller.
 *
 * @author dtreb
 */
@RestController
@Validated
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * Returns current user info (if logged in).
     * Information about passwords and keys is omitted.
     * @return {@link User}
     */
    @RequestMapping(method = RequestMethod.GET)
    public User getCurrentUser() {
        return DTOUtils.stripUser(userService.getCurrentUser());
    }

    public IUserService getUserService() {
        return userService;
    }
}

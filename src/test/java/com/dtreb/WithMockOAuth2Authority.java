package com.dtreb;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Custom annotation that allows to pass OAuth2 related authorities to run security related tests.
 *
 * @author dtreb
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockOAuth2AuthoritySecurityContextFactory.class)
public @interface WithMockOAuth2Authority {

    /**
     * OAuth2 authority.
     * @return OAuth2 authority
     */
    String authority() default "";
}
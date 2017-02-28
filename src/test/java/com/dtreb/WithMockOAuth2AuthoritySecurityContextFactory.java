package com.dtreb;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Allows to create custom security context and related OAuth2 credentials to run security tests.
 *
 * @author dtreb
 */
public class WithMockOAuth2AuthoritySecurityContextFactory implements WithSecurityContextFactory<WithMockOAuth2Authority> {

    /**
     * Provides {@link WithMockOAuth2Authority} for security context.
     * @param mockOAuth2Authority {@link WithMockOAuth2Authority}
     * @return {@link SecurityContext} with provided authentication info
     */
    @Override
    public SecurityContext createSecurityContext(WithMockOAuth2Authority mockOAuth2Authority) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Set<GrantedAuthority> authority = new HashSet<>();
        authority.add(new SimpleGrantedAuthority(mockOAuth2Authority.authority()));

        OAuth2Request request = new OAuth2Request(null, null, authority, true, null, null, null, null, null);
        Authentication auth = new OAuth2Authentication(request, null);
        context.setAuthentication(auth);

        return context;
    }
}
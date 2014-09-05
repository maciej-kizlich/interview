package com.epam.library.user.mock.factory;

import com.epam.library.security.authority.ExtendedAuthorityUtils;
import com.epam.library.user.mock.annotation.WithMockCustomUser;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.StringUtils;

import pl.maciejkizlich.interview.security.EpamUserDetails;
import pl.maciejkizlich.interview.security.UserPrincipal;

import java.lang.annotation.Annotation;
import java.util.List;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<Annotation> {

    @Override
    public SecurityContext createSecurityContext(Annotation customUser) {

        WithMockCustomUser annotatedUser = extractCustomUserData(customUser);

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        String username = StringUtils.hasLength(annotatedUser.username()) ? annotatedUser.username() : annotatedUser.value();
        if (username == null) {
            throw new IllegalArgumentException(customUser + " cannot have null username on both username and value properites");
        }

        String[] roleStrings = annotatedUser.roles();
        List<GrantedAuthority> authorities = ExtendedAuthorityUtils.stringArrayToAuthorityList(roleStrings);

        UserPrincipal principal =
                new UserPrincipal(username, "123456", authorities, annotatedUser.id());
        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }

    private WithMockCustomUser extractCustomUserData(Annotation customUser) {
        if (customUser instanceof WithMockCustomUser) {
            return (WithMockCustomUser) customUser;
        }
        WithMockCustomUser annotatedUser = customUser.annotationType().getAnnotation(WithMockCustomUser.class);
        if (annotatedUser == null) {
            throw new IllegalStateException("Class "+ customUser.getClass().getSimpleName() + " is not annotated with " + WithMockCustomUser.class);
        }
        return annotatedUser;
    }
}
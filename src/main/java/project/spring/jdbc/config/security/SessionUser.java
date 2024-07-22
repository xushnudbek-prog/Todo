package project.spring.jdbc.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import project.spring.jdbc.domains.AuthUser;

@Component
public class SessionUser {

    public AuthUser getUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();
        return userDetails.authUser();
    }
}

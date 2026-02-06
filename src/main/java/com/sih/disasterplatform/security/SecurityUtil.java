package com.sih.disasterplatform.security;

import com.sih.disasterplatform.security.auth.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
    }

    private static UserPrincipal getPrincipal() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal)) {
            return null;
        }

        return (UserPrincipal) authentication.getPrincipal();
    }

    public static Long getCurrentUserId() {
        UserPrincipal principal = getPrincipal();
        return principal != null ? principal.getUserId() : null;
    }

    public static Long getCurrentSchoolId() {
        UserPrincipal principal = getPrincipal();
        return principal != null ? principal.getSchoolId() : null;
    }

    public static boolean isIndividualUser() {
        UserPrincipal principal = getPrincipal();
        return principal != null && principal.getRole().name().equals("INDIVIDUAL");
    }
}

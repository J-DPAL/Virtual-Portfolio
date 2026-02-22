package com.portfolio.monolith.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

  private SecurityUtils() {}

  public static AuthenticatedUser getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser)) {
      return null;
    }
    return (AuthenticatedUser) authentication.getPrincipal();
  }

  public static boolean isAdmin() {
    AuthenticatedUser user = getCurrentUser();
    return user != null && user.isAdmin();
  }
}

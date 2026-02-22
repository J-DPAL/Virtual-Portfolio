package com.portfolio.monolith.security;

public class AuthenticatedUser {
  private final String id;
  private final String email;
  private final String fullName;
  private final boolean admin;

  public AuthenticatedUser(String id, String email, String fullName, boolean admin) {
    this.id = id;
    this.email = email;
    this.fullName = fullName;
    this.admin = admin;
  }

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getFullName() {
    return fullName;
  }

  public boolean isAdmin() {
    return admin;
  }
}

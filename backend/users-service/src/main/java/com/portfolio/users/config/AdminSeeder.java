package com.portfolio.users.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.portfolio.users.dataAccessLayer.entity.User;
import com.portfolio.users.dataAccessLayer.repository.UserRepository;

/**
 * Ensures an admin user exists based on environment variables.
 *
 * <p>We can't rely on a one-time Flyway migration for admin credentials because Flyway won't
 * re-apply versioned migrations when env vars change. This runner keeps admin credentials
 * updateable via Render env vars.</p>
 */
@Component
public class AdminSeeder implements ApplicationRunner {

  private static final Logger log = LoggerFactory.getLogger(AdminSeeder.class);

  private final UserRepository userRepository;

  @Value("${spring.flyway.placeholders.admin_email:}")
  private String adminEmail;

  @Value("${spring.flyway.placeholders.admin_password_hash:}")
  private String adminPasswordHash;

  @Value("${spring.flyway.placeholders.admin_full_name:Admin User}")
  private String adminFullName;

  public AdminSeeder(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void run(ApplicationArguments args) {
    if (adminEmail == null || adminEmail.isBlank()) {
      log.info("AdminSeeder: ADMIN_EMAIL not set; skipping admin seeding.");
      return;
    }
    if (adminPasswordHash == null || adminPasswordHash.isBlank()) {
      log.info("AdminSeeder: ADMIN_PASSWORD_HASH not set; skipping admin seeding.");
      return;
    }
    if (!isLikelyBcryptHash(adminPasswordHash)) {
      log.warn(
          "AdminSeeder: ADMIN_PASSWORD_HASH does not look like a bcrypt hash; skipping admin seeding.");
      return;
    }

    Optional<User> existing = userRepository.findByEmail(adminEmail.trim());
    User user =
        existing.orElseGet(
            () ->
                User.builder()
                    .email(adminEmail.trim())
                    .password(adminPasswordHash)
                    .fullName(defaultName(adminFullName))
                    .role(User.UserRole.ADMIN)
                    .active(true)
                    .build());

    user.setPassword(adminPasswordHash);
    user.setFullName(defaultName(adminFullName));
    user.setRole(User.UserRole.ADMIN);
    user.setActive(true);

    userRepository.save(user);
    log.info("AdminSeeder: ensured admin user exists for email={}", adminEmail.trim());
  }

  private static boolean isLikelyBcryptHash(String hash) {
    // BCrypt hashes typically start with $2a$, $2b$, or $2y$
    return hash.startsWith("$2a$") || hash.startsWith("$2b$") || hash.startsWith("$2y$");
  }

  private static String defaultName(String name) {
    if (name == null || name.isBlank()) {
      return "Admin User";
    }
    return name;
  }
}


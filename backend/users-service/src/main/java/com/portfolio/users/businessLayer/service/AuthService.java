package com.portfolio.users.businessLayer.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.portfolio.users.dataAccessLayer.entity.User;
import com.portfolio.users.dataAccessLayer.repository.UserRepository;
import com.portfolio.users.mappingLayer.dto.LoginRequest;
import com.portfolio.users.mappingLayer.dto.LoginResponse;
import com.portfolio.users.mappingLayer.dto.UserDTO;
import com.portfolio.users.mappingLayer.mapper.UserMapper;
import com.portfolio.users.security.JwtTokenProvider;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  public AuthService(
      UserRepository userRepository,
      UserMapper userMapper,
      PasswordEncoder passwordEncoder,
      JwtTokenProvider jwtTokenProvider) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  public LoginResponse login(LoginRequest loginRequest) {
    User user =
        userRepository
            .findByEmail(loginRequest.getEmail())
            .orElseThrow(
                // Avoid leaking whether the email exists
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

    if (!Boolean.TRUE.equals(user.getActive())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User account is inactive");
    }

    String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().toString());

    return LoginResponse.builder()
        .token(token)
        .user(userMapper.toDTO(user))
        .message("Login successful")
        .build();
  }

  public UserDTO getCurrentUser(String email) {
    User user =
        userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    return userMapper.toDTO(user);
  }
}

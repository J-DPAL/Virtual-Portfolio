package com.portfolio.businessLogicLayer.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.portfolio.dataAccessLayer.entity.User;
import com.portfolio.dataAccessLayer.repository.UserRepository;
import com.portfolio.mappingLayer.dto.LoginRequest;
import com.portfolio.mappingLayer.dto.LoginResponse;
import com.portfolio.mappingLayer.mapper.UserMapper;
import com.portfolio.security.JwtTokenProvider;

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
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      throw new RuntimeException("Invalid password");
    }

    if (!user.getActive()) {
      throw new RuntimeException("User account is inactive");
    }

    String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().toString());

    return LoginResponse.builder()
        .token(token)
        .user(userMapper.toDTO(user))
        .message("Login successful")
        .build();
  }
}

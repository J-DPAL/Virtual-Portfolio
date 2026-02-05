package com.portfolio.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RateLimitFilter Unit Tests")
class RateLimitFilterTest {

  @Test
  @DisplayName("Should allow requests under limit and block when exceeded")
  void testRateLimit_ForMessagesEndpoint() throws Exception {
    // Arrange: Initialize filter and request
    RateLimitFilter filter = new RateLimitFilter();

    // Act: Perform 5 allowed requests
    for (int i = 0; i < 5; i++) {
      MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/messages");
      request.setRemoteAddr("127.0.0.1");
      MockHttpServletResponse response = new MockHttpServletResponse();
      MockFilterChain chain = new MockFilterChain();
      filter.doFilter(request, response, chain);
      assertNotEquals(429, response.getStatus());
    }

    // Act: Sixth request should be blocked
    MockHttpServletRequest blockedRequest = new MockHttpServletRequest("POST", "/api/messages");
    blockedRequest.setRemoteAddr("127.0.0.1");
    MockHttpServletResponse blockedResponse = new MockHttpServletResponse();
    MockFilterChain blockedChain = new MockFilterChain();
    filter.doFilter(blockedRequest, blockedResponse, blockedChain);

    // Assert: Verify rate limit triggered
    assertEquals(429, blockedResponse.getStatus());
    assertTrue(blockedResponse.getContentAsString().contains("Too many requests"));
  }

  @Test
  @DisplayName("Should allow GET requests without rate limiting")
  void testRateLimit_ForGetRequest_Allows() throws Exception {
    // Arrange: Initialize filter and request
    RateLimitFilter filter = new RateLimitFilter();

    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/messages");
    request.setRemoteAddr("127.0.0.1");
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockFilterChain chain = new MockFilterChain();

    // Act: Perform GET request
    filter.doFilter(request, response, chain);

    // Assert: Verify request not blocked
    assertNotEquals(429, response.getStatus());
  }
}

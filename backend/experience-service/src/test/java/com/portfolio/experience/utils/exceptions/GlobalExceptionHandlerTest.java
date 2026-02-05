package com.portfolio.experience.utils.exceptions;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.portfolio.experience.mappingLayer.dto.WorkExperienceDTO;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GlobalExceptionHandler Tests")
class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @Test
  @DisplayName("Should handle ResourceNotFoundException")
  void testHandleResourceNotFoundException() {
    ResourceNotFoundException ex = new ResourceNotFoundException("Not found");

    ResponseEntity<ErrorResponse> response = handler.handleResourceNotFoundException(ex);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
    assertEquals("Not found", response.getBody().getMessage());
    assertNotNull(response.getBody().getTimestamp());
  }

  @Test
  @DisplayName("Should handle MethodArgumentNotValidException")
  void testHandleValidationExceptions() throws NoSuchMethodException {
    WorkExperienceDTO dto = new WorkExperienceDTO();
    BindingResult bindingResult = new BeanPropertyBindingResult(dto, "workExperienceDTO");
    bindingResult.addError(
        new FieldError("workExperienceDTO", "companyNameEn", "Company name is required"));
    bindingResult.addError(
        new FieldError("workExperienceDTO", "positionEn", "Position is required"));

    MethodParameter parameter =
        new MethodParameter(
            GlobalExceptionHandlerTest.class.getDeclaredMethod(
                "dummyMethod", WorkExperienceDTO.class),
            0);

    MethodArgumentNotValidException ex =
        new MethodArgumentNotValidException(parameter, bindingResult);

    ResponseEntity<ErrorResponse> response = handler.handleValidationExceptions(ex);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    assertEquals("Validation failed", response.getBody().getMessage());
    assertNotNull(response.getBody().getTimestamp());
    Map<String, String> errors = response.getBody().getErrors();
    assertNotNull(errors);
    assertEquals(2, errors.size());
    assertEquals("Company name is required", errors.get("companyNameEn"));
    assertEquals("Position is required", errors.get("positionEn"));
  }

  @Test
  @DisplayName("Should handle validation with no field errors")
  void testHandleValidationExceptions_NoErrors() throws NoSuchMethodException {
    WorkExperienceDTO dto = new WorkExperienceDTO();
    BindingResult bindingResult = new BeanPropertyBindingResult(dto, "workExperienceDTO");

    MethodParameter parameter =
        new MethodParameter(
            GlobalExceptionHandlerTest.class.getDeclaredMethod(
                "dummyMethod", WorkExperienceDTO.class),
            0);

    MethodArgumentNotValidException ex =
        new MethodArgumentNotValidException(parameter, bindingResult);

    ResponseEntity<ErrorResponse> response = handler.handleValidationExceptions(ex);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getErrors());
    assertTrue(response.getBody().getErrors().isEmpty());
    assertNotNull(response.getBody().getTimestamp());
  }

  @Test
  @DisplayName("Should handle generic Exception")
  void testHandleGlobalException() {
    Exception ex = new Exception("Unexpected error");

    ResponseEntity<ErrorResponse> response = handler.handleGlobalException(ex);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
    assertEquals("An unexpected error occurred", response.getBody().getMessage());
    assertNotNull(response.getBody().getTimestamp());
  }

  @SuppressWarnings("unused")
  private void dummyMethod(@SuppressWarnings("unused") WorkExperienceDTO dto) {
    // Used for MethodParameter in validation tests
  }
}

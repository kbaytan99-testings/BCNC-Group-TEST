package com.inditex.pricing.infrastructure.adapter.input.rest.exception;

import com.inditex.pricing.domain.exception.PriceNotFoundException;
import com.inditex.pricing.infrastructure.adapter.input.rest.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Global exception handler for REST API.
 * Converts exceptions to proper HTTP responses with consistent format.
 * 
 * Follows REST best practices for error handling.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Handles PriceNotFoundException.
     * Returns 404 NOT FOUND when no applicable price is found.
     * 
     * @param ex PriceNotFoundException
     * @return ResponseEntity with ErrorResponse and 404 status
     */
    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePriceNotFoundException(PriceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                "Price Not Found",
                ex.getMessage(),
                LocalDateTime.now().format(TIMESTAMP_FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handles invalid request parameters.
     * Returns 400 BAD REQUEST for malformed parameters.
     * 
     * @param ex MethodArgumentTypeMismatchException
     * @return ResponseEntity with ErrorResponse and 400 status
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Invalid parameter '%s': %s", ex.getName(), ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                "Bad Request",
                message,
                LocalDateTime.now().format(TIMESTAMP_FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handles missing required parameters.
     * Returns 400 BAD REQUEST when required parameters are missing.
     * 
     * @param ex MissingServletRequestParameterException
     * @return ResponseEntity with ErrorResponse and 400 status
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        String message = String.format("Required parameter '%s' is missing", ex.getParameterName());
        ErrorResponse error = new ErrorResponse(
                "Bad Request",
                message,
                LocalDateTime.now().format(TIMESTAMP_FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handles constraint violations from validation.
     * Returns 400 BAD REQUEST when validation fails.
     * 
     * @param ex ConstraintViolationException
     * @return ResponseEntity with ErrorResponse and 400 status
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        ErrorResponse error = new ErrorResponse(
                "Bad Request",
                ex.getMessage(),
                LocalDateTime.now().format(TIMESTAMP_FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handles unexpected exceptions.
     * Returns 500 INTERNAL SERVER ERROR for unhandled errors.
     * 
     * @param ex Exception
     * @return ResponseEntity with ErrorResponse and 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "Internal Server Error",
                "An unexpected error occurred: " + ex.getMessage(),
                LocalDateTime.now().format(TIMESTAMP_FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

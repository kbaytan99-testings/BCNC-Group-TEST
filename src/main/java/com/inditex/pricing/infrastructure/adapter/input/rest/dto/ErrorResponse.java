package com.inditex.pricing.infrastructure.adapter.input.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error response DTO for API exceptions.
 * Provides consistent error format across the API.
 */
public class ErrorResponse {

    @JsonProperty("error")
    private String error;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private String timestamp;

    public ErrorResponse() {
    }

    public ErrorResponse(String error, String message, String timestamp) {
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

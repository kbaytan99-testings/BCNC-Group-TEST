package com.inditex.pricing.domain.exception;

/**
 * Exception thrown when a price is not found for the given criteria.
 * This is a domain exception that represents a business rule violation.
 */
public class PriceNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "No applicable price found for the given criteria";

    public PriceNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public PriceNotFoundException(String message) {
        super(message);
    }

    public PriceNotFoundException(Long productId, Long brandId, String applicationDate) {
        super(String.format("No applicable price found for productId=%d, brandId=%d, applicationDate=%s",
                productId, brandId, applicationDate));
    }
}

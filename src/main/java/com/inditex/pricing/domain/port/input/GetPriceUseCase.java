package com.inditex.pricing.domain.port.input;

import com.inditex.pricing.domain.model.Price;

import java.time.LocalDateTime;

/**
 * Input port (use case interface) for querying prices.
 * This defines the contract for the application service.
 * 
 * Part of hexagonal architecture - defines the API for the domain.
 */
public interface GetPriceUseCase {

    /**
     * Retrieves the applicable price for a product at a specific date.
     * When multiple prices overlap, the one with highest priority is returned.
     * 
     * @param applicationDate Date and time to apply the price query
     * @param productId Product identifier
     * @param brandId Brand identifier
     * @return The applicable Price
     * @throws com.inditex.pricing.domain.exception.PriceNotFoundException if no price is found
     */
    Price getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}

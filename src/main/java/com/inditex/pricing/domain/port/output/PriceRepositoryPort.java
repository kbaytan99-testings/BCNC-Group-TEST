package com.inditex.pricing.domain.port.output;

import com.inditex.pricing.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Output port (repository interface) for price persistence.
 * This defines the contract that the infrastructure layer must implement.
 * 
 * Part of hexagonal architecture - domain defines what it needs from infrastructure.
 */
public interface PriceRepositoryPort {

    /**
     * Finds the applicable price with the highest priority for the given criteria.
     * 
     * @param applicationDate Date and time to apply the price query
     * @param productId Product identifier
     * @param brandId Brand identifier
     * @return Optional containing the Price if found, empty otherwise
     */
    Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}

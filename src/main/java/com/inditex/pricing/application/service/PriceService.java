package com.inditex.pricing.application.service;

import com.inditex.pricing.domain.exception.PriceNotFoundException;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.port.input.GetPriceUseCase;
import com.inditex.pricing.domain.port.output.PriceRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Application service that implements the GetPriceUseCase.
 * This is the orchestration layer between domain and infrastructure.
 * 
 * Follows the Single Responsibility Principle - only handles price query use case.
 */
@Service
public class PriceService implements GetPriceUseCase {

    private final PriceRepositoryPort priceRepositoryPort;

    /**
     * Constructor injection for dependency inversion.
     * Depends on abstractions (ports), not concrete implementations.
     * 
     * @param priceRepositoryPort Port for price persistence operations
     */
    public PriceService(PriceRepositoryPort priceRepositoryPort) {
        this.priceRepositoryPort = priceRepositoryPort;
    }

    /**
     * {@inheritDoc}
     * 
     * Business logic: Query the repository for the applicable price.
     * The repository is responsible for applying priority rules.
     */
    @Override
    public Price getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepositoryPort.findApplicablePrice(applicationDate, productId, brandId)
                .orElseThrow(() -> new PriceNotFoundException(productId, brandId, applicationDate.toString()));
    }
}

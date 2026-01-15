package com.inditex.pricing.infrastructure.adapter.output.persistence;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.port.output.PriceRepositoryPort;
import com.inditex.pricing.infrastructure.adapter.output.persistence.mapper.PriceMapper;
import com.inditex.pricing.infrastructure.adapter.output.persistence.repository.JpaPriceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Adapter that implements the PriceRepositoryPort using JPA.
 * This is the bridge between domain (port) and infrastructure (JPA repository).
 * 
 * Follows the Adapter pattern from hexagonal architecture.
 */
@Component
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final JpaPriceRepository jpaPriceRepository;
    private final PriceMapper priceMapper;

    /**
     * Constructor injection for dependencies.
     * 
     * @param jpaPriceRepository JPA repository for database access
     * @param priceMapper Mapper to convert between entity and domain model
     */
    public PriceRepositoryAdapter(JpaPriceRepository jpaPriceRepository, PriceMapper priceMapper) {
        this.jpaPriceRepository = jpaPriceRepository;
        this.priceMapper = priceMapper;
    }

    /**
     * {@inheritDoc}
     * 
     * Delegates to JPA repository and maps the result to domain model.
     */
    @Override
    public Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return jpaPriceRepository.findApplicablePrice(applicationDate, productId, brandId)
                .map(priceMapper::toDomain);
    }
}

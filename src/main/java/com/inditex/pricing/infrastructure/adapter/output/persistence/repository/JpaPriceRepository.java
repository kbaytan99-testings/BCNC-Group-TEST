package com.inditex.pricing.infrastructure.adapter.output.persistence.repository;

import com.inditex.pricing.infrastructure.adapter.output.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Spring Data JPA repository for PriceEntity.
 * Provides efficient data access with custom query for price lookup.
 */
@Repository
public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {

    /**
     * Finds the applicable price with the highest priority for the given criteria.
     * 
     * Query strategy:
     * 1. Filters by productId and brandId
     * 2. Checks if applicationDate is within the date range
     * 3. Orders by priority descending (highest first)
     * 4. Returns only the first result
     * 
     * This ensures efficient execution with proper indexing on PRODUCT_ID, BRAND_ID, and dates.
     * 
     * @param applicationDate Date and time to check
     * @param productId Product identifier
     * @param brandId Brand identifier
     * @return Optional containing the PriceEntity with highest priority if found
     */
    @Query("SELECT p FROM PriceEntity p " +
           "WHERE p.productId = :productId " +
           "AND p.brandId = :brandId " +
           "AND :applicationDate BETWEEN p.startDate AND p.endDate " +
           "ORDER BY p.priority DESC " +
           "LIMIT 1")
    Optional<PriceEntity> findApplicablePrice(
            @Param("applicationDate") LocalDateTime applicationDate,
            @Param("productId") Long productId,
            @Param("brandId") Long brandId
    );
}

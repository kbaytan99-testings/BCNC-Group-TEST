package com.inditex.pricing.infrastructure.adapter.input.rest;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.port.input.GetPriceUseCase;
import com.inditex.pricing.infrastructure.adapter.input.rest.dto.PriceResponse;
import com.inditex.pricing.infrastructure.adapter.input.rest.mapper.PriceResponseMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST Controller for price queries.
 * Provides the GET endpoint to query applicable prices.
 * 
 * Best practices:
 * - Uses GET method for query operations
 * - Clear endpoint naming (/api/prices)
 * - Query parameters for input
 * - Proper HTTP status codes
 * - JSON response format
 */
@RestController
@RequestMapping("/api/prices")
@Validated
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;
    private final PriceResponseMapper priceResponseMapper;

    /**
     * Constructor injection for dependencies.
     * 
     * @param getPriceUseCase Use case for price queries
     * @param priceResponseMapper Mapper to convert domain to DTO
     */
    public PriceController(GetPriceUseCase getPriceUseCase, PriceResponseMapper priceResponseMapper) {
        this.getPriceUseCase = getPriceUseCase;
        this.priceResponseMapper = priceResponseMapper;
    }

    /**
     * GET endpoint to query applicable price.
     * 
     * Example request:
     * GET /api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1
     * 
     * @param applicationDate Date and time for price application (ISO format)
     * @param productId Product identifier
     * @param brandId Brand identifier
     * @return ResponseEntity with PriceResponse or error
     */
    @GetMapping
    public ResponseEntity<PriceResponse> getApplicablePrice(
            @RequestParam("applicationDate") 
            @NotNull(message = "applicationDate is required")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime applicationDate,
            
            @RequestParam("productId")
            @NotNull(message = "productId is required")
            Long productId,
            
            @RequestParam("brandId")
            @NotNull(message = "brandId is required")
            Long brandId) {

        Price price = getPriceUseCase.getApplicablePrice(applicationDate, productId, brandId);
        PriceResponse response = priceResponseMapper.toResponse(price);

        return ResponseEntity.ok(response);
    }
}

package com.inditex.pricing.infrastructure.adapter.input.rest.mapper;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.infrastructure.adapter.input.rest.dto.PriceResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper to convert domain Price to REST PriceResponse DTO.
 * Separates domain model from API representation.
 */
@Component
public class PriceResponseMapper {

    /**
     * Converts a domain Price to a PriceResponse DTO.
     * 
     * @param price Domain price model
     * @return PriceResponse DTO for REST API
     */
    public PriceResponse toResponse(Price price) {
        if (price == null) {
            return null;
        }

        return new PriceResponse(
                price.getProductId(),
                price.getBrandId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice(),
                price.getCurrency()
        );
    }
}

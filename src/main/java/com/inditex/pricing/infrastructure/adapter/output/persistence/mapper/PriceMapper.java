package com.inditex.pricing.infrastructure.adapter.output.persistence.mapper;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.infrastructure.adapter.output.persistence.entity.PriceEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper to convert between PriceEntity (infrastructure) and Price (domain).
 * Follows the Dependency Inversion Principle - domain doesn't depend on infrastructure.
 * 
 * Using explicit mapper instead of MapStruct for clarity and control.
 */
@Component
public class PriceMapper {

    /**
     * Converts a PriceEntity to a domain Price model.
     * 
     * @param entity PriceEntity from database
     * @return Price domain model
     */
    public Price toDomain(PriceEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Price(
                entity.getId(),
                entity.getBrandId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriceList(),
                entity.getProductId(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurrency()
        );
    }

    /**
     * Converts a domain Price model to a PriceEntity.
     * 
     * @param price Price domain model
     * @return PriceEntity for database
     */
    public PriceEntity toEntity(Price price) {
        if (price == null) {
            return null;
        }

        return PriceEntity.builder()
                .id(price.getId())
                .brandId(price.getBrandId())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .priceList(price.getPriceList())
                .productId(price.getProductId())
                .priority(price.getPriority())
                .price(price.getPrice())
                .currency(price.getCurrency())
                .build();
    }
}

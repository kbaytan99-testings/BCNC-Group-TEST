package com.inditex.pricing.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Domain model representing a price for a product.
 * This is a core business entity in the domain layer, independent of infrastructure.
 * 
 * Immutable value object following DDD principles.
 */
public class Price {

    private final Long id;
    private final Long brandId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Long priceList;
    private final Long productId;
    private final Integer priority;
    private final BigDecimal price;
    private final String currency;

    /**
     * Constructor for Price domain model.
     * 
     * @param id Unique identifier
     * @param brandId Brand identifier
     * @param startDate Start date of price applicability
     * @param endDate End date of price applicability
     * @param priceList Price list identifier
     * @param productId Product identifier
     * @param priority Priority for price disambiguation
     * @param price Final sale price
     * @param currency Currency ISO code
     */
    public Price(Long id, Long brandId, LocalDateTime startDate, LocalDateTime endDate,
                 Long priceList, Long productId, Integer priority, BigDecimal price, String currency) {
        this.id = id;
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.priority = priority;
        this.price = price;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getPriceList() {
        return priceList;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getPriority() {
        return priority;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", brandId=" + brandId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priceList=" + priceList +
                ", productId=" + productId +
                ", priority=" + priority +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                '}';
    }
}

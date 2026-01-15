-- Schema definition for PRICES table
-- This script creates the database structure for the pricing service

DROP TABLE IF EXISTS PRICES;

CREATE TABLE PRICES (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    BRAND_ID BIGINT NOT NULL,
    START_DATE TIMESTAMP NOT NULL,
    END_DATE TIMESTAMP NOT NULL,
    PRICE_LIST BIGINT NOT NULL,
    PRODUCT_ID BIGINT NOT NULL,
    PRIORITY INTEGER NOT NULL,
    PRICE DECIMAL(10, 2) NOT NULL,
    CURR VARCHAR(3) NOT NULL
);

-- Create indexes for efficient querying
-- These indexes optimize the main query (by productId, brandId, and date range)
CREATE INDEX idx_product_brand ON PRICES(PRODUCT_ID, BRAND_ID);
CREATE INDEX idx_dates ON PRICES(START_DATE, END_DATE);
CREATE INDEX idx_priority ON PRICES(PRIORITY);

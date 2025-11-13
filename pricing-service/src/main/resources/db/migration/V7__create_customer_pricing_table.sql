-- Create customer_pricing table
CREATE TABLE customer_pricing (
    id BIGINT NOT NULL AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    special_price DECIMAL(19,2) NOT NULL,
    discount_percentage DECIMAL(5,2),
    min_order_quantity INT,
    max_order_quantity INT,
    effective_date TIMESTAMP NULL,
    expiry_date TIMESTAMP NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    notes VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
    INDEX idx_customer_pricing_customer (customer_id),
    INDEX idx_customer_pricing_item (item_id),
    INDEX idx_customer_pricing_dates (effective_date, expiry_date),
    INDEX idx_customer_pricing_status (status),
    UNIQUE KEY uk_customer_item (customer_id, item_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- Create customer_categories table
CREATE TABLE customer_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    default_credit_limit DECIMAL(19,2),
    default_discount_percentage DECIMAL(5,2) DEFAULT 0.00,
    loyalty_tier VARCHAR(20),
    loyalty_multiplier DECIMAL(5,2) DEFAULT 1.00,
    points_per_currency DECIMAL(5,2) DEFAULT 1.00,
    grace_period_days INT DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    INDEX idx_customer_category_code (code),
    INDEX idx_customer_category_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


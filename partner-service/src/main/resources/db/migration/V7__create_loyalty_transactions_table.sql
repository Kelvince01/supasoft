-- Create loyalty_transactions table
CREATE TABLE loyalty_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    points_earned INT DEFAULT 0,
    points_redeemed INT DEFAULT 0,
    points_balance INT DEFAULT 0,
    reference_number VARCHAR(50),
    transaction_date TIMESTAMP NOT NULL,
    expiry_date DATE,
    description VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE,
    version BIGINT,
    INDEX idx_loyalty_customer (customer_id),
    INDEX idx_loyalty_date (transaction_date),
    INDEX idx_loyalty_expiry (expiry_date),
    INDEX idx_loyalty_reference (reference_number),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


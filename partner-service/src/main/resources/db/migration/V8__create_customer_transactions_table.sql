-- Create customer_transactions table
CREATE TABLE customer_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    balance_before DECIMAL(19,2) DEFAULT 0.00,
    balance_after DECIMAL(19,2) DEFAULT 0.00,
    reference_number VARCHAR(50),
    transaction_date TIMESTAMP NOT NULL,
    description VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE,
    version BIGINT,
    INDEX idx_customer_transaction_customer (customer_id),
    INDEX idx_customer_transaction_type (transaction_type),
    INDEX idx_customer_transaction_date (transaction_date),
    INDEX idx_customer_transaction_reference (reference_number),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


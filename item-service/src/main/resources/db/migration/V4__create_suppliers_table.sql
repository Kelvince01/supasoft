-- Create suppliers table for vendor management
CREATE TABLE suppliers (
    supplier_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    supplier_code VARCHAR(50) NOT NULL UNIQUE,
    supplier_name VARCHAR(200) NOT NULL,
    contact_person VARCHAR(100),
    email VARCHAR(100),
    phone_number VARCHAR(20),
    mobile_number VARCHAR(20),
    address VARCHAR(500),
    city VARCHAR(100),
    country VARCHAR(50) DEFAULT 'Kenya',
    postal_code VARCHAR(20),
    
    -- Tax Information
    tax_id VARCHAR(50),
    vat_number VARCHAR(50),
    
    -- Business Terms
    payment_terms VARCHAR(100),
    credit_limit DECIMAL(15,2) DEFAULT 0.00,
    credit_days INT DEFAULT 0,
    lead_time_days INT DEFAULT 0,
    minimum_order_value DECIMAL(12,2) DEFAULT 0.00,
    
    -- Rating & Status
    rating DECIMAL(3,2),
    is_active BOOLEAN DEFAULT TRUE,
    is_preferred BOOLEAN DEFAULT FALSE,
    
    -- Bank Details
    bank_name VARCHAR(100),
    bank_account_number VARCHAR(50),
    bank_branch VARCHAR(100),
    swift_code VARCHAR(20),
    
    notes TEXT,
    id BIGINT,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    is_deleted BOOLEAN DEFAULT FALSE,
    
    INDEX idx_supplier_code (supplier_code),
    INDEX idx_supplier_name (supplier_name),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


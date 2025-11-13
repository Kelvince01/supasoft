-- Create units of measure table
CREATE TABLE units_of_measure (
    uom_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uom_code VARCHAR(20) NOT NULL UNIQUE,
    uom_name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    uom_type VARCHAR(20),  -- WEIGHT, VOLUME, COUNT, LENGTH
    symbol VARCHAR(10),
    is_base_unit BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    id BIGINT,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    is_deleted BOOLEAN DEFAULT FALSE,
    
    INDEX idx_uom_code (uom_code),
    INDEX idx_uom_name (uom_name),
    INDEX idx_uom_type (uom_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


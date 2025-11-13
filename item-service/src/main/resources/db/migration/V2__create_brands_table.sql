-- Create brands table for product brand management
CREATE TABLE brands (
    brand_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_code VARCHAR(50) NOT NULL UNIQUE,
    brand_name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    manufacturer VARCHAR(100),
    country_of_origin VARCHAR(50),
    logo_url VARCHAR(255),
    website VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    id BIGINT,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    is_deleted BOOLEAN DEFAULT FALSE,
    
    INDEX idx_brand_code (brand_code),
    INDEX idx_brand_name (brand_name),
    INDEX idx_manufacturer (manufacturer)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


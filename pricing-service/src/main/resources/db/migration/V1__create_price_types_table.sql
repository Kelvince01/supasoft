-- Create price_types table
CREATE TABLE price_types (
    id BIGINT NOT NULL AUTO_INCREMENT,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    priority INT NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
    INDEX idx_price_type_code (code),
    INDEX idx_price_type_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert default price types
INSERT INTO price_types (code, name, description, priority, is_default) VALUES
('RETAIL', 'Retail Price', 'Standard retail customer price', 1, TRUE),
('WHOLESALE', 'Wholesale Price', 'Bulk purchase price for retailers', 2, FALSE),
('DISTRIBUTOR', 'Distributor Price', 'Special price for distributors', 3, FALSE),
('ONLINE', 'Online Price', 'E-commerce platform price', 4, FALSE);


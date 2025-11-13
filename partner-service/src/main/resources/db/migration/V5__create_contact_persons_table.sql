-- Create contact_persons table
CREATE TABLE contact_persons (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    supplier_id BIGINT,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    mobile VARCHAR(20),
    is_primary BOOLEAN DEFAULT FALSE,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE,
    version BIGINT,
    INDEX idx_contact_customer (customer_id),
    INDEX idx_contact_supplier (supplier_id),
    INDEX idx_contact_primary (is_primary),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


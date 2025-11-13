-- Create supplier_items table
CREATE TABLE supplier_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    supplier_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    supplier_item_code VARCHAR(50),
    cost_price DECIMAL(19,2),
    min_order_quantity INT DEFAULT 1,
    lead_time_days INT DEFAULT 0,
    is_preferred BOOLEAN DEFAULT FALSE,
    notes VARCHAR(500),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    INDEX idx_supplier_item_supplier (supplier_id),
    INDEX idx_supplier_item_item (item_id),
    INDEX idx_supplier_item_preferred (is_preferred),
    INDEX idx_supplier_item_status (status),
    UNIQUE KEY uk_supplier_item (supplier_id, item_id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


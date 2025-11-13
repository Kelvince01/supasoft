-- Create item_uom_conversions table for UOM conversion rules
CREATE TABLE item_uom_conversions (
    conversion_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_id BIGINT NOT NULL,
    from_uom_id BIGINT NOT NULL,
    to_uom_id BIGINT NOT NULL,
    conversion_factor DECIMAL(10,4) NOT NULL,
    barcode VARCHAR(50) UNIQUE,
    is_active BOOLEAN DEFAULT TRUE,
    id BIGINT,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    is_deleted BOOLEAN DEFAULT FALSE,
    
    CONSTRAINT fk_conversion_item FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE,
    CONSTRAINT fk_conversion_from_uom FOREIGN KEY (from_uom_id) REFERENCES units_of_measure(uom_id),
    CONSTRAINT fk_conversion_to_uom FOREIGN KEY (to_uom_id) REFERENCES units_of_measure(uom_id),
    CONSTRAINT uk_item_uom_conversion UNIQUE (item_id, from_uom_id, to_uom_id),
    
    INDEX idx_item_id (item_id),
    INDEX idx_from_uom (from_uom_id),
    INDEX idx_to_uom (to_uom_id),
    INDEX idx_barcode (barcode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


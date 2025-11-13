-- Create item_barcodes table for multiple barcodes per item support
CREATE TABLE item_barcodes (
    barcode_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_id BIGINT NOT NULL,
    barcode VARCHAR(50) NOT NULL UNIQUE,
    barcode_type VARCHAR(20),  -- EAN13, CODE128, QR, etc.
    is_primary BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    description VARCHAR(255),
    id BIGINT,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    is_deleted BOOLEAN DEFAULT FALSE,
    
    CONSTRAINT fk_barcode_item FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE,
    
    INDEX idx_barcode (barcode),
    INDEX idx_item_id (item_id),
    INDEX idx_barcode_type (barcode_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


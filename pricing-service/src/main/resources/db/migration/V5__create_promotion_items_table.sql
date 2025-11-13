-- Create promotion_items table
CREATE TABLE promotion_items (
    id BIGINT NOT NULL AUTO_INCREMENT,
    promotion_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    item_role VARCHAR(20) NOT NULL,
    required_quantity INT,
    special_price DECIMAL(19,2),
    discount_percentage DECIMAL(5,2),
    is_required BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
    INDEX idx_promotion_item_promotion (promotion_id),
    INDEX idx_promotion_item_item (item_id),
    UNIQUE KEY uk_promotion_item (promotion_id, item_id),
    FOREIGN KEY (promotion_id) REFERENCES promotions(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


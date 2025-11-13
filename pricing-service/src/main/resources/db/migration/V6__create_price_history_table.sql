-- Create price_history table
CREATE TABLE price_history (
    id BIGINT NOT NULL AUTO_INCREMENT,
    item_id BIGINT NOT NULL,
    price_type_id BIGINT NOT NULL,
    old_selling_price DECIMAL(19,2) NOT NULL,
    new_selling_price DECIMAL(19,2) NOT NULL,
    old_cost_price DECIMAL(19,2) NOT NULL,
    new_cost_price DECIMAL(19,2) NOT NULL,
    old_profit_margin DECIMAL(10,2),
    new_profit_margin DECIMAL(10,2),
    changed_at TIMESTAMP NOT NULL,
    changed_by VARCHAR(100) NOT NULL,
    change_reason VARCHAR(500),
    approved_by VARCHAR(100),
    approved_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
#     created_by VARCHAR(100),
#     updated_by VARCHAR(100),
    PRIMARY KEY (id),
    INDEX idx_price_history_item (item_id),
    INDEX idx_price_history_price_type (price_type_id),
    INDEX idx_price_history_date (changed_at),
    INDEX idx_price_history_user (changed_by),
    FOREIGN KEY (price_type_id) REFERENCES price_types(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


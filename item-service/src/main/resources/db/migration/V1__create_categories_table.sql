-- Create categories table for hierarchical product categorization
CREATE TABLE categories (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_code VARCHAR(50) NOT NULL UNIQUE,
    category_name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    parent_category_id BIGINT,
    level INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    sort_order INT DEFAULT 0,
    image_url VARCHAR(255),
    id BIGINT,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    is_deleted BOOLEAN DEFAULT FALSE,
    
    CONSTRAINT fk_category_parent FOREIGN KEY (parent_category_id) REFERENCES categories(category_id),
    INDEX idx_category_code (category_code),
    INDEX idx_category_name (category_name),
    INDEX idx_parent_category (parent_category_id),
    INDEX idx_level (level)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


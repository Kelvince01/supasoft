-- Create items table (main products table)
CREATE TABLE items (
    item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_code VARCHAR(50) NOT NULL UNIQUE,
    barcode VARCHAR(50) UNIQUE,
    item_name VARCHAR(255) NOT NULL,
    description TEXT,
    short_description VARCHAR(500),
    
    -- Relationships
    category_id BIGINT,
    brand_id BIGINT,
    base_uom_id BIGINT NOT NULL,
    supplier_id BIGINT,
    
    -- Tax Information
    vat_rate DECIMAL(5,2) DEFAULT 16.00,
    is_vat_exempt BOOLEAN DEFAULT FALSE,
    
    -- Pricing
    cost_price DECIMAL(12,2) DEFAULT 0.00,
    selling_price DECIMAL(12,2) DEFAULT 0.00,
    
    -- Inventory Tracking
    track_inventory BOOLEAN DEFAULT TRUE,
    has_expiry BOOLEAN DEFAULT FALSE,
    shelf_life_days INT,
    reorder_level DECIMAL(10,2) DEFAULT 0.00,
    minimum_stock_level DECIMAL(10,2) DEFAULT 0.00,
    maximum_stock_level DECIMAL(10,2) DEFAULT 0.00,
    
    -- Physical Attributes
    weight DECIMAL(10,3),
    weight_unit VARCHAR(10),
    length DECIMAL(10,2),
    width DECIMAL(10,2),
    height DECIMAL(10,2),
    dimension_unit VARCHAR(10),
    
    -- Status & Flags
    is_active BOOLEAN DEFAULT TRUE,
    is_for_sale BOOLEAN DEFAULT TRUE,
    is_for_purchase BOOLEAN DEFAULT TRUE,
    is_serialized BOOLEAN DEFAULT FALSE,
    is_batch_tracked BOOLEAN DEFAULT FALSE,
    
    -- Additional Information
    sku VARCHAR(100) UNIQUE,
    manufacturer_part_number VARCHAR(100),
    hsn_code VARCHAR(20),
    image_url VARCHAR(500),
    tags VARCHAR(500),
    notes TEXT,
    launch_date DATE,
    discontinue_date DATE,
    
    id BIGINT,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    is_deleted BOOLEAN DEFAULT FALSE,
    
    CONSTRAINT fk_item_category FOREIGN KEY (category_id) REFERENCES categories(category_id),
    CONSTRAINT fk_item_brand FOREIGN KEY (brand_id) REFERENCES brands(brand_id),
    CONSTRAINT fk_item_base_uom FOREIGN KEY (base_uom_id) REFERENCES units_of_measure(uom_id),
    CONSTRAINT fk_item_supplier FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
    
    INDEX idx_item_code (item_code),
    INDEX idx_barcode (barcode),
    INDEX idx_item_name (item_name),
    INDEX idx_sku (sku),
    INDEX idx_category (category_id),
    INDEX idx_brand (brand_id),
    INDEX idx_supplier (supplier_id),
    INDEX idx_is_active (is_active),
    INDEX idx_is_for_sale (is_for_sale)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


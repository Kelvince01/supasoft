-- Insert default Units of Measure
INSERT INTO units_of_measure (uom_code, uom_name, description, uom_type, symbol, is_base_unit, is_active, created_at, updated_at) VALUES
('PCS', 'Pieces', 'Individual pieces or units', 'COUNT', 'pcs', TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('KG', 'Kilogram', 'Weight in kilograms', 'WEIGHT', 'kg', TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('G', 'Gram', 'Weight in grams', 'WEIGHT', 'g', FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('L', 'Liter', 'Volume in liters', 'VOLUME', 'L', TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ML', 'Milliliter', 'Volume in milliliters', 'VOLUME', 'ml', FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('M', 'Meter', 'Length in meters', 'LENGTH', 'm', TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('CM', 'Centimeter', 'Length in centimeters', 'LENGTH', 'cm', FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('DOZ', 'Dozen', 'Twelve pieces', 'COUNT', 'doz', FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BOX', 'Box', 'Box packaging', 'COUNT', 'box', FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('CTN', 'Carton', 'Carton packaging', 'COUNT', 'ctn', FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('PKT', 'Packet', 'Packet packaging', 'COUNT', 'pkt', FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BTL', 'Bottle', 'Bottle packaging', 'COUNT', 'btl', FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('SET', 'Set', 'Set of items', 'COUNT', 'set', FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample root categories
INSERT INTO categories (category_code, category_name, description, parent_category_id, level, is_active, sort_order, created_at, updated_at) VALUES
('FOOD', 'Food & Beverages', 'Food and beverage products', NULL, 0, TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ELECTRONICS', 'Electronics', 'Electronic devices and accessories', NULL, 0, TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('HOUSEHOLD', 'Household', 'Household items and supplies', NULL, 0, TRUE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('PERSONAL', 'Personal Care', 'Personal care and hygiene products', NULL, 0, TRUE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('STATIONERY', 'Stationery', 'Office and school supplies', NULL, 0, TRUE, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample brands
INSERT INTO brands (brand_code, brand_name, description, manufacturer, country_of_origin, is_active, created_at, updated_at) VALUES
('GENERIC', 'Generic', 'Generic unbranded products', 'Various', 'Kenya', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('SAMSUNG', 'Samsung', 'Samsung Electronics', 'Samsung Electronics', 'South Korea', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('NIKE', 'Nike', 'Nike Sports Brand', 'Nike Inc.', 'USA', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('COCACOLA', 'Coca-Cola', 'Coca-Cola Company', 'The Coca-Cola Company', 'USA', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample supplier
INSERT INTO suppliers (supplier_code, supplier_name, contact_person, email, phone_number, country, is_active, created_at, updated_at) VALUES
('SUP001', 'Default Supplier', 'John Doe', 'supplier@example.com', '+254700000000', 'Kenya', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


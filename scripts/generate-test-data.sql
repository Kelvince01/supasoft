-- ============================================================================
-- Supasoft Test Data Generation Script
-- ============================================================================
-- Description: Generates realistic test data for development and testing
-- Usage: mysql -u supasoft_app -p < generate-test-data.sql
-- Warning: This script will DELETE existing data. Use only in development!
-- ============================================================================

-- Set SQL mode and disable foreign key checks
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET FOREIGN_KEY_CHECKS = 0;
SET time_zone = "+03:00"; -- East Africa Time (Kenya)

-- ============================================================================
-- AUTH SERVICE - Users, Roles, Permissions
-- ============================================================================

USE supasoft_auth;

-- Clear existing data
TRUNCATE TABLE user_roles;
TRUNCATE TABLE users;
TRUNCATE TABLE roles;
TRUNCATE TABLE permissions;

-- Insert Permissions
INSERT INTO permissions (permission_id, permission_name, description, created_at) VALUES
(1, 'VIEW_ITEMS', 'View items and inventory', NOW()),
(2, 'CREATE_ITEMS', 'Create new items', NOW()),
(3, 'UPDATE_ITEMS', 'Update existing items', NOW()),
(4, 'DELETE_ITEMS', 'Delete items', NOW()),
(5, 'VIEW_SALES', 'View sales data', NOW()),
(6, 'CREATE_SALES', 'Create sales invoices', NOW()),
(7, 'REFUND_SALES', 'Process refunds', NOW()),
(8, 'VIEW_REPORTS', 'View reports', NOW()),
(9, 'MANAGE_USERS', 'Manage user accounts', NOW()),
(10, 'APPROVE_PURCHASES', 'Approve purchase orders', NOW());

-- Insert Roles
INSERT INTO roles (role_id, role_name, description, created_at) VALUES
(1, 'ADMIN', 'System Administrator - Full access', NOW()),
(2, 'MANAGER', 'Store Manager - Management functions', NOW()),
(3, 'CASHIER', 'Cashier - POS operations', NOW()),
(4, 'STOCK_CLERK', 'Stock Clerk - Inventory management', NOW()),
(5, 'ACCOUNTANT', 'Accountant - Financial reports', NOW());

-- Insert Users (password: Test@123 - BCrypt hashed)
INSERT INTO users (user_id, username, email, password_hash, first_name, last_name, is_active, created_at) VALUES
(1, 'admin', 'admin@supasoft.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'System', 'Administrator', true, NOW()),
(2, 'manager1', 'manager1@supasoft.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'John', 'Mwangi', true, NOW()),
(3, 'cashier1', 'cashier1@supasoft.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Mary', 'Wanjiru', true, NOW()),
(4, 'cashier2', 'cashier2@supasoft.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Peter', 'Omondi', true, NOW()),
(5, 'stock1', 'stock1@supasoft.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Grace', 'Akinyi', true, NOW()),
(6, 'accountant1', 'accountant1@supasoft.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'David', 'Kimani', true, NOW());

-- Assign Roles to Users
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1), -- admin
(2, 2), -- manager1
(3, 3), -- cashier1
(4, 3), -- cashier2
(5, 4), -- stock1
(6, 5); -- accountant1

-- ============================================================================
-- ITEM SERVICE - Categories, Brands, Items
-- ============================================================================

USE supasoft_items;

-- Clear existing data
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE item_barcodes;
TRUNCATE TABLE item_uom_conversions;
TRUNCATE TABLE items;
TRUNCATE TABLE categories;
TRUNCATE TABLE brands;
TRUNCATE TABLE unit_of_measures;
TRUNCATE TABLE suppliers;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert Unit of Measures
INSERT INTO unit_of_measures (uom_id, uom_name, uom_code, is_base_unit, created_at) VALUES
(1, 'Piece', 'PCS', true, NOW()),
(2, 'Kilogram', 'KG', true, NOW()),
(3, 'Liter', 'LTR', true, NOW()),
(4, 'Dozen', 'DZN', false, NOW()),
(5, 'Carton', 'CTN', false, NOW()),
(6, 'Pack', 'PACK', false, NOW()),
(7, 'Bag', 'BAG', false, NOW()),
(8, 'Bottle', 'BTL', false, NOW());

-- Insert Categories
INSERT INTO categories (category_id, category_name, parent_id, description, created_at) VALUES
(1, 'Beverages', NULL, 'All beverages', NOW()),
(2, 'Soft Drinks', 1, 'Carbonated and non-carbonated drinks', NOW()),
(3, 'Water', 1, 'Bottled water', NOW()),
(4, 'Juices', 1, 'Fruit juices', NOW()),
(5, 'Dairy Products', NULL, 'Milk and dairy', NOW()),
(6, 'Milk', 5, 'Fresh and long-life milk', NOW()),
(7, 'Yogurt', 5, 'Yogurt products', NOW()),
(8, 'Grocery', NULL, 'Dry goods and groceries', NOW()),
(9, 'Rice & Cereals', 8, 'Rice, wheat, cereals', NOW()),
(10, 'Cooking Oil', 8, 'Cooking oils and fats', NOW()),
(11, 'Personal Care', NULL, 'Personal hygiene products', NOW()),
(12, 'Soap', 11, 'Bath and laundry soap', NOW()),
(13, 'Toiletries', 11, 'Toothpaste, shampoo, etc', NOW());

-- Insert Brands
INSERT INTO brands (brand_id, brand_name, description, created_at) VALUES
(1, 'Coca-Cola', 'Coca-Cola Company products', NOW()),
(2, 'Pepsi', 'PepsiCo products', NOW()),
(3, 'Kenya Creameries', 'KCC - Dairy products', NOW()),
(4, 'Brookside', 'Brookside Dairy', NOW()),
(5, 'Keringet', 'Keringet Water', NOW()),
(6, 'Dasani', 'Dasani Water', NOW()),
(7, 'Del Monte', 'Del Monte Foods', NOW()),
(8, 'Tropical Heat', 'Tropical Heat Cooking Oil', NOW()),
(9, 'Elianto', 'Elianto Cooking Oil', NOW()),
(10, 'Colgate', 'Colgate-Palmolive', NOW()),
(11, 'Dettol', 'Dettol Products', NOW()),
(12, 'Omo', 'Unilever - Omo', NOW());

-- Insert Suppliers
INSERT INTO suppliers (supplier_id, supplier_name, supplier_code, contact_person, phone, email, address, created_at) VALUES
(1, 'Nairobi Beverages Ltd', 'SUP001', 'James Muturi', '+254712345678', 'james@nairobibev.co.ke', 'Industrial Area, Nairobi', NOW()),
(2, 'East Africa Dairy Co', 'SUP002', 'Susan Wanjiku', '+254723456789', 'susan@eadairy.co.ke', 'Ruiru, Kenya', NOW()),
(3, 'Global Grocers Wholesale', 'SUP003', 'Ahmed Hassan', '+254734567890', 'ahmed@globalgrocers.co.ke', 'Mombasa Road, Nairobi', NOW()),
(4, 'Kenya Personal Care Supplies', 'SUP004', 'Lucy Auma', '+254745678901', 'lucy@kpcsupplies.co.ke', 'Westlands, Nairobi', NOW());

-- Insert Items
INSERT INTO items (item_id, item_code, barcode, item_name, category_id, brand_id, base_uom_id, vat_rate, is_active, reorder_level, max_stock_level, shelf_life_days, created_at) VALUES
-- Beverages
(1, 'ITM0001', '6001234567890', 'Coca-Cola 300ml', 2, 1, 1, 16.00, true, 100, 500, 365, NOW()),
(2, 'ITM0002', '6001234567891', 'Coca-Cola 500ml', 2, 1, 1, 16.00, true, 80, 400, 365, NOW()),
(3, 'ITM0003', '6001234567892', 'Pepsi 300ml', 2, 2, 1, 16.00, true, 100, 500, 365, NOW()),
(4, 'ITM0004', '6001234567893', 'Fanta Orange 300ml', 2, 1, 1, 16.00, true, 80, 400, 365, NOW()),
(5, 'ITM0005', '6001234567894', 'Sprite 300ml', 2, 1, 1, 16.00, true, 80, 400, 365, NOW()),
(6, 'ITM0006', '6001234567895', 'Keringet Water 500ml', 3, 5, 1, 16.00, true, 200, 1000, 730, NOW()),
(7, 'ITM0007', '6001234567896', 'Dasani Water 500ml', 3, 6, 1, 16.00, true, 200, 1000, 730, NOW()),
(8, 'ITM0008', '6001234567897', 'Del Monte Orange Juice 1L', 4, 7, 1, 16.00, true, 50, 200, 180, NOW()),
(9, 'ITM0009', '6001234567898', 'Del Monte Mango Juice 1L', 4, 7, 1, 16.00, true, 50, 200, 180, NOW()),

-- Dairy
(10, 'ITM0010', '6001234567899', 'KCC Fresh Milk 500ml', 6, 3, 1, 0.00, true, 100, 300, 7, NOW()),
(11, 'ITM0011', '6001234567900', 'KCC Fresh Milk 1L', 6, 3, 1, 0.00, true, 150, 400, 7, NOW()),
(12, 'ITM0012', '6001234567901', 'Brookside Milk 500ml', 6, 4, 1, 0.00, true, 100, 300, 7, NOW()),
(13, 'ITM0013', '6001234567902', 'Brookside UHT Milk 1L', 6, 4, 1, 0.00, true, 80, 300, 180, NOW()),
(14, 'ITM0014', '6001234567903', 'Brookside Yogurt 500g', 7, 4, 1, 0.00, true, 60, 200, 14, NOW()),

-- Grocery
(15, 'ITM0015', '6001234567904', 'Pishori Rice 2kg', 9, NULL, 6, 16.00, true, 50, 200, 365, NOW()),
(16, 'ITM0016', '6001234567905', 'Basmati Rice 5kg', 9, NULL, 7, 16.00, true, 30, 150, 365, NOW()),
(17, 'ITM0017', '6001234567906', 'Tropical Heat Oil 2L', 10, 8, 8, 16.00, true, 40, 200, 365, NOW()),
(18, 'ITM0018', '6001234567907', 'Elianto Oil 3L', 10, 9, 8, 16.00, true, 30, 150, 365, NOW()),

-- Personal Care
(19, 'ITM0019', '6001234567908', 'Colgate Toothpaste 75ml', 13, 10, 1, 16.00, true, 80, 300, 730, NOW()),
(20, 'ITM0020', '6001234567909', 'Dettol Soap 75g', 12, 11, 1, 16.00, true, 100, 400, 1095, NOW()),
(21, 'ITM0021', '6001234567910', 'Omo Washing Powder 1kg', 12, 12, 6, 16.00, true, 50, 200, 1095, NOW());

-- UOM Conversions
INSERT INTO item_uom_conversions (item_id, from_uom_id, to_uom_id, conversion_factor, created_at) VALUES
-- Soft drinks typically sold in crates (24 bottles)
(1, 5, 1, 24, NOW()), -- 1 Carton = 24 Pieces (Coca-Cola 300ml)
(2, 5, 1, 24, NOW()), -- 1 Carton = 24 Pieces (Coca-Cola 500ml)
(3, 5, 1, 24, NOW()), -- 1 Carton = 24 Pieces (Pepsi)
(4, 5, 1, 24, NOW()), -- 1 Carton = 24 Pieces (Fanta)
(5, 5, 1, 24, NOW()), -- 1 Carton = 24 Pieces (Sprite)
-- Water
(6, 5, 1, 24, NOW()), -- 1 Carton = 24 Pieces (Keringet)
(7, 5, 1, 24, NOW()), -- 1 Carton = 24 Pieces (Dasani)
-- Milk
(10, 5, 1, 20, NOW()), -- 1 Carton = 20 Pieces (KCC 500ml)
(11, 5, 1, 12, NOW()), -- 1 Carton = 12 Pieces (KCC 1L)
(12, 5, 1, 20, NOW()), -- 1 Carton = 20 Pieces (Brookside 500ml)
(13, 5, 1, 12, NOW()); -- 1 Carton = 12 Pieces (Brookside 1L)

-- ============================================================================
-- PRICING SERVICE - Price Types, Item Prices
-- ============================================================================

USE supasoft_pricing;

-- Clear existing data
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE item_prices;
TRUNCATE TABLE price_types;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert Price Types
INSERT INTO price_types (price_type_id, price_type_name, description, is_default, created_at) VALUES
(1, 'Retail', 'Standard retail price', true, NOW()),
(2, 'Wholesale', 'Wholesale price (min 12 pieces)', false, NOW()),
(3, 'Bulk', 'Bulk price (min 1 carton)', false, NOW());

-- Insert Item Prices (in KES - Kenya Shillings)
INSERT INTO item_prices (item_id, price_type_id, uom_id, cost_price, selling_price, profit_margin, created_at) VALUES
-- Coca-Cola 300ml
(1, 1, 1, 35.00, 50.00, 42.86, NOW()),  -- Retail per piece
(1, 2, 1, 35.00, 45.00, 28.57, NOW()),  -- Wholesale per piece
(1, 3, 5, 840.00, 1000.00, 19.05, NOW()), -- Bulk per carton (24 pcs)

-- Coca-Cola 500ml
(2, 1, 1, 50.00, 70.00, 40.00, NOW()),  -- Retail
(2, 2, 1, 50.00, 65.00, 30.00, NOW()),  -- Wholesale
(2, 3, 5, 1200.00, 1440.00, 20.00, NOW()), -- Bulk per carton

-- Pepsi 300ml
(3, 1, 1, 32.00, 45.00, 40.63, NOW()),
(3, 2, 1, 32.00, 42.00, 31.25, NOW()),

-- Water
(6, 1, 1, 20.00, 30.00, 50.00, NOW()),  -- Keringet retail
(6, 3, 5, 480.00, 600.00, 25.00, NOW()), -- Keringet carton

(7, 1, 1, 22.00, 35.00, 59.09, NOW()),  -- Dasani retail

-- Milk
(10, 1, 1, 55.00, 70.00, 27.27, NOW()),  -- KCC 500ml
(11, 1, 1, 105.00, 130.00, 23.81, NOW()), -- KCC 1L
(12, 1, 1, 52.00, 68.00, 30.77, NOW()),  -- Brookside 500ml
(13, 1, 1, 100.00, 125.00, 25.00, NOW()), -- Brookside UHT 1L
(14, 1, 1, 80.00, 100.00, 25.00, NOW()),  -- Yogurt

-- Rice
(15, 1, 6, 220.00, 280.00, 27.27, NOW()), -- Pishori 2kg
(16, 1, 7, 550.00, 700.00, 27.27, NOW()), -- Basmati 5kg

-- Cooking Oil
(17, 1, 8, 450.00, 550.00, 22.22, NOW()), -- Tropical Heat 2L
(18, 1, 8, 650.00, 800.00, 23.08, NOW()), -- Elianto 3L

-- Personal Care
(19, 1, 1, 120.00, 150.00, 25.00, NOW()), -- Colgate
(20, 1, 1, 35.00, 50.00, 42.86, NOW()),   -- Dettol Soap
(21, 1, 6, 280.00, 350.00, 25.00, NOW());  -- Omo 1kg

-- ============================================================================
-- TRANSFER SERVICE - Branches
-- ============================================================================

USE supasoft_transfer;

-- Clear existing data
TRUNCATE TABLE branches;

-- Insert Branches
INSERT INTO branches (branch_id, branch_code, branch_name, location, phone, email, is_active, created_at) VALUES
(1, 'BR001', 'Main Branch - Nairobi CBD', 'Tom Mboya Street, Nairobi', '+254712000001', 'main@supasoft.com', true, NOW()),
(2, 'BR002', 'Westlands Branch', 'Westlands, Nairobi', '+254712000002', 'westlands@supasoft.com', true, NOW()),
(3, 'BR003', 'Eastleigh Branch', 'Eastleigh, Nairobi', '+254712000003', 'eastleigh@supasoft.com', true, NOW()),
(4, 'BR004', 'Kisumu Branch', 'Kisumu City', '+254712000004', 'kisumu@supasoft.com', true, NOW());

-- ============================================================================
-- STOCK SERVICE - Initial Stock
-- ============================================================================

USE supasoft_stock;

-- Clear existing data
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE stock_balance;
TRUNCATE TABLE stock_movements;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert Initial Stock for Main Branch
INSERT INTO stock_balance (item_id, branch_id, quantity, average_cost, last_cost, created_at, updated_at) VALUES
-- Beverages
(1, 1, 200, 35.00, 35.00, NOW(), NOW()),  -- Coca-Cola 300ml
(2, 1, 150, 50.00, 50.00, NOW(), NOW()),  -- Coca-Cola 500ml
(3, 1, 180, 32.00, 32.00, NOW(), NOW()),  -- Pepsi 300ml
(4, 1, 160, 33.00, 33.00, NOW(), NOW()),  -- Fanta
(5, 1, 160, 33.00, 33.00, NOW(), NOW()),  -- Sprite
(6, 1, 300, 20.00, 20.00, NOW(), NOW()),  -- Keringet Water
(7, 1, 250, 22.00, 22.00, NOW(), NOW()),  -- Dasani Water
(8, 1, 80, 120.00, 120.00, NOW(), NOW()), -- Del Monte Orange
(9, 1, 80, 120.00, 120.00, NOW(), NOW()), -- Del Monte Mango

-- Dairy
(10, 1, 150, 55.00, 55.00, NOW(), NOW()), -- KCC 500ml
(11, 1, 200, 105.00, 105.00, NOW(), NOW()), -- KCC 1L
(12, 1, 140, 52.00, 52.00, NOW(), NOW()), -- Brookside 500ml
(13, 1, 120, 100.00, 100.00, NOW(), NOW()), -- Brookside UHT
(14, 1, 80, 80.00, 80.00, NOW(), NOW()),  -- Yogurt

-- Grocery
(15, 1, 100, 220.00, 220.00, NOW(), NOW()), -- Pishori Rice
(16, 1, 60, 550.00, 550.00, NOW(), NOW()),  -- Basmati Rice
(17, 1, 80, 450.00, 450.00, NOW(), NOW()),  -- Tropical Heat Oil
(18, 1, 60, 650.00, 650.00, NOW(), NOW()),  -- Elianto Oil

-- Personal Care
(19, 1, 150, 120.00, 120.00, NOW(), NOW()), -- Colgate
(20, 1, 200, 35.00, 35.00, NOW(), NOW()),   -- Dettol Soap
(21, 1, 100, 280.00, 280.00, NOW(), NOW()); -- Omo

-- Record opening stock movements
INSERT INTO stock_movements (item_id, branch_id, movement_type, quantity, cost_price, reference_number, notes, created_by, created_at) VALUES
(1, 1, 'OPENING_STOCK', 200, 35.00, 'OPEN-001', 'Opening stock', 1, NOW()),
(2, 1, 'OPENING_STOCK', 150, 50.00, 'OPEN-001', 'Opening stock', 1, NOW()),
(3, 1, 'OPENING_STOCK', 180, 32.00, 'OPEN-001', 'Opening stock', 1, NOW()),
(6, 1, 'OPENING_STOCK', 300, 20.00, 'OPEN-001', 'Opening stock', 1, NOW()),
(10, 1, 'OPENING_STOCK', 150, 55.00, 'OPEN-001', 'Opening stock', 1, NOW()),
(11, 1, 'OPENING_STOCK', 200, 105.00, 'OPEN-001', 'Opening stock', 1, NOW()),
(15, 1, 'OPENING_STOCK', 100, 220.00, 'OPEN-001', 'Opening stock', 1, NOW()),
(19, 1, 'OPENING_STOCK', 150, 120.00, 'OPEN-001', 'Opening stock', 1, NOW());

-- ============================================================================
-- Summary
-- ============================================================================

SELECT 'Test data generated successfully!' AS Status;

SELECT 
    'Auth Service' AS Service,
    (SELECT COUNT(*) FROM supasoft_auth.users) AS Users,
    (SELECT COUNT(*) FROM supasoft_auth.roles) AS Roles;

SELECT 
    'Item Service' AS Service,
    (SELECT COUNT(*) FROM supasoft_items.items) AS Items,
    (SELECT COUNT(*) FROM supasoft_items.categories) AS Categories,
    (SELECT COUNT(*) FROM supasoft_items.brands) AS Brands;

SELECT 
    'Pricing Service' AS Service,
    (SELECT COUNT(*) FROM supasoft_pricing.item_prices) AS Prices,
    (SELECT COUNT(*) FROM supasoft_pricing.price_types) AS PriceTypes;

SELECT 
    'Stock Service' AS Service,
    (SELECT COUNT(*) FROM supasoft_stock.stock_balance) AS StockRecords,
    (SELECT SUM(quantity) FROM supasoft_stock.stock_balance) AS TotalQuantity;

SELECT 
    'Transfer Service' AS Service,
    (SELECT COUNT(*) FROM supasoft_transfer.branches) AS Branches;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;


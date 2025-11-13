# Supasoft - Database Schema Documentation

## 1. DATABASE OVERVIEW

### 1.1 Database Architecture
- **DBMS**: MySQL 8.0+
- **Pattern**: Database-per-Service
- **Charset**: utf8mb4
- **Collation**: utf8mb4_unicode_ci
- **Timezone**: Africa/Nairobi (EAT)
- **Migration Tool**: Flyway

### 1.2 Database List

| Database Name | Service | Purpose |
|--------------|---------|---------|
| `supasoft_auth` | Auth Service | User authentication and authorization |
| `supasoft_items` | Item Service | Item master data |
| `supasoft_pricing` | Pricing Service | Pricing and promotions |
| `supasoft_stock` | Stock Service | Inventory management |
| `supasoft_sales` | Sales Service | POS and sales transactions |
| `supasoft_purchase` | Purchase Service | Procurement |
| `supasoft_transfer` | Transfer Service | Inter-branch transfers |
| `supasoft_reports` | Reports Service | Analytics and reporting |
| `supasoft_notifications` | Notification Service | Notifications |
| `supasoft_integrations` | Integration Service | External integrations |
| `supasoft_audit` | Audit Service | Audit trails |

---

## 2. AUTH SERVICE DATABASE (`supasoft_auth`)

### 2.1 users
```sql
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20),
    branch_id BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    is_locked BOOLEAN DEFAULT FALSE,
    failed_login_attempts INT DEFAULT 0,
    last_login_at TIMESTAMP NULL,
    password_changed_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_branch_id (branch_id),
    INDEX idx_is_active (is_active)
);
```

### 2.2 roles
```sql
CREATE TABLE roles (
    role_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) UNIQUE NOT NULL,
    role_code VARCHAR(20) UNIQUE NOT NULL,
    description VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_role_code (role_code)
);

-- Default Roles
INSERT INTO roles (role_name, role_code, description) VALUES
('Super Administrator', 'SUPER_ADMIN', 'Full system access'),
('Administrator', 'ADMIN', 'Branch administrator'),
('Manager', 'MANAGER', 'Branch manager'),
('Cashier', 'CASHIER', 'Point of Sale operator'),
('Stock Keeper', 'STOCK_KEEPER', 'Inventory management');
```

### 2.3 permissions
```sql
CREATE TABLE permissions (
    permission_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    permission_name VARCHAR(100) NOT NULL,
    permission_code VARCHAR(50) UNIQUE NOT NULL,
    module VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_module (module),
    INDEX idx_permission_code (permission_code)
);

-- Example Permissions
INSERT INTO permissions (permission_name, permission_code, module) VALUES
('Create Item', 'ITEM_CREATE', 'ITEMS'),
('View Item', 'ITEM_VIEW', 'ITEMS'),
('Update Item', 'ITEM_UPDATE', 'ITEMS'),
('Delete Item', 'ITEM_DELETE', 'ITEMS'),
('Process Sale', 'SALE_CREATE', 'SALES'),
('View Reports', 'REPORT_VIEW', 'REPORTS');
```

### 2.4 user_roles
```sql
CREATE TABLE user_roles (
    user_role_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    assigned_by BIGINT,
    
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_role (user_id, role_id),
    INDEX idx_user_id (user_id),
    INDEX idx_role_id (role_id)
);
```

### 2.5 role_permissions
```sql
CREATE TABLE role_permissions (
    role_permission_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(permission_id) ON DELETE CASCADE,
    UNIQUE KEY uk_role_permission (role_id, permission_id),
    INDEX idx_role_id (role_id)
);
```

### 2.6 refresh_tokens
```sql
CREATE TABLE refresh_tokens (
    token_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    token VARCHAR(500) UNIQUE NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    revoked BOOLEAN DEFAULT FALSE,
    
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_token (token),
    INDEX idx_user_id (user_id),
    INDEX idx_expires_at (expires_at)
);
```

---

## 3. ITEM SERVICE DATABASE (`supasoft_items`)

### 3.1 categories
```sql
CREATE TABLE categories (
    category_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(100) NOT NULL,
    category_code VARCHAR(20) UNIQUE NOT NULL,
    parent_category_id BIGINT NULL,
    description TEXT,
    display_order INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    
    FOREIGN KEY (parent_category_id) REFERENCES categories(category_id),
    INDEX idx_parent_category (parent_category_id),
    INDEX idx_category_code (category_code),
    INDEX idx_is_active (is_active)
);
```

### 3.2 brands
```sql
CREATE TABLE brands (
    brand_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    brand_name VARCHAR(100) UNIQUE NOT NULL,
    brand_code VARCHAR(20) UNIQUE NOT NULL,
    description TEXT,
    logo_url VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_brand_code (brand_code)
);
```

### 3.3 unit_of_measures (UOM)
```sql
CREATE TABLE unit_of_measures (
    uom_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    uom_name VARCHAR(50) NOT NULL,
    uom_code VARCHAR(10) UNIQUE NOT NULL,
    uom_type ENUM('BASE', 'DERIVED') DEFAULT 'BASE',
    base_uom_id BIGINT NULL,
    conversion_factor DECIMAL(10,4) DEFAULT 1.0000,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (base_uom_id) REFERENCES unit_of_measures(uom_id),
    INDEX idx_uom_code (uom_code)
);

-- Default UOMs
INSERT INTO unit_of_measures (uom_name, uom_code, uom_type) VALUES
('Piece', 'PCS', 'BASE'),
('Kilogram', 'KG', 'BASE'),
('Liter', 'L', 'BASE'),
('Dozen', 'DOZ', 'DERIVED'),
('Carton', 'CTN', 'DERIVED');
```

### 3.4 items
```sql
CREATE TABLE items (
    item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_code VARCHAR(50) UNIQUE NOT NULL,
    item_name VARCHAR(200) NOT NULL,
    description TEXT,
    category_id BIGINT NOT NULL,
    brand_id BIGINT,
    base_uom_id BIGINT NOT NULL,
    barcode VARCHAR(50) UNIQUE,
    sku VARCHAR(50),
    
    -- Tax Information
    is_taxable BOOLEAN DEFAULT TRUE,
    vat_rate DECIMAL(5,2) DEFAULT 16.00,
    tax_category VARCHAR(50),
    
    -- Item Attributes
    is_serialized BOOLEAN DEFAULT FALSE,
    is_batch_tracked BOOLEAN DEFAULT FALSE,
    is_expiry_tracked BOOLEAN DEFAULT FALSE,
    shelf_life_days INT,
    reorder_level DECIMAL(10,2),
    reorder_quantity DECIMAL(10,2),
    
    -- Dimensions
    weight DECIMAL(10,3),
    volume DECIMAL(10,3),
    length DECIMAL(10,2),
    width DECIMAL(10,2),
    height DECIMAL(10,2),
    
    -- Status
    is_active BOOLEAN DEFAULT TRUE,
    is_discontinued BOOLEAN DEFAULT FALSE,
    
    -- Audit
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    
    FOREIGN KEY (category_id) REFERENCES categories(category_id),
    FOREIGN KEY (brand_id) REFERENCES brands(brand_id),
    FOREIGN KEY (base_uom_id) REFERENCES unit_of_measures(uom_id),
    
    INDEX idx_item_code (item_code),
    INDEX idx_barcode (barcode),
    INDEX idx_item_name (item_name),
    INDEX idx_category_id (category_id),
    INDEX idx_brand_id (brand_id),
    INDEX idx_is_active (is_active),
    FULLTEXT idx_search (item_name, description)
);
```

### 3.5 item_uoms
```sql
CREATE TABLE item_uoms (
    item_uom_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_id BIGINT NOT NULL,
    uom_id BIGINT NOT NULL,
    conversion_factor DECIMAL(10,4) NOT NULL,
    barcode VARCHAR(50),
    is_default BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE,
    FOREIGN KEY (uom_id) REFERENCES unit_of_measures(uom_id),
    UNIQUE KEY uk_item_uom (item_id, uom_id),
    INDEX idx_item_id (item_id),
    INDEX idx_barcode (barcode)
);
```

### 3.6 item_barcodes
```sql
CREATE TABLE item_barcodes (
    barcode_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_id BIGINT NOT NULL,
    uom_id BIGINT,
    barcode VARCHAR(50) UNIQUE NOT NULL,
    barcode_type ENUM('EAN13', 'EAN8', 'CODE128', 'CODE39', 'QR') DEFAULT 'EAN13',
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE,
    FOREIGN KEY (uom_id) REFERENCES unit_of_measures(uom_id),
    INDEX idx_item_id (item_id),
    UNIQUE KEY uk_barcode (barcode)
);
```

---

## 4. PRICING SERVICE DATABASE (`supasoft_pricing`)

### 4.1 price_types
```sql
CREATE TABLE price_types (
    price_type_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    price_type_name VARCHAR(50) NOT NULL,
    price_type_code VARCHAR(20) UNIQUE NOT NULL,
    description VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_price_type_code (price_type_code)
);

-- Default Price Types
INSERT INTO price_types (price_type_name, price_type_code) VALUES
('Retail Price', 'RETAIL'),
('Wholesale Price', 'WHOLESALE'),
('Special Price', 'SPECIAL'),
('Member Price', 'MEMBER');
```

### 4.2 item_prices
```sql
CREATE TABLE item_prices (
    price_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_id BIGINT NOT NULL,
    uom_id BIGINT NOT NULL,
    branch_id BIGINT,
    price_type_id BIGINT NOT NULL,
    
    -- Pricing
    cost_price DECIMAL(15,2) NOT NULL,
    selling_price DECIMAL(15,2) NOT NULL,
    markup_percentage DECIMAL(5,2),
    profit_margin DECIMAL(15,2),
    
    -- Validity
    effective_from DATE NOT NULL,
    effective_to DATE,
    
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    
    INDEX idx_item_id (item_id),
    INDEX idx_branch_id (branch_id),
    INDEX idx_price_type_id (price_type_id),
    INDEX idx_effective_dates (effective_from, effective_to),
    UNIQUE KEY uk_item_price (item_id, uom_id, branch_id, price_type_id, effective_from)
);
```

### 4.3 promotions
```sql
CREATE TABLE promotions (
    promotion_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    promotion_code VARCHAR(50) UNIQUE NOT NULL,
    promotion_name VARCHAR(200) NOT NULL,
    description TEXT,
    promotion_type ENUM('DISCOUNT_PERCENTAGE', 'DISCOUNT_AMOUNT', 'BUY_X_GET_Y', 'BUNDLE') NOT NULL,
    
    -- Discount Details
    discount_percentage DECIMAL(5,2),
    discount_amount DECIMAL(15,2),
    
    -- Buy X Get Y
    buy_quantity INT,
    get_quantity INT,
    
    -- Validity
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    
    -- Restrictions
    min_purchase_amount DECIMAL(15,2),
    max_discount_amount DECIMAL(15,2),
    usage_limit INT,
    usage_count INT DEFAULT 0,
    
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    
    INDEX idx_promotion_code (promotion_code),
    INDEX idx_dates (start_date, end_date)
);
```

### 4.4 promotion_items
```sql
CREATE TABLE promotion_items (
    promotion_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    promotion_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    category_id BIGINT,
    
    FOREIGN KEY (promotion_id) REFERENCES promotions(promotion_id) ON DELETE CASCADE,
    INDEX idx_promotion_id (promotion_id),
    INDEX idx_item_id (item_id)
);
```

### 4.5 price_history
```sql
CREATE TABLE price_history (
    history_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_id BIGINT NOT NULL,
    uom_id BIGINT NOT NULL,
    branch_id BIGINT,
    price_type_id BIGINT NOT NULL,
    old_price DECIMAL(15,2),
    new_price DECIMAL(15,2) NOT NULL,
    change_reason VARCHAR(255),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    changed_by BIGINT,
    
    INDEX idx_item_id (item_id),
    INDEX idx_changed_at (changed_at)
);
```

---

## 5. STOCK SERVICE DATABASE (`supasoft_stock`)

### 5.1 branches
```sql
CREATE TABLE branches (
    branch_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    branch_code VARCHAR(20) UNIQUE NOT NULL,
    branch_name VARCHAR(100) NOT NULL,
    branch_type ENUM('MAIN', 'BRANCH', 'WAREHOUSE') DEFAULT 'BRANCH',
    
    -- Contact
    phone_number VARCHAR(20),
    email VARCHAR(100),
    
    -- Address
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    county VARCHAR(100),
    country VARCHAR(50) DEFAULT 'Kenya',
    postal_code VARCHAR(20),
    
    -- Location
    latitude DECIMAL(10,8),
    longitude DECIMAL(11,8),
    
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_branch_code (branch_code)
);
```

### 5.2 stock_balance
```sql
CREATE TABLE stock_balance (
    balance_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    batch_number VARCHAR(50),
    serial_number VARCHAR(50),
    
    -- Quantities
    quantity_on_hand DECIMAL(15,3) NOT NULL DEFAULT 0,
    quantity_reserved DECIMAL(15,3) DEFAULT 0,
    quantity_available DECIMAL(15,3) GENERATED ALWAYS AS (quantity_on_hand - quantity_reserved) STORED,
    
    -- Costing
    average_cost DECIMAL(15,4) NOT NULL,
    total_value DECIMAL(20,2) GENERATED ALWAYS AS (quantity_on_hand * average_cost) STORED,
    
    -- Expiry
    manufacture_date DATE,
    expiry_date DATE,
    
    -- Location
    warehouse_location VARCHAR(50),
    
    last_movement_at TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_item_branch (item_id, branch_id),
    INDEX idx_batch_number (batch_number),
    INDEX idx_expiry_date (expiry_date),
    UNIQUE KEY uk_stock (item_id, branch_id, batch_number, serial_number)
);
```

### 5.3 stock_movements
```sql
CREATE TABLE stock_movements (
    movement_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    batch_number VARCHAR(50),
    
    movement_type ENUM(
        'PURCHASE', 'SALE', 'ADJUSTMENT', 'TRANSFER_IN', 
        'TRANSFER_OUT', 'RETURN', 'REPACKAGE', 'DAMAGE', 'EXPIRY'
    ) NOT NULL,
    
    -- Quantities
    quantity DECIMAL(15,3) NOT NULL,
    unit_cost DECIMAL(15,4),
    total_cost DECIMAL(20,2),
    
    -- Reference
    reference_type VARCHAR(50),
    reference_number VARCHAR(100),
    reference_id BIGINT,
    
    -- Details
    movement_date DATE NOT NULL,
    notes TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    
    INDEX idx_item_id (item_id),
    INDEX idx_branch_id (branch_id),
    INDEX idx_movement_date (movement_date),
    INDEX idx_movement_type (movement_type),
    INDEX idx_reference (reference_type, reference_number)
);
```

### 5.4 stock_adjustments
```sql
CREATE TABLE stock_adjustments (
    adjustment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    adjustment_number VARCHAR(50) UNIQUE NOT NULL,
    branch_id BIGINT NOT NULL,
    adjustment_type ENUM('INCREASE', 'DECREASE', 'STOCK_TAKE') NOT NULL,
    adjustment_reason ENUM('DAMAGE', 'EXPIRY', 'THEFT', 'STOCK_TAKE', 'ERROR_CORRECTION', 'OTHER') NOT NULL,
    
    adjustment_date DATE NOT NULL,
    notes TEXT,
    photo_url VARCHAR(255),
    
    status ENUM('DRAFT', 'PENDING_APPROVAL', 'APPROVED', 'REJECTED', 'POSTED') DEFAULT 'DRAFT',
    
    approved_by BIGINT,
    approved_at TIMESTAMP NULL,
    posted_at TIMESTAMP NULL,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    
    INDEX idx_adjustment_number (adjustment_number),
    INDEX idx_branch_id (branch_id),
    INDEX idx_status (status),
    INDEX idx_adjustment_date (adjustment_date)
);
```

### 5.5 stock_adjustment_items
```sql
CREATE TABLE stock_adjustment_items (
    adjustment_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    adjustment_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    batch_number VARCHAR(50),
    
    system_quantity DECIMAL(15,3) NOT NULL,
    actual_quantity DECIMAL(15,3) NOT NULL,
    variance_quantity DECIMAL(15,3) GENERATED ALWAYS AS (actual_quantity - system_quantity) STORED,
    
    unit_cost DECIMAL(15,4) NOT NULL,
    variance_value DECIMAL(20,2) GENERATED ALWAYS AS ((actual_quantity - system_quantity) * unit_cost) STORED,
    
    notes VARCHAR(255),
    
    FOREIGN KEY (adjustment_id) REFERENCES stock_adjustments(adjustment_id) ON DELETE CASCADE,
    INDEX idx_adjustment_id (adjustment_id),
    INDEX idx_item_id (item_id)
);
```

### 5.6 repackaging
```sql
CREATE TABLE repackaging (
    repackage_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    repackage_number VARCHAR(50) UNIQUE NOT NULL,
    branch_id BIGINT NOT NULL,
    repackage_date DATE NOT NULL,
    
    -- Source
    source_item_id BIGINT NOT NULL,
    source_uom_id BIGINT NOT NULL,
    source_quantity DECIMAL(15,3) NOT NULL,
    source_batch_number VARCHAR(50),
    source_cost DECIMAL(15,4),
    
    -- Destination
    dest_item_id BIGINT NOT NULL,
    dest_uom_id BIGINT NOT NULL,
    dest_quantity DECIMAL(15,3) NOT NULL,
    dest_batch_number VARCHAR(50),
    dest_cost DECIMAL(15,4),
    
    conversion_factor DECIMAL(10,4),
    notes TEXT,
    
    status ENUM('DRAFT', 'COMPLETED', 'CANCELLED') DEFAULT 'DRAFT',
    posted_at TIMESTAMP NULL,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    
    INDEX idx_repackage_number (repackage_number),
    INDEX idx_branch_id (branch_id),
    INDEX idx_source_item (source_item_id),
    INDEX idx_dest_item (dest_item_id)
);
```

---

## 6. SALES SERVICE DATABASE (`supasoft_sales`)

### 6.1 customers
```sql
CREATE TABLE customers (
    customer_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_code VARCHAR(50) UNIQUE NOT NULL,
    customer_name VARCHAR(200) NOT NULL,
    customer_type ENUM('INDIVIDUAL', 'CORPORATE') DEFAULT 'INDIVIDUAL',
    
    -- Contact
    phone_number VARCHAR(20),
    email VARCHAR(100),
    
    -- Address
    address_line1 VARCHAR(255),
    city VARCHAR(100),
    
    -- Credit
    credit_limit DECIMAL(15,2) DEFAULT 0,
    credit_balance DECIMAL(15,2) DEFAULT 0,
    credit_days INT DEFAULT 0,
    
    -- Loyalty
    loyalty_points INT DEFAULT 0,
    
    -- Tax
    tax_pin VARCHAR(50),
    
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_customer_code (customer_code),
    INDEX idx_phone_number (phone_number),
    FULLTEXT idx_customer_name (customer_name)
);
```

### 6.2 cashier_shifts
```sql
CREATE TABLE cashier_shifts (
    shift_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    shift_number VARCHAR(50) UNIQUE NOT NULL,
    cashier_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    till_number VARCHAR(20),
    
    -- Float
    opening_float DECIMAL(15,2) NOT NULL,
    closing_cash DECIMAL(15,2),
    expected_cash DECIMAL(15,2),
    cash_variance DECIMAL(15,2),
    
    -- Shift Times
    shift_start TIMESTAMP NOT NULL,
    shift_end TIMESTAMP NULL,
    
    status ENUM('OPEN', 'CLOSED') DEFAULT 'OPEN',
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_cashier_id (cashier_id),
    INDEX idx_branch_id (branch_id),
    INDEX idx_shift_start (shift_start),
    INDEX idx_status (status)
);
```

### 6.3 sales_invoices
```sql
CREATE TABLE sales_invoices (
    invoice_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    invoice_number VARCHAR(50) UNIQUE NOT NULL,
    invoice_date DATE NOT NULL,
    invoice_time TIME NOT NULL,
    
    branch_id BIGINT NOT NULL,
    customer_id BIGINT,
    cashier_id BIGINT NOT NULL,
    shift_id BIGINT,
    
    -- Totals
    subtotal DECIMAL(15,2) NOT NULL,
    discount_amount DECIMAL(15,2) DEFAULT 0,
    tax_amount DECIMAL(15,2) NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL,
    
    -- Payment
    paid_amount DECIMAL(15,2) NOT NULL,
    change_amount DECIMAL(15,2) DEFAULT 0,
    payment_status ENUM('PAID', 'PARTIAL', 'UNPAID') DEFAULT 'PAID',
    
    -- KRA eTIMS
    cu_invoice_number VARCHAR(100),
    qr_code_url VARCHAR(255),
    etims_status ENUM('PENDING', 'SUBMITTED', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    etims_response TEXT,
    
    -- Status
    invoice_status ENUM('ACTIVE', 'RETURNED', 'CANCELLED') DEFAULT 'ACTIVE',
    
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_invoice_number (invoice_number),
    INDEX idx_branch_id (branch_id),
    INDEX idx_customer_id (customer_id),
    INDEX idx_cashier_id (cashier_id),
    INDEX idx_invoice_date (invoice_date),
    INDEX idx_cu_invoice_number (cu_invoice_number)
);
```

### 6.4 sales_invoice_items
```sql
CREATE TABLE sales_invoice_items (
    invoice_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    invoice_id BIGINT NOT NULL,
    line_number INT NOT NULL,
    
    item_id BIGINT NOT NULL,
    uom_id BIGINT NOT NULL,
    batch_number VARCHAR(50),
    
    item_name VARCHAR(200) NOT NULL,
    quantity DECIMAL(15,3) NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    discount_percentage DECIMAL(5,2) DEFAULT 0,
    discount_amount DECIMAL(15,2) DEFAULT 0,
    
    line_total DECIMAL(15,2) NOT NULL,
    tax_rate DECIMAL(5,2) NOT NULL,
    tax_amount DECIMAL(15,2) NOT NULL,
    final_amount DECIMAL(15,2) NOT NULL,
    
    -- Costing
    unit_cost DECIMAL(15,4),
    total_cost DECIMAL(20,2),
    profit_margin DECIMAL(20,2),
    
    FOREIGN KEY (invoice_id) REFERENCES sales_invoices(invoice_id) ON DELETE CASCADE,
    INDEX idx_invoice_id (invoice_id),
    INDEX idx_item_id (item_id)
);
```

### 6.5 payments
```sql
CREATE TABLE payments (
    payment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    invoice_id BIGINT NOT NULL,
    payment_method ENUM('CASH', 'MPESA', 'CARD', 'BANK_TRANSFER', 'CHEQUE', 'CREDIT') NOT NULL,
    payment_amount DECIMAL(15,2) NOT NULL,
    
    -- M-Pesa Details
    mpesa_receipt_number VARCHAR(50),
    mpesa_phone_number VARCHAR(20),
    
    -- Card Details
    card_last_four VARCHAR(4),
    card_type VARCHAR(20),
    authorization_code VARCHAR(50),
    
    -- Cheque Details
    cheque_number VARCHAR(50),
    cheque_bank VARCHAR(100),
    
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (invoice_id) REFERENCES sales_invoices(invoice_id) ON DELETE CASCADE,
    INDEX idx_invoice_id (invoice_id),
    INDEX idx_payment_method (payment_method),
    INDEX idx_mpesa_receipt (mpesa_receipt_number)
);
```

### 6.6 sales_returns
```sql
CREATE TABLE sales_returns (
    return_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    return_number VARCHAR(50) UNIQUE NOT NULL,
    original_invoice_id BIGINT NOT NULL,
    return_date DATE NOT NULL,
    
    branch_id BIGINT NOT NULL,
    customer_id BIGINT,
    processed_by BIGINT NOT NULL,
    
    return_reason ENUM('DEFECTIVE', 'EXPIRED', 'WRONG_ITEM', 'CUSTOMER_REQUEST', 'OTHER') NOT NULL,
    return_notes TEXT,
    
    total_return_amount DECIMAL(15,2) NOT NULL,
    refund_method ENUM('CASH', 'MPESA', 'CREDIT_NOTE') NOT NULL,
    
    -- Credit Note
    credit_note_number VARCHAR(50),
    credit_note_amount DECIMAL(15,2),
    
    status ENUM('DRAFT', 'APPROVED', 'COMPLETED', 'REJECTED') DEFAULT 'DRAFT',
    approved_by BIGINT,
    approved_at TIMESTAMP NULL,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (original_invoice_id) REFERENCES sales_invoices(invoice_id),
    INDEX idx_return_number (return_number),
    INDEX idx_original_invoice (original_invoice_id),
    INDEX idx_return_date (return_date)
);
```

### 6.7 sales_return_items
```sql
CREATE TABLE sales_return_items (
    return_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    return_id BIGINT NOT NULL,
    invoice_item_id BIGINT NOT NULL,
    
    item_id BIGINT NOT NULL,
    return_quantity DECIMAL(15,3) NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    return_amount DECIMAL(15,2) NOT NULL,
    
    FOREIGN KEY (return_id) REFERENCES sales_returns(return_id) ON DELETE CASCADE,
    INDEX idx_return_id (return_id),
    INDEX idx_item_id (item_id)
);
```

---

## 7. PURCHASE SERVICE DATABASE (`supasoft_purchase`)

### 7.1 suppliers
```sql
CREATE TABLE suppliers (
    supplier_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    supplier_code VARCHAR(50) UNIQUE NOT NULL,
    supplier_name VARCHAR(200) NOT NULL,
    
    -- Contact
    contact_person VARCHAR(100),
    phone_number VARCHAR(20),
    email VARCHAR(100),
    
    -- Address
    address_line1 VARCHAR(255),
    city VARCHAR(100),
    
    -- Payment Terms
    payment_terms_days INT DEFAULT 30,
    credit_limit DECIMAL(15,2),
    
    -- Tax
    tax_pin VARCHAR(50),
    
    -- Banking
    bank_name VARCHAR(100),
    bank_account_number VARCHAR(50),
    
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_supplier_code (supplier_code),
    FULLTEXT idx_supplier_name (supplier_name)
);
```

### 7.2 purchase_orders
```sql
CREATE TABLE purchase_orders (
    po_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    po_number VARCHAR(50) UNIQUE NOT NULL,
    po_date DATE NOT NULL,
    supplier_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    
    expected_delivery_date DATE,
    delivery_address TEXT,
    
    subtotal DECIMAL(15,2) NOT NULL,
    tax_amount DECIMAL(15,2) NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL,
    
    status ENUM('DRAFT', 'PENDING_APPROVAL', 'APPROVED', 'SENT', 'PARTIALLY_RECEIVED', 'RECEIVED', 'CANCELLED') DEFAULT 'DRAFT',
    
    notes TEXT,
    terms_and_conditions TEXT,
    
    requested_by BIGINT,
    approved_by BIGINT,
    approved_at TIMESTAMP NULL,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
    INDEX idx_po_number (po_number),
    INDEX idx_supplier_id (supplier_id),
    INDEX idx_branch_id (branch_id),
    INDEX idx_status (status),
    INDEX idx_po_date (po_date)
);
```

### 7.3 purchase_order_items
```sql
CREATE TABLE purchase_order_items (
    po_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    po_id BIGINT NOT NULL,
    line_number INT NOT NULL,
    
    item_id BIGINT NOT NULL,
    uom_id BIGINT NOT NULL,
    
    item_name VARCHAR(200) NOT NULL,
    ordered_quantity DECIMAL(15,3) NOT NULL,
    received_quantity DECIMAL(15,3) DEFAULT 0,
    pending_quantity DECIMAL(15,3) GENERATED ALWAYS AS (ordered_quantity - received_quantity) STORED,
    
    unit_price DECIMAL(15,2) NOT NULL,
    discount_percentage DECIMAL(5,2) DEFAULT 0,
    tax_rate DECIMAL(5,2) NOT NULL,
    
    line_total DECIMAL(15,2) NOT NULL,
    
    notes VARCHAR(255),
    
    FOREIGN KEY (po_id) REFERENCES purchase_orders(po_id) ON DELETE CASCADE,
    INDEX idx_po_id (po_id),
    INDEX idx_item_id (item_id)
);
```

### 7.4 goods_received_notes
```sql
CREATE TABLE goods_received_notes (
    grn_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    grn_number VARCHAR(50) UNIQUE NOT NULL,
    grn_date DATE NOT NULL,
    po_id BIGINT,
    supplier_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    
    supplier_invoice_number VARCHAR(50),
    supplier_invoice_date DATE,
    
    total_amount DECIMAL(15,2) NOT NULL,
    
    status ENUM('DRAFT', 'QUALITY_CHECK', 'APPROVED', 'POSTED', 'REJECTED') DEFAULT 'DRAFT',
    
    received_by BIGINT,
    approved_by BIGINT,
    approved_at TIMESTAMP NULL,
    posted_at TIMESTAMP NULL,
    
    notes TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (po_id) REFERENCES purchase_orders(po_id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
    INDEX idx_grn_number (grn_number),
    INDEX idx_po_id (po_id),
    INDEX idx_supplier_id (supplier_id),
    INDEX idx_grn_date (grn_date),
    INDEX idx_status (status)
);
```

### 7.5 grn_items
```sql
CREATE TABLE grn_items (
    grn_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    grn_id BIGINT NOT NULL,
    po_item_id BIGINT,
    
    item_id BIGINT NOT NULL,
    uom_id BIGINT NOT NULL,
    
    ordered_quantity DECIMAL(15,3),
    received_quantity DECIMAL(15,3) NOT NULL,
    accepted_quantity DECIMAL(15,3) NOT NULL,
    rejected_quantity DECIMAL(15,3) DEFAULT 0,
    
    unit_cost DECIMAL(15,4) NOT NULL,
    line_total DECIMAL(15,2) NOT NULL,
    
    -- Batch & Expiry
    batch_number VARCHAR(50),
    manufacture_date DATE,
    expiry_date DATE,
    
    rejection_reason VARCHAR(255),
    notes VARCHAR(255),
    
    FOREIGN KEY (grn_id) REFERENCES goods_received_notes(grn_id) ON DELETE CASCADE,
    INDEX idx_grn_id (grn_id),
    INDEX idx_item_id (item_id),
    INDEX idx_batch_number (batch_number)
);
```

---

## 8. TRANSFER SERVICE DATABASE (`supasoft_transfer`)

### 8.1 stock_transfers
```sql
CREATE TABLE stock_transfers (
    transfer_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    transfer_number VARCHAR(50) UNIQUE NOT NULL,
    transfer_date DATE NOT NULL,
    
    from_branch_id BIGINT NOT NULL,
    to_branch_id BIGINT NOT NULL,
    
    transfer_type ENUM('STANDARD', 'EMERGENCY', 'REBALANCE') DEFAULT 'STANDARD',
    
    total_cost DECIMAL(15,2),
    
    status ENUM('DRAFT', 'PENDING_APPROVAL', 'APPROVED', 'IN_TRANSIT', 'RECEIVED', 'REJECTED', 'CANCELLED') DEFAULT 'DRAFT',
    
    requested_by BIGINT,
    approved_by BIGINT,
    approved_at TIMESTAMP NULL,
    sent_at TIMESTAMP NULL,
    received_at TIMESTAMP NULL,
    received_by BIGINT,
    
    notes TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_transfer_number (transfer_number),
    INDEX idx_from_branch (from_branch_id),
    INDEX idx_to_branch (to_branch_id),
    INDEX idx_status (status),
    INDEX idx_transfer_date (transfer_date)
);
```

### 8.2 stock_transfer_items
```sql
CREATE TABLE stock_transfer_items (
    transfer_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    transfer_id BIGINT NOT NULL,
    line_number INT NOT NULL,
    
    item_id BIGINT NOT NULL,
    uom_id BIGINT NOT NULL,
    batch_number VARCHAR(50),
    
    requested_quantity DECIMAL(15,3) NOT NULL,
    sent_quantity DECIMAL(15,3),
    received_quantity DECIMAL(15,3),
    variance_quantity DECIMAL(15,3),
    
    unit_cost DECIMAL(15,4),
    total_cost DECIMAL(20,2),
    
    variance_reason VARCHAR(255),
    notes VARCHAR(255),
    
    FOREIGN KEY (transfer_id) REFERENCES stock_transfers(transfer_id) ON DELETE CASCADE,
    INDEX idx_transfer_id (transfer_id),
    INDEX idx_item_id (item_id)
);
```

---

## 9. NOTIFICATIONS DATABASE (`supasoft_notifications`)

### 9.1 notifications
```sql
CREATE TABLE notifications (
    notification_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    notification_type ENUM('EMAIL', 'SMS', 'PUSH', 'WHATSAPP') NOT NULL,
    recipient VARCHAR(255) NOT NULL,
    subject VARCHAR(255),
    message TEXT NOT NULL,
    
    status ENUM('PENDING', 'SENT', 'DELIVERED', 'FAILED') DEFAULT 'PENDING',
    
    priority ENUM('LOW', 'NORMAL', 'HIGH', 'URGENT') DEFAULT 'NORMAL',
    
    template_code VARCHAR(50),
    metadata JSON,
    
    sent_at TIMESTAMP NULL,
    delivered_at TIMESTAMP NULL,
    error_message TEXT,
    retry_count INT DEFAULT 0,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_status (status),
    INDEX idx_recipient (recipient),
    INDEX idx_notification_type (notification_type),
    INDEX idx_created_at (created_at)
);
```

---

## 10. AUDIT DATABASE (`supasoft_audit`)

### 10.1 audit_logs
```sql
CREATE TABLE audit_logs (
    audit_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    username VARCHAR(50),
    
    action_type ENUM('CREATE', 'READ', 'UPDATE', 'DELETE', 'LOGIN', 'LOGOUT', 'EXPORT', 'PRINT') NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    entity_id BIGINT,
    
    old_values JSON,
    new_values JSON,
    
    ip_address VARCHAR(45),
    user_agent TEXT,
    
    branch_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_user_id (user_id),
    INDEX idx_action_type (action_type),
    INDEX idx_entity_type (entity_type),
    INDEX idx_entity_id (entity_id),
    INDEX idx_created_at (created_at),
    INDEX idx_branch_id (branch_id)
);
```

---

## 11. COMMON TABLES (Shared Reference Data)

Note: These tables may be replicated across services or accessed via API.

### 11.1 countries
```sql
CREATE TABLE countries (
    country_id INT PRIMARY KEY AUTO_INCREMENT,
    country_code VARCHAR(3) NOT NULL,
    country_name VARCHAR(100) NOT NULL,
    currency_code VARCHAR(3),
    phone_code VARCHAR(10),
    is_active BOOLEAN DEFAULT TRUE
);
```

### 11.2 counties (Kenya-specific)
```sql
CREATE TABLE counties (
    county_id INT PRIMARY KEY AUTO_INCREMENT,
    county_code VARCHAR(10) NOT NULL,
    county_name VARCHAR(100) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);
```

---

## 12. DATABASE INDEXES STRATEGY

### 12.1 Primary Keys
- All tables use BIGINT AUTO_INCREMENT for primary keys
- Named as `{table_name}_id` (e.g., `item_id`, `invoice_id`)

### 12.2 Foreign Keys
- All foreign key relationships are explicitly defined
- ON DELETE CASCADE for child records
- Indexed for performance

### 12.3 Unique Constraints
- Business keys (codes, numbers) have UNIQUE constraints
- Composite unique keys for junction tables

### 12.4 Search Indexes
- B-Tree indexes on frequently queried columns
- FULLTEXT indexes for name/description searches
- Composite indexes for multi-column filters

### 12.5 Date Indexes
- All date/timestamp columns used in WHERE/ORDER BY are indexed

---

## 13. DATA RETENTION POLICY

| Table Type | Retention Period | Archive Strategy |
|-----------|------------------|------------------|
| Transactional (Sales, Stock) | 7 years | Annual archival to separate schema |
| Audit Logs | 5 years | Quarterly archival |
| User Activity | 2 years | Annual deletion |
| Notifications | 90 days | Monthly deletion |
| Temporary Data | 30 days | Weekly cleanup |

---

## 14. BACKUP STRATEGY

### 14.1 Backup Schedule
- **Full Backup**: Daily at 3:00 AM EAT
- **Incremental Backup**: Every 6 hours
- **Transaction Log Backup**: Every 15 minutes

### 14.2 Backup Retention
- Daily backups: 30 days
- Weekly backups: 3 months
- Monthly backups: 1 year
- Yearly backups: 7 years (compliance)

### 14.3 Backup Location
- Primary: On-site NAS
- Secondary: Cloud storage (AWS S3/Azure Blob)
- Tertiary: Off-site physical backup (monthly)

---

**Document Version**: 1.0  
**Last Updated**: November 2025  
**Database Version**: MySQL 8.0.35  
**Schema Version**: 1.0.0


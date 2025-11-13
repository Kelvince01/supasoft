-- Insert default roles
INSERT INTO roles (role_name, description, is_active, created_by) VALUES
('ROLE_SUPER_ADMIN', 'Super Administrator with full system access', TRUE, 'system'),
('ROLE_ADMIN', 'Administrator with administrative privileges', TRUE, 'system'),
('ROLE_MANAGER', 'Manager with management privileges', TRUE, 'system'),
('ROLE_BRANCH_MANAGER', 'Branch Manager with branch-level management', TRUE, 'system'),
('ROLE_CASHIER', 'Cashier with POS access', TRUE, 'system'),
('ROLE_SALES_PERSON', 'Sales person', TRUE, 'system'),
('ROLE_STOCK_KEEPER', 'Stock keeper with inventory management', TRUE, 'system'),
('ROLE_ACCOUNTANT', 'Accountant with financial access', TRUE, 'system'),
('ROLE_AUDITOR', 'Auditor with read-only access to all records', TRUE, 'system'),
('ROLE_PURCHASER', 'Purchaser with procurement access', TRUE, 'system');


-- Insert default customer categories
INSERT INTO customer_categories (code, name, description, default_credit_limit, default_discount_percentage, loyalty_tier, loyalty_multiplier, points_per_currency, grace_period_days, status) 
VALUES
('RETAIL', 'Retail Customer', 'Walk-in retail customers', 0.00, 0.00, 'BRONZE', 1.00, 1.00, 0, 'ACTIVE'),
('WHOLESALE', 'Wholesale Customer', 'Wholesale business customers', 50000.00, 5.00, 'SILVER', 1.25, 1.50, 7, 'ACTIVE'),
('VIP', 'VIP Customer', 'VIP premium customers', 100000.00, 10.00, 'GOLD', 1.50, 2.00, 14, 'ACTIVE'),
('CORPORATE', 'Corporate Customer', 'Large corporate accounts', 200000.00, 15.00, 'PLATINUM', 2.00, 2.50, 30, 'ACTIVE');


-- Insert default admin user
-- Password: Admin@123 (BCrypt encoded with strength 12)
INSERT INTO users (username, email, password, first_name, last_name, is_active, is_email_verified, created_by) VALUES
('admin', 'admin@supasoft.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GK.w3r8K9F1C', 'System', 'Administrator', TRUE, TRUE, 'system');

-- Assign SUPER_ADMIN role to admin user
INSERT INTO user_roles (user_id, role_id) 
SELECT u.user_id, r.role_id 
FROM users u, roles r 
WHERE u.username = 'admin' AND r.role_name = 'ROLE_SUPER_ADMIN';


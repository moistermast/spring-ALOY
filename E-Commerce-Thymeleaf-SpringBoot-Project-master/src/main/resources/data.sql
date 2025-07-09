-- Initial data for E-Commerce application
-- This file contains sample data to populate the database

-- Insert sample categories
INSERT INTO ec_category (id, name, description, created_at) VALUES 
('550e8400-e29b-41d4-a716-446655440001', 'RINGS', 'Elegant rings for every occasion', CURRENT_TIMESTAMP),
('550e8400-e29b-41d4-a716-446655440002', 'CHAINS', 'Stylish chains and necklaces', CURRENT_TIMESTAMP),
('550e8400-e29b-41d4-a716-446655440003', 'BRACELETS', 'Beautiful bracelets and wrist accessories', CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

-- Insert sample products for RINGS category
INSERT INTO ec_product (id, name, description, price, image, sale, sale_price, views, featured, featured_image, created_at, color, size, category_id) VALUES 
('660e8400-e29b-41d4-a716-446655440001', 'Classic Silver Ring', 'Elegant silver ring with timeless design', 89.99, 'Rings/1.png', false, null, 15, true, 'Rings/1.png', CURRENT_TIMESTAMP, 'Silver', '6,7,8,9,10', '550e8400-e29b-41d4-a716-446655440001'),
('660e8400-e29b-41d4-a716-446655440002', 'Modern Design Ring', 'Contemporary ring with unique geometric patterns', 125.00, 'Rings/3.png', true, 99.99, 8, false, null, CURRENT_TIMESTAMP, 'Silver', '6,7,8,9,10', '550e8400-e29b-41d4-a716-446655440001'),
('660e8400-e29b-41d4-a716-446655440003', 'Elegant Band Ring', 'Simple yet sophisticated band ring', 75.50, 'Rings/IMGP7466.png', false, null, 22, true, 'Rings/IMGP7466.png', CURRENT_TIMESTAMP, 'Silver', '6,7,8,9,10', '550e8400-e29b-41d4-a716-446655440001')
ON CONFLICT (id) DO NOTHING;

-- Insert sample products for CHAINS category
INSERT INTO ec_product (id, name, description, price, image, sale, sale_price, views, featured, featured_image, created_at, color, size, category_id) VALUES 
('660e8400-e29b-41d4-a716-446655440004', 'Silver Cuban Chain', 'Classic Cuban link chain in sterling silver', 120.00, 'Chains/Silver Cuban.jpg', false, null, 25, true, 'Chains/Silver Cuban.jpg', CURRENT_TIMESTAMP, 'Silver', '18",20",22",24"', '550e8400-e29b-41d4-a716-446655440002'),
('660e8400-e29b-41d4-a716-446655440005', 'Cuban Chain Front View', 'Premium Cuban chain with detailed front view', 135.00, 'Chains/Silver Cuban front.jpg', true, 110.00, 18, false, null, CURRENT_TIMESTAMP, 'Silver', '18",20",22",24"', '550e8400-e29b-41d4-a716-446655440002'),
('660e8400-e29b-41d4-a716-446655440006', 'Lily Flower Chain', 'Delicate chain with lily flower design', 85.00, 'Chains/lily flower chain.jpg', false, null, 20, false, null, CURRENT_TIMESTAMP, 'Silver', '16",18",20"', '550e8400-e29b-41d4-a716-446655440002')
ON CONFLICT (id) DO NOTHING;

-- Insert sample products for BRACELETS category
INSERT INTO ec_product (id, name, description, price, image, sale, sale_price, views, featured, featured_image, created_at, color, size, category_id) VALUES 
('660e8400-e29b-41d4-a716-446655440007', 'Couple Black Bracelet', 'Matching black bracelets for couples', 65.00, 'Bracelets/couple black.jpg', false, null, 30, true, 'Bracelets/couple black.jpg', CURRENT_TIMESTAMP, 'Black', '7",8",9"', '550e8400-e29b-41d4-a716-446655440003'),
('660e8400-e29b-41d4-a716-446655440008', 'Couple White Bracelet', 'Elegant white bracelets for couples', 70.00, 'Bracelets/couple white.jpg', true, 55.00, 28, false, null, CURRENT_TIMESTAMP, 'White', '7",8",9"', '550e8400-e29b-41d4-a716-446655440003'),
('660e8400-e29b-41d4-a716-446655440009', 'Yin Yang Couple Bracelet', 'Yin yang design couple bracelets', 85.00, 'Bracelets/Couple yin yan-min.png', false, null, 35, true, 'Bracelets/Couple yin yan-min.png', CURRENT_TIMESTAMP, 'Black/White', '7",8",9"', '550e8400-e29b-41d4-a716-446655440003')
ON CONFLICT (id) DO NOTHING; 
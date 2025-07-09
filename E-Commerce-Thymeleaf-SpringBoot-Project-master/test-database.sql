-- Database Verification Script
-- Run this script to verify that all tables and data are properly created

-- Check if tables exist
SELECT 'Checking table existence...' as status;

SELECT 
    table_name,
    CASE WHEN table_name IS NOT NULL THEN 'EXISTS' ELSE 'MISSING' END as status
FROM information_schema.tables 
WHERE table_schema = 'public' 
AND table_name LIKE 'ec_%'
ORDER BY table_name;

-- Check table counts
SELECT 'Checking data counts...' as status;

SELECT 'ec_category' as table_name, COUNT(*) as record_count FROM ec_category
UNION ALL
SELECT 'ec_product' as table_name, COUNT(*) as record_count FROM ec_product
UNION ALL
SELECT 'ec_user' as table_name, COUNT(*) as record_count FROM ec_user
UNION ALL
SELECT 'ec_cart' as table_name, COUNT(*) as record_count FROM ec_cart
UNION ALL
SELECT 'ec_order' as table_name, COUNT(*) as record_count FROM ec_order
UNION ALL
SELECT 'ec_order_item' as table_name, COUNT(*) as record_count FROM ec_order_item;

-- Check sample data
SELECT 'Checking sample data...' as status;

-- Categories
SELECT 'Categories:' as info;
SELECT name, description FROM ec_category ORDER BY name;

-- Products by category
SELECT 'Products by category:' as info;
SELECT 
    c.name as category_name,
    p.name as product_name,
    p.price,
    p.featured,
    p.sale
FROM ec_product p
JOIN ec_category c ON p.category_id = c.id
ORDER BY c.name, p.name;

-- Featured products
SELECT 'Featured products:' as info;
SELECT name, price, featured_image FROM ec_product WHERE featured = true ORDER BY name;

-- Sale products
SELECT 'Sale products:' as info;
SELECT name, price, sale_price FROM ec_product WHERE sale = true ORDER BY name;

-- Check foreign key relationships
SELECT 'Checking foreign key relationships...' as status;

-- Products with categories
SELECT 
    COUNT(*) as products_with_categories,
    (SELECT COUNT(*) FROM ec_product) as total_products
FROM ec_product p
JOIN ec_category c ON p.category_id = c.id;

-- Verify indexes exist
SELECT 'Checking indexes...' as status;

SELECT 
    indexname,
    tablename
FROM pg_indexes 
WHERE tablename LIKE 'ec_%'
ORDER BY tablename, indexname; 
-- E-Commerce Database Schema
-- This file contains the complete database schema for the e-commerce application

-- Create ec_category table
CREATE TABLE IF NOT EXISTS ec_category (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    parent_id CHAR(36),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create ec_product table
CREATE TABLE IF NOT EXISTS ec_product (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    image VARCHAR(500),
    sale BOOLEAN DEFAULT FALSE,
    sale_price DECIMAL(10,2),
    views INTEGER DEFAULT 0,
    featured BOOLEAN DEFAULT FALSE,
    featured_image VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    color VARCHAR(100),
    size VARCHAR(100),
    category_id CHAR(36),
    FOREIGN KEY (category_id) REFERENCES ec_category(id) ON DELETE SET NULL
);

-- Create ec_user table
CREATE TABLE IF NOT EXISTS ec_user (
    id CHAR(36) PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create ec_cart table
CREATE TABLE IF NOT EXISTS ec_cart (
    id CHAR(36) PRIMARY KEY,
    quantity INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id CHAR(36),
    product_id CHAR(36),
    FOREIGN KEY (user_id) REFERENCES ec_user(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES ec_product(id) ON DELETE CASCADE
);

-- Create ec_order table
CREATE TABLE IF NOT EXISTS ec_order (
    id CHAR(36) PRIMARY KEY,
    address TEXT,
    total DECIMAL(10,2) NOT NULL,
    status VARCHAR(100) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id CHAR(36),
    FOREIGN KEY (user_id) REFERENCES ec_user(id) ON DELETE CASCADE
);

-- Create ec_order_item table
CREATE TABLE IF NOT EXISTS ec_order_item (
    id CHAR(36) PRIMARY KEY,
    quantity INTEGER NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    order_id CHAR(36),
    product_id CHAR(36),
    FOREIGN KEY (order_id) REFERENCES ec_order(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES ec_product(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_product_category ON ec_product(category_id);
CREATE INDEX IF NOT EXISTS idx_product_featured ON ec_product(featured);
CREATE INDEX IF NOT EXISTS idx_product_sale ON ec_product(sale);
CREATE INDEX IF NOT EXISTS idx_cart_user ON ec_cart(user_id);
CREATE INDEX IF NOT EXISTS idx_cart_product ON ec_cart(product_id);
CREATE INDEX IF NOT EXISTS idx_order_user ON ec_order(user_id);
CREATE INDEX IF NOT EXISTS idx_order_item_order ON ec_order_item(order_id);
CREATE INDEX IF NOT EXISTS idx_order_item_product ON ec_order_item(product_id);
CREATE INDEX IF NOT EXISTS idx_user_email ON ec_user(email); 
DROP TABLE IF EXISTS tbl_order_items;
DROP TABLE IF EXISTS tbl_orders;
DROP TABLE IF EXISTS tbl_products;

CREATE TABLE tbl_products (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(255) NOT NULL,
                              description TEXT NOT NULL,
                              price DECIMAL(10, 2) NOT NULL,
                              stock INT NOT NULL,
                              image_url VARCHAR(255),
                              status VARCHAR(20) NOT NULL CHECK (status IN ('AVAILABLE', 'UNAVAILABLE'))
);

CREATE TABLE tbl_orders (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            total_amount DECIMAL(10, 2) NOT NULL,
                            status VARCHAR(20) NOT NULL CHECK (status IN ('NEW_ORDER', 'PENDING_PAYMENT', 'PAID')),
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tbl_order_items (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 order_id BIGINT,
                                 product_id BIGINT,
                                 quantity INT,
                                 price_at_purchase DECIMAL(10, 2),
                                 FOREIGN KEY (order_id) REFERENCES tbl_orders(id),
                                 FOREIGN KEY (product_id) REFERENCES tbl_products(id)
);

DELETE FROM tbl_orders;
DELETE FROM tbl_products;
DELETE FROM tbl_order_items;

INSERT INTO tbl_orders (total_amount, status, created_at) VALUES (0.0, 'NEW_ORDER', '2024-09-10 10:00:00');

INSERT INTO tbl_products (name, description, price, stock, image_url, status)
VALUES ('Laptop Pro', 'High-performance laptop for professionals', 1200000.00, 25, 'https://images.pexels.com/photos/18105/pexels-photo.jpg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 'AVAILABLE');

INSERT INTO tbl_products (name, description, price, stock, image_url, status)
VALUES ('Wireless Mouse', 'Ergonomic wireless mouse', 100000.00, 100, 'https://images.pexels.com/photos/5082559/pexels-photo-5082559.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 'AVAILABLE');

INSERT INTO tbl_products (name, description, price, stock, image_url, status)
VALUES ('Elegant Chair', 'Comfortable elegant chair with lumbar support', 199000.00, 50, 'https://images.pexels.com/photos/11112735/pexels-photo-11112735.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 'AVAILABLE');

INSERT INTO tbl_products (name, description, price, stock, image_url, status)
VALUES ('Mechanical Keyboard', 'RGB mechanical keyboard with blue switches', 89000.00, 0, 'https://images.pexels.com/photos/6623763/pexels-photo-6623763.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 'UNAVAILABLE');

INSERT INTO tbl_products (name, description, price, stock, image_url, status)
VALUES ('4K Monitor', '27-inch 4K Ultra HD monitor', 399000.00, 0, 'https://img.freepik.com/foto-gratis/tv-pantalla-ancha_144627-12166.jpg?t=st=1726077350~exp=1726080950~hmac=e8ab6cbae3ea817f742c2f4b31fb2d59405210f5378e7e985345742b57134740&w=1380', 'UNAVAILABLE');

INSERT INTO tbl_products (name, description, price, stock, image_url, status)
VALUES ('Smartphone Max', 'Latest smartphone with advanced features', 999000.00, 15, 'https://img.freepik.com/foto-gratis/hermosa-flor-eustoma-telefono-inteligente-contra-fondo-rosa_23-2147923975.jpg?t=st=1726077485~exp=1726081085~hmac=b11d295e0ae3964cd3dd09ceac742984e46d651f3b12f9c0026a38c4a3ed9eb2&w=740', 'AVAILABLE');

INSERT INTO tbl_products (name, description, price, stock, image_url, status)
VALUES ('Bluetooth Speaker', 'Portable Bluetooth speaker with excellent sound quality', 490000.00, 80, 'https://img.freepik.com/foto-gratis/dispositivo-digital-altavoz-inteligente-blanco-inalambrico_53876-96821.jpg?t=st=1726077517~exp=1726081117~hmac=f25061cd66ef84f2e881f8df4e8feec852b14a4db6d4b8abebf077530f9e8f14&w=740', 'AVAILABLE');

INSERT INTO tbl_products (name, description, price, stock, image_url, status)
VALUES ('Smartwatch', 'Smartwatch with heart rate monitoring and GPS', 249000.00, 0, 'https://img.freepik.com/foto-gratis/dispositivo-digital-pantalla-smartwatch_53876-96809.jpg?t=st=1726077545~exp=1726081145~hmac=d5bd6edfaca1c525bf0a1490a435fcd822a9ddf982fce054e46571d5c398558f&w=740', 'UNAVAILABLE');

INSERT INTO tbl_products (name, description, price, stock, image_url, status)
VALUES ('Tablet Pro', 'High-end tablet for professionals and artists', 799000.00, 30, 'https://img.freepik.com/foto-gratis/maqueta-tableta-blanco-sobre-fondo-color_23-2148182469.jpg?t=st=1726077569~exp=1726081169~hmac=a23e8d1007a2dbbeda3b2bd53a70bdcb479878e3ef10b963e835ef7efe1df3b1&w=740', 'AVAILABLE');

INSERT INTO tbl_products (name, description, price, stock, image_url, status)
VALUES ('Noise Cancelling Headphones', 'Over-ear headphones with active noise cancelling', 199000.00, 0, 'https://img.freepik.com/foto-gratis/auriculares-vista-superior-sobre-fondo-rosa_23-2148681142.jpg?t=st=1726077629~exp=1726081229~hmac=a38d9cb066477ead10f5ba8a42edf60867e2cd292bce680f0a3069ef5cb53522&w=826', 'UNAVAILABLE');

INSERT INTO tbl_order_items (order_id, product_id, quantity, price_at_purchase)
VALUES (1, 3, 2, 398000.00);
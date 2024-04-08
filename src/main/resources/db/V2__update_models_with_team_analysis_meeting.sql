CREATE TABLE cart_items
(
    cart_item_id       BIGINT       NOT NULL,
    product_product_id VARCHAR(255) NOT NULL,
    count              BIGINT,
    customer_shop_id   BIGINT       NOT NULL,
    CONSTRAINT pk_cart_items PRIMARY KEY (cart_item_id)
);

ALTER TABLE products
    ADD categories VARCHAR(255);

ALTER TABLE products
    ADD max_size DOUBLE PRECISION;

ALTER TABLE shops
    ADD categories VARCHAR(255);

ALTER TABLE cart_items
    ADD CONSTRAINT FK_CART_ITEMS_ON_CUSTOMER_SHOPID FOREIGN KEY (customer_shop_id) REFERENCES shops (shop_id);

ALTER TABLE cart_items
    ADD CONSTRAINT FK_CART_ITEMS_ON_PRODUCT_PRODUCTID FOREIGN KEY (product_product_id) REFERENCES products (product_id);

ALTER TABLE categories_products
    DROP CONSTRAINT fk_catpro_on_category;

ALTER TABLE categories_products
    DROP CONSTRAINT fk_catpro_on_product;

ALTER TABLE categories_shops
    DROP CONSTRAINT fk_catsho_on_category;

ALTER TABLE categories_shops
    DROP CONSTRAINT fk_catsho_on_shop;

DROP TABLE categories CASCADE;

DROP TABLE categories_products CASCADE;

DROP TABLE categories_shops CASCADE;

ALTER TABLE products
    ALTER COLUMN cost TYPE DECIMAL USING (cost::DECIMAL);
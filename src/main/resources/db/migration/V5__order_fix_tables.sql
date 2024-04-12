ALTER TABLE order_parts
    ADD customer_id BIGINT;

ALTER TABLE order_parts
    ADD size BIGINT;

ALTER TABLE cart_items
    DROP CONSTRAINT fk_cart_items_on_customer_shopid;

ALTER TABLE cart_items
    DROP CONSTRAINT fk_cart_items_on_product_productid;

DROP TABLE cart_items CASCADE;

ALTER TABLE products
    DROP COLUMN max_size;

ALTER TABLE products
    DROP COLUMN min_size;

ALTER TABLE products
    ADD max_size BIGINT;

ALTER TABLE products
    ADD min_size BIGINT;

ALTER TABLE order_parts
    ALTER COLUMN order_order_id DROP NOT NULL;
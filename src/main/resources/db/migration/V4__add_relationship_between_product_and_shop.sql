ALTER TABLE products
    ADD shop_shop_id BIGINT;

ALTER TABLE products
    ALTER COLUMN shop_shop_id SET NOT NULL;

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_SHOP_SHOPID FOREIGN KEY (shop_shop_id) REFERENCES shops (shop_id);
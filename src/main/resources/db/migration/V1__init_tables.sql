CREATE TABLE categories
(
    category_id VARCHAR(255) NOT NULL,
    name        VARCHAR(255),
    CONSTRAINT pk_categories PRIMARY KEY (category_id)
);

CREATE TABLE categories_products
(
    category_id VARCHAR(255) NOT NULL,
    product_id  VARCHAR(255) NOT NULL
);

CREATE TABLE categories_shops
(
    category_id VARCHAR(255) NOT NULL,
    shop_id     BIGINT       NOT NULL
);

CREATE TABLE comments
(
    comment_id       BIGINT NOT NULL,
    customer_shop_id BIGINT NOT NULL,
    seller_shop_id   BIGINT NOT NULL,
    rate             DOUBLE PRECISION,
    text             VARCHAR(255),
    created_at       TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_comments PRIMARY KEY (comment_id)
);

CREATE TABLE order_parts
(
    order_part_id      BIGINT       NOT NULL,
    status             VARCHAR(255),
    order_order_id     BIGINT       NOT NULL,
    product_product_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_order_parts PRIMARY KEY (order_part_id)
);

CREATE TABLE orders
(
    order_id         BIGINT NOT NULL,
    address          VARCHAR(255),
    date             TIMESTAMP WITHOUT TIME ZONE,
    customer_shop_id BIGINT NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (order_id)
);

CREATE TABLE products
(
    product_id VARCHAR(255) NOT NULL,
    name       VARCHAR(255),
    units      VARCHAR(255),
    min_size   DOUBLE PRECISION,
    cost       DECIMAL,
    image_url  VARCHAR(255),
    CONSTRAINT pk_products PRIMARY KEY (product_id)
);

CREATE TABLE shops
(
    shop_id      BIGINT  NOT NULL,
    name         VARCHAR(255),
    email        VARCHAR(255),
    password     VARCHAR(255),
    phone_number VARCHAR(255),
    image_url    VARCHAR(255),
    is_active    BOOLEAN NOT NULL,
    CONSTRAINT pk_shops PRIMARY KEY (shop_id)
);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_CUSTOMER_SHOPID FOREIGN KEY (customer_shop_id) REFERENCES shops (shop_id);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_SELLER_SHOPID FOREIGN KEY (seller_shop_id) REFERENCES shops (shop_id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_CUSTOMER_SHOPID FOREIGN KEY (customer_shop_id) REFERENCES shops (shop_id);

ALTER TABLE order_parts
    ADD CONSTRAINT FK_ORDER_PARTS_ON_ORDER_ORDERID FOREIGN KEY (order_order_id) REFERENCES orders (order_id);

ALTER TABLE order_parts
    ADD CONSTRAINT FK_ORDER_PARTS_ON_PRODUCT_PRODUCTID FOREIGN KEY (product_product_id) REFERENCES products (product_id);

ALTER TABLE categories_products
    ADD CONSTRAINT fk_catpro_on_category FOREIGN KEY (category_id) REFERENCES categories (category_id);

ALTER TABLE categories_products
    ADD CONSTRAINT fk_catpro_on_product FOREIGN KEY (product_id) REFERENCES products (product_id);

ALTER TABLE categories_shops
    ADD CONSTRAINT fk_catsho_on_category FOREIGN KEY (category_id) REFERENCES categories (category_id);

ALTER TABLE categories_shops
    ADD CONSTRAINT fk_catsho_on_shop FOREIGN KEY (shop_id) REFERENCES shops (shop_id);
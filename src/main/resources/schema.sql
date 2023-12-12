drop table if exists items cascade;
drop table if exists cartItems cascade;
drop table if exists sales cascade;
/*drop table if exists discounts cascade;*/
/*drop table if exists basket cascade;
drop table if exists basketItems cascade;
drop table if exists sales cascade;
drop table if exists salesItems cascade;*/

/*CREATE SEQUENCE entity_id_seq START 1;*/

CREATE TABLE IF NOT EXISTS items
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    vendor_code BIGINT           NOT NULL,
    name       VARCHAR(128)     NOT NULL,
    price      double precision NOT NULL,
    amount     BIGINT           NOT NULL
);

CREATE TABLE IF NOT EXISTS cart_items
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    vendor_code BIGINT           NOT NULL,
    name       VARCHAR(128)     NOT NULL,
    price      double precision NOT NULL,
    amount     BIGINT           NOT NULL
);

CREATE TABLE IF NOT EXISTS sales
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sales_code BIGINT           NOT NULL,
    name       VARCHAR(128)     NOT NULL,
    price      double precision NOT NULL,
    amount     BIGINT           NOT NULL,
    discount double precision NOT NULL,
    discount_code BIGINT           NOT NULL,
    final_price double precision NOT NULL,
    total_price double precision NOT NULL,
    created_on TIMESTAMP DEFAULT NOW()
);


CREATE TABLE IF NOT EXISTS discounts
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    coefficient DOUBLE precision                          NOT NULL,
    item_vendor_code       BIGINT                        NOT null REFERENCES items (id),
    starting        timestamp                               not null,
    ending          timestamp                               not null
);

-- CREATE TABLE IF NOT EXISTS sales
-- (
--     id           BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
--     created_on   TIMESTAMP DEFAULT NOW(),
--     orderItem_id BIGINT,
--     total_amount DOUBLE PRECISION
-- );
--
-- CREATE TABLE IF NOT EXISTS salesItems
-- (
--     id             BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
--     item_id        BIGINT                                  NOT null REFERENCES items (id),
--     fixedItemPrice DOUBLE PRECISION                                  NOT NULL,
--     count          SMALLINT                                NOT NULL,
--     basket_id      BIGINT                                  NOT null REFERENCES sales (id)
-- );

/*CREATE TABLE IF NOT EXISTS basketItems
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    item_id    BIGINT                                  NOT null REFERENCES items (id),
    count      SMALLINT                                NOT NULL,
    created_on TIMESTAMP DEFAULT NOW(),
    basket_id  BIGINT                                  NOT null REFERENCES basket (id)
);

CREATE TABLE IF NOT EXISTS basket
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    created_on    TIMESTAMP DEFAULT NOW(),
    basketItem_id BIGINT
);

/*CREATE TABLE IF NOT EXISTS orders
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    created_on   TIMESTAMP DEFAULT NOW(),
    orderItem_id BIGINT,
    total_amount DOUBLE
);

CREATE TABLE IF NOT EXISTS orderItems
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    item_id        BIGINT                                  NOT null REFERENCES items (id),
    fixedItemPrice DOUBLE                                  NOT NULL,
    count          SMALLINT                                NOT NULL,
    basket_id      BIGINT                                  NOT null REFERENCES orders (id)
);*/


/*INSERT INTO ITEMS
values (1, 'Дрель кондитерская', 55.20),
       (2, 'Морская кладь', 160.15),
       (3, 'Быстронаживное дело', 10.00),
       (4, 'Итерационный луч', 99.99),
       (5, 'Роман Широков', 1234.56),
       (6, 'Киндер сюрприз', 200.00),
       (7, 'Буква Ю без перекладины (I0)', 300),
       (8, 'Гвоздь Маяковского', 1.15),
       (9, 'Астон Мартин DB6', 100000.00),
       (10, 'Аспирин для котов', 0.25)*/

/*INSERT INTO ITEMS
values ('Дрель кондитерская', 55.20),
('Морская кладь', 160.15),
('Быстронаживное дело', 10.00),
('Итерационный луч', 99.99),
('Роман Широков', 1234.56),
('Киндер сюрприз', 200.00),
('Буква Ю без перекладины (I0)', 300),
('Гвоздь Маяковского', 1.15),
('Астон Мартин DB6', 100000.00),
('Аспирин для котов', 0.25)*/
CREATE DATABASE wise;
\c wise


drop table if exists products cascade;
drop table if exists statistics cascade;
drop table if exists cart_products cascade;
drop table if exists sales cascade;
drop table if exists discounts cascade;
drop table if exists sale_products cascade;
drop table if exists users cascade;
drop table if exists roles cascade;
drop table if exists users_roles cascade;

CREATE TABLE IF NOT EXISTS products
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    vendor_code BIGINT       NOT NULL UNIQUE,
    name        VARCHAR(128) NOT NULL,
    price       INT          NOT NULL CHECK (price >= 0),
    amount      BIGINT       NOT NULL CHECK (amount >= 0)
);

CREATE TABLE IF NOT EXISTS cart_products
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    vendor_code BIGINT       NOT NULL,
    product_id     BIGINT       NOT NULL REFERENCES products (id) ON DELETE CASCADE,
    name        VARCHAR(128) NOT NULL,
    price       INT          NOT NULL,
    amount      BIGINT       NOT NULL
);

CREATE TABLE IF NOT EXISTS sale_products
(
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sale_code     BIGINT       NOT NULL,
    vendor_code   BIGINT       NOT NULL REFERENCES products (vendor_code) ON DELETE SET NULL,
    product_id       BIGINT,
    name          VARCHAR(128) NOT NULL,
    price         INT          NOT NULL,
    final_price   INT          NOT NULL,
    total_price   INT          NOT NULL,
    amount        BIGINT       NOT NULL,
    discount      INT          NOT NULL,
    discount_code BIGINT,
    created_on    TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sales
(
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sale_code     BIGINT NOT NULL,
    price         INT    NOT NULL,
    final_price   INT    NOT NULL,
    discount_sum  INT    NOT NULL,
    discount_code BIGINT,
    created_on    TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS discounts
(
    id               BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    discount_code    BIGINT,
    name             VARCHAR(128)                        NOT NULL,
    coefficient      DOUBLE precision                    NOT NULL,
    product_vendor_code BIGINT                              NOT NULL,
    starting         TIMESTAMP,
    ending           TIMESTAMP
);

CREATE TABLE IF NOT EXISTS statistics
(
    id                        BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    datetime_code             BIGINT                              NOT NULL,
    starting                  TIMESTAMP,
    ending                    TIMESTAMP,
    count_receipts            INT                                 NOT NULL,
    sum_without_discounts     INT                                 NOT NULL,
    avg_sum_without_discounts INT                                 NOT NULL,
    discount_sum              INT                                 NOT NULL,
    sum_with_discounts        INT                                 NOT NULL,
    avg_sum_with_discounts    INT                                 NOT NULL,
    increase_receipts         INT                                 NOT NULL,
    newest                    BOOLEAN
);

insert INTO products (id,  vendor_code, name, price, amount)
values (default, 101, 'Дрель кондитерская', 5520, 5);
insert INTO products (id, vendor_code, name, price, amount)
values (default, 102, 'Морская кладь', 16015, 0);
insert INTO products (id, vendor_code, name, price, amount)
values (default, 103, 'Быстронаживное дело', 1000, 100);
insert INTO products (id, vendor_code, name, price, amount)
values (default, 104, 'Итерационный луч', 9999, 1);
insert INTO products (id, vendor_code, name, price, amount)
values (default, 105, 'Роман Широков', 123456, 1);
insert INTO products (id, vendor_code, name, price, amount)
values (default, 106, 'Киндер сюрприз', 20000, 101);
insert INTO products (id, vendor_code, name, price, amount)
values (default, 107, 'Буква Ю без перекладины (I0)', 300, 500);
insert INTO products (id, vendor_code, name, price, amount)
values (default, 108, 'Гвоздь Маяковского', 115, 1000000);
insert INTO products (id, vendor_code, name, price, amount)
values (default, 109, 'Астон Мартин DB6', 10000000, 2);
insert INTO products (id, vendor_code, name, price, amount)
values (default, 110, 'Аспирин для котов', 25, 20);
insert INTO products (id, vendor_code, name, price, amount)
values (default, 111, 'Телеграмма Дмитрию Киселеву', 25, 1);
insert INTO products (id, vendor_code, name, price, amount)
values (default, 112, 'Сережки для жены', 10050000, 11);
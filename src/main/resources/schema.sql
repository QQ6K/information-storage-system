drop table if exists discounts cascade;
drop table if exists items cascade;
drop table if exists itemSales cascade;
drop table if exists Sales cascade;

CREATE TABLE IF NOT EXISTS items
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name  VARCHAR(128)                            NOT NULL,
    price double                                  NOT NULL
);

CREATE TABLE IF NOT EXISTS discounts
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    val     SMALLINT                                NOT NULL,
    item_id BIGINT                                  NOT null REFERENCES items (id)
);

CREATE TABLE IF NOT EXISTS sale
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS itemSales
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    sale_id          BIGINT                             NOT NULL REFERENCES sale (id),
    item_id     BIGINT                                  NOT NULL REFERENCES items (id),
    count       SMALLINT                                NOT NULL,
    discount_id SMALLINT                                NOT NULL REFERENCES discounts (id)
);
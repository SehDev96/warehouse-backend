CREATE TABLE IF NOT EXISTS app_user
(
    id uuid NOT NUll PRIMARY KEY,
    username varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    role varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS app_product
(
  id uuid NOT NULL PRIMARY KEY,
  name varchar(255) NOT NULL UNIQUE,
  sku varchar(255) NOT NULL,
  description varchar(255),
  quantity int,
  price double precision,
  date_created TIMESTAMP
);

CREATE TABLE IF NOT EXISTS app_warehouse
(
    id uuid NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    code varchar(255) NOT NULL UNIQUE,
    address varchar(255),
    state varchar(255) NOT NULL,
    city varchar(255) NOT NULL,
    description varchar(255),
    date_created TIMESTAMP
);

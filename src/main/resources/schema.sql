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

CREATE TABLE IF NOT EXISTS app_inbound_transaction
(
    id uuid NOT NULL PRIMARY KEY,
    product_id uuid NOT NULL,
    reference varchar(255) UNIQUE NOT NULL,
    quantity int,
    date_received varchar(255) NOT NULL,
    warehouse_id UUID NOT NULL,
    remarks varchar(255),
    date_created TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES app_product(id),
    FOREIGN KEY (warehouse_id) REFERENCES app_warehouse(id)
);

CREATE TABLE IF NOT EXISTS app_destination
(
    id uuid NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    date_created TIMESTAMP
);

CREATE TABLE IF NOT EXISTS app_outbound_transaction
(
    id uuid NOT NULL PRIMARY KEY,
    product_id uuid NOT NULL,
    reference varchar(255) UNIQUE NOT NULL,
    quantity int,
    date_shipped varchar(255) NOT NULL,
    destination_id UUID NOT NULL,
    remarks varchar(255),
    date_created TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES app_product(id),
    FOREIGN KEY (destination_id) REFERENCES app_destination(id)
);



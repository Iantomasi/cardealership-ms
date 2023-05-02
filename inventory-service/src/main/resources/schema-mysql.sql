USE `inventory-db`;

create table if not exists inventories (
                                           id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                           inventory_id VARCHAR(36),
    type VARCHAR(100)
    );

create table if not exists vehicle_options (
                                               vehicle_id INTEGER,
                                               name VARCHAR(100),
    description VARCHAR(200),
    cost DECIMAL(19,2)
    );

create table if not exists vehicles (
                                        id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                        vin VARCHAR(50) UNIQUE,
    inventory_id VARCHAR(36),
    status VARCHAR(50),
    usage_type VARCHAR(50),
    year INTEGER,
    manufacturer VARCHAR(100),
    make VARCHAR(50),
    model VARCHAR(75),
    body_class VARCHAR(100),
    msrp DECIMAL(19,2),
    cost DECIMAL(19,2),
    total_options_cost DECIMAL(19,2)
    );

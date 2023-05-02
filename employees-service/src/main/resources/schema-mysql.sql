USE `employees-db`;

create table if not exists employees (
                                         id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                         employee_id VARCHAR(36),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email_address VARCHAR(50),
    phone_number VARCHAR(50),
    salary DECIMAL(19,4),
    commission_rate DECIMAL(3,1),
    department VARCHAR(50),
    street_address VARCHAR (50),
    city VARCHAR (50),
    province VARCHAR (50),
    country VARCHAR (50),
    postal_code VARCHAR (9)
);


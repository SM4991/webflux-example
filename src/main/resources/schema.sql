CREATE TABLE IF NOT EXISTS employee (
    id SERIAL PRIMARY KEY,
    name varchar(50) NOT NULL,
    email varchar(255) NOT NULL
);
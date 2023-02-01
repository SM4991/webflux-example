CREATE TABLE IF NOT EXISTS employee (
    id SERIAL PRIMARY KEY,
    name varchar(50) NOT NULL,
    email varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS account (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(50),
    balance INT
);

CREATE TABLE IF NOT EXISTS money_deposit_event (
    id SERIAL PRIMARY KEY,
    account_number SERIAL,
    amount INT,
    foreign key (account_number) references account(id),
    check (amount > 99) -- business rule says there should be min $100 deposit
);

insert into account (user_name, balance) values
    ('Tara', 0),
    ('Daisy', 0),
    ('Fredericka', 0),
    ('Brita', 0);
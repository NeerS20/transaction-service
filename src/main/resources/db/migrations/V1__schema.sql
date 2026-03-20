CREATE TABLE accounts (
    account_id BIGSERIAL PRIMARY KEY,
    document_number VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE operation_types (
    id INT PRIMARY KEY,
    description VARCHAR(100),
    sign_type VARCHAR(10)
);

CREATE TABLE transactions (
    transaction_id BIGSERIAL PRIMARY KEY,
    account_id BIGINT,
    operation_type_id INT,
    amount NUMERIC(10,2),
    event_date TIMESTAMP,

    FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    FOREIGN KEY (operation_type_id) REFERENCES operation_types(id)
);
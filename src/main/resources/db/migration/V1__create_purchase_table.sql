CREATE TABLE IF NOT EXISTS purchase
(
    id               UUID PRIMARY KEY,
    description      VARCHAR(50)    NOT NULL,
    amount           NUMERIC(10, 2) NOT NULL,
    transaction_date DATE           NOT NULL,
    created_at       TIMESTAMP      NOT NULL
);
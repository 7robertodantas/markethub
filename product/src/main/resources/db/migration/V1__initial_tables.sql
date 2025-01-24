CREATE TABLE product (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    price BIGINT NOT NULL,
    quantity BIGINT NOT NULL
);

CREATE TABLE product (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    value NUMERIC(15, 2) NOT NULL,
    quantity INTEGER NOT NULL
);

CREATE TABLE checkout (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id uuid NOT NULL,
    status VARCHAR(50) NOT NULL,
    total BIGINT NOT NULL,
    product_ids jsonb NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
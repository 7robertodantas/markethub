CREATE TABLE wallet_balance (
    user_id UUID PRIMARY KEY,
    amount BIGINT NOT NULL
);

CREATE TABLE wallet_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    amount BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES wallet_balance(user_id)
);
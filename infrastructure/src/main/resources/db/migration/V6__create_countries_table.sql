CREATE TABLE IF NOT EXISTS ${db_schema}.countries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    code VARCHAR(10) NOT NULL UNIQUE,
    description VARCHAR(100),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    telephone_prefix VARCHAR(5),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

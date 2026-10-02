CREATE TABLE IF NOT EXISTS ${db_schema}.state_regions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    code VARCHAR(10) NOT NULL,
    description VARCHAR(100),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    country_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_state_regions_country_id FOREIGN KEY (country_id) REFERENCES ${db_schema}.countries (id),
    UNIQUE (country_id, code)
);

CREATE INDEX IF NOT EXISTS idx_state_regions_country_id ON ${db_schema}.state_regions (country_id);

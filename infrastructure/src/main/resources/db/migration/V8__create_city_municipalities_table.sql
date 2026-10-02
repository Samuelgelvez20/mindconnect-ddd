CREATE TABLE IF NOT EXISTS ${db_schema}.city_municipalities (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    code VARCHAR(10) NOT NULL,
    description VARCHAR(100),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    region_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_city_municipalities_region_id FOREIGN KEY (region_id) REFERENCES ${db_schema}.state_regions (id),
    UNIQUE (region_id, code)
);

CREATE INDEX IF NOT EXISTS idx_city_municipalities_region_id ON ${db_schema}.city_municipalities (region_id);

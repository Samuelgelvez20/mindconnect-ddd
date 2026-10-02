CREATE TABLE IF NOT EXISTS ${db_schema}.professionals (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    document_type_id UUID NOT NULL,
    document_number VARCHAR(30) NOT NULL,
    first_name VARCHAR(60) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    professional_type_id UUID NOT NULL,
    license_number VARCHAR(100) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    city_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_professionals_document_type_id FOREIGN KEY (document_type_id) REFERENCES ${db_schema}.document_types (id),
    CONSTRAINT fk_professionals_professional_type_id FOREIGN KEY (professional_type_id) REFERENCES ${db_schema}.professional_types (id),
    CONSTRAINT fk_professionals_city_id FOREIGN KEY (city_id) REFERENCES ${db_schema}.city_municipalities (id),
    UNIQUE (document_type_id, document_number)
);

CREATE INDEX IF NOT EXISTS idx_professionals_document_type_id ON ${db_schema}.professionals (document_type_id);
CREATE INDEX IF NOT EXISTS idx_professionals_professional_type_id ON ${db_schema}.professionals (professional_type_id);
CREATE INDEX IF NOT EXISTS idx_professionals_city_id ON ${db_schema}.professionals (city_id);

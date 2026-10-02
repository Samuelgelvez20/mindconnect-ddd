CREATE TABLE IF NOT EXISTS ${db_schema}.patients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    document_type_id UUID NOT NULL,
    document_number VARCHAR(30) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50),
    last_name VARCHAR(50) NOT NULL,
    second_last_name VARCHAR(50),
    birth_date DATE NOT NULL,
    biological_sex_id UUID NOT NULL,
    gender_identity_id UUID NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(30),
    address VARCHAR(250),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by UUID,
    city_id UUID NOT NULL,
    CONSTRAINT fk_patients_document_type_id FOREIGN KEY (document_type_id) REFERENCES ${db_schema}.document_types (id),
    CONSTRAINT fk_patients_biological_sex_id FOREIGN KEY (biological_sex_id) REFERENCES ${db_schema}.genders (id),
    CONSTRAINT fk_patients_gender_identity_id FOREIGN KEY (gender_identity_id) REFERENCES ${db_schema}.genders (id),
    CONSTRAINT fk_patients_created_by FOREIGN KEY (created_by) REFERENCES ${db_schema}.professionals (id),
    CONSTRAINT fk_patients_updated_by FOREIGN KEY (updated_by) REFERENCES ${db_schema}.professionals (id),
    CONSTRAINT fk_patients_city_id FOREIGN KEY (city_id) REFERENCES ${db_schema}.city_municipalities (id),
    UNIQUE (document_type_id, document_number)
);

CREATE INDEX IF NOT EXISTS idx_patients_document_type_id ON ${db_schema}.patients (document_type_id);
CREATE INDEX IF NOT EXISTS idx_patients_biological_sex_id ON ${db_schema}.patients (biological_sex_id);
CREATE INDEX IF NOT EXISTS idx_patients_gender_identity_id ON ${db_schema}.patients (gender_identity_id);
CREATE INDEX IF NOT EXISTS idx_patients_created_by ON ${db_schema}.patients (created_by);
CREATE INDEX IF NOT EXISTS idx_patients_updated_by ON ${db_schema}.patients (updated_by);
CREATE INDEX IF NOT EXISTS idx_patients_city_id ON ${db_schema}.patients (city_id);

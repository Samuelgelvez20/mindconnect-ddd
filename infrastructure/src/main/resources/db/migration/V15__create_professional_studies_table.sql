CREATE TABLE IF NOT EXISTS ${db_schema}.professional_studies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    study_id UUID NOT NULL,
    professional_id UUID NOT NULL,
    title VARCHAR(100) NOT NULL,
    university VARCHAR(100) NOT NULL,
    is_valid BOOLEAN NOT NULL DEFAULT FALSE,
    resolution_number VARCHAR(60),
    country_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_professional_studies_study_id FOREIGN KEY (study_id) REFERENCES ${db_schema}.studies (id),
    CONSTRAINT fk_professional_studies_professional_id FOREIGN KEY (professional_id) REFERENCES ${db_schema}.professionals (id),
    CONSTRAINT fk_professional_studies_country_id FOREIGN KEY (country_id) REFERENCES ${db_schema}.countries (id)
);

CREATE INDEX IF NOT EXISTS idx_professional_studies_study_id ON ${db_schema}.professional_studies (study_id);
CREATE INDEX IF NOT EXISTS idx_professional_studies_professional_id ON ${db_schema}.professional_studies (professional_id);
CREATE INDEX IF NOT EXISTS idx_professional_studies_country_id ON ${db_schema}.professional_studies (country_id);

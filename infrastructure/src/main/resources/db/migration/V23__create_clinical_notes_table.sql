CREATE TABLE IF NOT EXISTS ${db_schema}.clinical_notes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    encounter_id UUID NOT NULL,
    professional_id UUID NOT NULL,
    subjective TEXT,
    objective TEXT,
    assessment TEXT,
    plan TEXT,
    additional_notes TEXT,
    signed_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_clinical_notes_encounter_id FOREIGN KEY (encounter_id) REFERENCES ${db_schema}.encounters (id),
    CONSTRAINT fk_clinical_notes_professional_id FOREIGN KEY (professional_id) REFERENCES ${db_schema}.professionals (id)
);

CREATE INDEX IF NOT EXISTS idx_clinical_notes_encounter_id ON ${db_schema}.clinical_notes (encounter_id);
CREATE INDEX IF NOT EXISTS idx_clinical_notes_professional_id ON ${db_schema}.clinical_notes (professional_id);

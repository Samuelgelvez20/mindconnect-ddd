CREATE TABLE IF NOT EXISTS ${db_schema}.encounters (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    clinical_record_id UUID NOT NULL,
    professional_id UUID NOT NULL,
    encounter_type_id UUID NOT NULL,
    started_at TIMESTAMPTZ NOT NULL,
    ended_at TIMESTAMPTZ,
    reason_for_visit TEXT,
    current_condition TEXT,
    modality_id UUID NOT NULL,
    status_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by UUID,
    CONSTRAINT fk_encounters_clinical_record_id FOREIGN KEY (clinical_record_id) REFERENCES ${db_schema}.clinical_records (id),
    CONSTRAINT fk_encounters_professional_id FOREIGN KEY (professional_id) REFERENCES ${db_schema}.professionals (id),
    CONSTRAINT fk_encounters_encounter_type_id FOREIGN KEY (encounter_type_id) REFERENCES ${db_schema}.encounter_types (id),
    CONSTRAINT fk_encounters_modality_id FOREIGN KEY (modality_id) REFERENCES ${db_schema}.encounter_modalities (id),
    CONSTRAINT fk_encounters_status_id FOREIGN KEY (status_id) REFERENCES ${db_schema}.encounter_statuses (id),
    CONSTRAINT fk_encounters_created_by FOREIGN KEY (created_by) REFERENCES ${db_schema}.professionals (id),
    CONSTRAINT fk_encounters_updated_by FOREIGN KEY (updated_by) REFERENCES ${db_schema}.professionals (id)
);

CREATE INDEX IF NOT EXISTS idx_encounters_clinical_record_id ON ${db_schema}.encounters (clinical_record_id);
CREATE INDEX IF NOT EXISTS idx_encounters_professional_id ON ${db_schema}.encounters (professional_id);
CREATE INDEX IF NOT EXISTS idx_encounters_encounter_type_id ON ${db_schema}.encounters (encounter_type_id);
CREATE INDEX IF NOT EXISTS idx_encounters_modality_id ON ${db_schema}.encounters (modality_id);
CREATE INDEX IF NOT EXISTS idx_encounters_status_id ON ${db_schema}.encounters (status_id);
CREATE INDEX IF NOT EXISTS idx_encounters_created_by ON ${db_schema}.encounters (created_by);
CREATE INDEX IF NOT EXISTS idx_encounters_updated_by ON ${db_schema}.encounters (updated_by);

CREATE TABLE IF NOT EXISTS ${db_schema}.clinical_records (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    patient_id UUID NOT NULL,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    record_number VARCHAR(50) NOT NULL,
    opened_at TIMESTAMPTZ,
    closed_at TIMESTAMPTZ,
    status_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID NOT NULL,
    CONSTRAINT fk_clinical_records_patient_id FOREIGN KEY (patient_id) REFERENCES ${db_schema}.patients (id),
    CONSTRAINT fk_clinical_records_status_id FOREIGN KEY (status_id) REFERENCES ${db_schema}.clinical_record_statuses (id),
    CONSTRAINT fk_clinical_records_created_by FOREIGN KEY (created_by) REFERENCES ${db_schema}.professionals (id)
);

CREATE INDEX IF NOT EXISTS idx_clinical_records_patient_id ON ${db_schema}.clinical_records (patient_id);
CREATE INDEX IF NOT EXISTS idx_clinical_records_status_id ON ${db_schema}.clinical_records (status_id);
CREATE INDEX IF NOT EXISTS idx_clinical_records_created_by ON ${db_schema}.clinical_records (created_by);

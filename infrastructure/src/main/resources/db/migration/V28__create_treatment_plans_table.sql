CREATE TABLE IF NOT EXISTS ${db_schema}.treatment_plans (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    encounter_id UUID NOT NULL,
    professional_id UUID NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE,
    treatment_status_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_treatment_plans_encounter_id FOREIGN KEY (encounter_id) REFERENCES ${db_schema}.encounters (id),
    CONSTRAINT fk_treatment_plans_professional_id FOREIGN KEY (professional_id) REFERENCES ${db_schema}.professionals (id),
    CONSTRAINT fk_treatment_plans_treatment_status_id FOREIGN KEY (treatment_status_id) REFERENCES ${db_schema}.treatment_statuses (id)
);

CREATE INDEX IF NOT EXISTS idx_treatment_plans_encounter_id ON ${db_schema}.treatment_plans (encounter_id);
CREATE INDEX IF NOT EXISTS idx_treatment_plans_professional_id ON ${db_schema}.treatment_plans (professional_id);
CREATE INDEX IF NOT EXISTS idx_treatment_plans_treatment_status_id ON ${db_schema}.treatment_plans (treatment_status_id);

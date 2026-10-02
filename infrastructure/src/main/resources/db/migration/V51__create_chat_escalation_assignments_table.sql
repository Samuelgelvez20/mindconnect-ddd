CREATE TABLE IF NOT EXISTS ${db_schema}.chat_escalation_assignments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    escalation_id UUID NOT NULL,
    professional_id UUID NOT NULL,
    assigned_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_chat_escalation_assignments_escalation_id FOREIGN KEY (escalation_id) REFERENCES ${db_schema}.chat_escalations (id),
    CONSTRAINT fk_chat_escalation_assignments_professional_id FOREIGN KEY (professional_id) REFERENCES ${db_schema}.professionals (id),
    UNIQUE (escalation_id, professional_id)
);

CREATE INDEX IF NOT EXISTS idx_chat_escalation_assignments_escalation_id ON ${db_schema}.chat_escalation_assignments (escalation_id);
CREATE INDEX IF NOT EXISTS idx_chat_escalation_assignments_professional_id ON ${db_schema}.chat_escalation_assignments (professional_id);

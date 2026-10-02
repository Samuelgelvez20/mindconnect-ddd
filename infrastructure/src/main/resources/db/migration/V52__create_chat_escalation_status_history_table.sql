CREATE TABLE IF NOT EXISTS ${db_schema}.chat_escalation_status_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    escalation_id UUID NOT NULL,
    escalation_status_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    changed_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_chat_escalation_status_history_escalation_id FOREIGN KEY (escalation_id) REFERENCES ${db_schema}.chat_escalations (id),
    CONSTRAINT fk_chat_escalation_status_history_escalation_status_id FOREIGN KEY (escalation_status_id) REFERENCES ${db_schema}.chat_escalation_statuses (id)
);

CREATE INDEX IF NOT EXISTS idx_chat_escalation_status_history_escalation_id ON ${db_schema}.chat_escalation_status_history (escalation_id);
CREATE INDEX IF NOT EXISTS idx_chat_escalation_status_history_escalation_status_id ON ${db_schema}.chat_escalation_status_history (escalation_status_id);

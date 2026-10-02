CREATE TABLE IF NOT EXISTS ${db_schema}.chat_escalations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    conversation_id UUID NOT NULL,
    status_id UUID NOT NULL,
    from_ai BOOLEAN NOT NULL DEFAULT FALSE,
    reason TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_chat_escalations_conversation_id FOREIGN KEY (conversation_id) REFERENCES ${db_schema}.chat_conversations (id),
    CONSTRAINT fk_chat_escalations_status_id FOREIGN KEY (status_id) REFERENCES ${db_schema}.chat_escalation_statuses (id)
);

CREATE INDEX IF NOT EXISTS idx_chat_escalations_conversation_id ON ${db_schema}.chat_escalations (conversation_id);
CREATE INDEX IF NOT EXISTS idx_chat_escalations_status_id ON ${db_schema}.chat_escalations (status_id);

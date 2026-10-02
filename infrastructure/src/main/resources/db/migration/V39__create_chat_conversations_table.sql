CREATE TABLE IF NOT EXISTS ${db_schema}.chat_conversations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    conversation_status_id UUID NOT NULL,
    priority_id UUID NOT NULL,
    last_message_at TIMESTAMPTZ,
    closed BOOLEAN NOT NULL DEFAULT FALSE,
    closed_at TIMESTAMPTZ,
    closed_by UUID,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_chat_conversations_conversation_status_id FOREIGN KEY (conversation_status_id) REFERENCES ${db_schema}.chat_conversation_statuses (id),
    CONSTRAINT fk_chat_conversations_priority_id FOREIGN KEY (priority_id) REFERENCES ${db_schema}.priorities (id)
);

CREATE INDEX IF NOT EXISTS idx_chat_conversations_conversation_status_id ON ${db_schema}.chat_conversations (conversation_status_id);
CREATE INDEX IF NOT EXISTS idx_chat_conversations_priority_id ON ${db_schema}.chat_conversations (priority_id);

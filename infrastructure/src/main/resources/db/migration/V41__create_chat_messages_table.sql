CREATE TABLE IF NOT EXISTS ${db_schema}.chat_messages (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    conversation_id UUID NOT NULL,
    message_type_id UUID NOT NULL,
    participant_id UUID NOT NULL,
    content JSONB NOT NULL,
    metadata JSONB,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_chat_messages_conversation_id FOREIGN KEY (conversation_id) REFERENCES ${db_schema}.chat_conversations (id),
    CONSTRAINT fk_chat_messages_message_type_id FOREIGN KEY (message_type_id) REFERENCES ${db_schema}.message_types (id),
    CONSTRAINT fk_chat_messages_participant_id FOREIGN KEY (participant_id) REFERENCES ${db_schema}.chat_participants (id)
);

CREATE INDEX IF NOT EXISTS idx_chat_messages_conversation_id ON ${db_schema}.chat_messages (conversation_id);
CREATE INDEX IF NOT EXISTS idx_chat_messages_message_type_id ON ${db_schema}.chat_messages (message_type_id);
CREATE INDEX IF NOT EXISTS idx_chat_messages_participant_id ON ${db_schema}.chat_messages (participant_id);

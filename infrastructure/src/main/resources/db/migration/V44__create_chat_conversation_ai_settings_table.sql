CREATE TABLE IF NOT EXISTS ${db_schema}.chat_conversation_ai_settings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    conversation_id UUID NOT NULL UNIQUE,
    ai_enabled BOOLEAN NOT NULL DEFAULT TRUE,
    default_model_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_chat_conversation_ai_settings_conversation_id FOREIGN KEY (conversation_id) REFERENCES ${db_schema}.chat_conversations (id),
    CONSTRAINT fk_chat_conversation_ai_settings_default_model_id FOREIGN KEY (default_model_id) REFERENCES ${db_schema}.ai_models (id)
);

CREATE INDEX IF NOT EXISTS idx_chat_conversation_ai_settings_default_model_id ON ${db_schema}.chat_conversation_ai_settings (default_model_id);

CREATE TABLE IF NOT EXISTS ${db_schema}.chat_ai_runs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    conversation_id UUID NOT NULL,
    message_id UUID NOT NULL,
    model_id UUID NOT NULL,
    ai_run_status_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_chat_ai_runs_conversation_id FOREIGN KEY (conversation_id) REFERENCES ${db_schema}.chat_conversations (id),
    CONSTRAINT fk_chat_ai_runs_message_id FOREIGN KEY (message_id) REFERENCES ${db_schema}.chat_messages (id),
    CONSTRAINT fk_chat_ai_runs_model_id FOREIGN KEY (model_id) REFERENCES ${db_schema}.ai_models (id),
    CONSTRAINT fk_chat_ai_runs_ai_run_status_id FOREIGN KEY (ai_run_status_id) REFERENCES ${db_schema}.chat_ai_run_statuses (id)
);

CREATE INDEX IF NOT EXISTS idx_chat_ai_runs_conversation_id ON ${db_schema}.chat_ai_runs (conversation_id);
CREATE INDEX IF NOT EXISTS idx_chat_ai_runs_message_id ON ${db_schema}.chat_ai_runs (message_id);
CREATE INDEX IF NOT EXISTS idx_chat_ai_runs_model_id ON ${db_schema}.chat_ai_runs (model_id);
CREATE INDEX IF NOT EXISTS idx_chat_ai_runs_ai_run_status_id ON ${db_schema}.chat_ai_runs (ai_run_status_id);

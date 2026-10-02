CREATE TABLE IF NOT EXISTS ${db_schema}.chat_ai_run_errors (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    ai_run_id UUID NOT NULL,
    error_message TEXT NOT NULL,
    error_code VARCHAR(80),
    provider_error_id VARCHAR(120),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_chat_ai_run_errors_ai_run_id FOREIGN KEY (ai_run_id) REFERENCES ${db_schema}.chat_ai_runs (id)
);

CREATE INDEX IF NOT EXISTS idx_chat_ai_run_errors_ai_run_id ON ${db_schema}.chat_ai_run_errors (ai_run_id);

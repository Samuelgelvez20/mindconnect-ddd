CREATE TABLE IF NOT EXISTS ${db_schema}.chat_ai_run_metrics (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    ai_run_id UUID NOT NULL UNIQUE,
    prompt_tokens INTEGER NOT NULL DEFAULT 0,
    completion_tokens INTEGER NOT NULL DEFAULT 0,
    total_tokens INTEGER NOT NULL DEFAULT 0,
    cost DECIMAL(10,6) NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_chat_ai_run_metrics_ai_run_id FOREIGN KEY (ai_run_id) REFERENCES ${db_schema}.chat_ai_runs (id)
);

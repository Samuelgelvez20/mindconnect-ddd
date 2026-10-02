CREATE TABLE IF NOT EXISTS ${db_schema}.ai_models (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    ai_provider_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    model_key VARCHAR(120) NOT NULL UNIQUE,
    input_token_price DECIMAL(12,8) NOT NULL,
    output_token_price DECIMAL(12,8) NOT NULL,
    max_tokens INTEGER NOT NULL,
    context_window INTEGER NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_ai_models_ai_provider_id FOREIGN KEY (ai_provider_id) REFERENCES ${db_schema}.ai_providers (id)
);

CREATE INDEX IF NOT EXISTS idx_ai_models_ai_provider_id ON ${db_schema}.ai_models (ai_provider_id);

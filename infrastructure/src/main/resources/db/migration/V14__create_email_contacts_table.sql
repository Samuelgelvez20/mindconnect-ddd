CREATE TABLE IF NOT EXISTS ${db_schema}.email_contacts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    contact_id UUID NOT NULL,
    email VARCHAR(150) NOT NULL,
    notes TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_email_contacts_contact_id FOREIGN KEY (contact_id) REFERENCES ${db_schema}.contacts (id)
);

CREATE INDEX IF NOT EXISTS idx_email_contacts_contact_id ON ${db_schema}.email_contacts (contact_id);

CREATE TABLE IF NOT EXISTS ${db_schema}.phone_contacts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    contact_id UUID NOT NULL,
    phone VARCHAR(30) NOT NULL,
    notes TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_phone_contacts_contact_id FOREIGN KEY (contact_id) REFERENCES ${db_schema}.contacts (id)
);

CREATE INDEX IF NOT EXISTS idx_phone_contacts_contact_id ON ${db_schema}.phone_contacts (contact_id);

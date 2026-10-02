CREATE TABLE IF NOT EXISTS ${db_schema}.chat_participants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    conversation_id UUID NOT NULL,
    participant_type_id UUID NOT NULL,
    patient_id UUID,
    professional_id UUID,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_chat_participants_conversation_id FOREIGN KEY (conversation_id) REFERENCES ${db_schema}.chat_conversations (id),
    CONSTRAINT fk_chat_participants_participant_type_id FOREIGN KEY (participant_type_id) REFERENCES ${db_schema}.sender_types (id),
    CONSTRAINT fk_chat_participants_patient_id FOREIGN KEY (patient_id) REFERENCES ${db_schema}.patients (id),
    CONSTRAINT fk_chat_participants_professional_id FOREIGN KEY (professional_id) REFERENCES ${db_schema}.professionals (id),
    CONSTRAINT ck_chat_participants_one_person CHECK (patient_id IS NULL OR professional_id IS NULL)
);

CREATE INDEX IF NOT EXISTS idx_chat_participants_conversation_id ON ${db_schema}.chat_participants (conversation_id);
CREATE INDEX IF NOT EXISTS idx_chat_participants_participant_type_id ON ${db_schema}.chat_participants (participant_type_id);
CREATE INDEX IF NOT EXISTS idx_chat_participants_patient_id ON ${db_schema}.chat_participants (patient_id);
CREATE INDEX IF NOT EXISTS idx_chat_participants_professional_id ON ${db_schema}.chat_participants (professional_id);

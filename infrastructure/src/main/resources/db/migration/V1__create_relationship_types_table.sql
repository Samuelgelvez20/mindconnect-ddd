CREATE TABLE IF NOT EXISTS ${db_schema}.relationship_types (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    description VARCHAR(50) NOT NULL UNIQUE
);

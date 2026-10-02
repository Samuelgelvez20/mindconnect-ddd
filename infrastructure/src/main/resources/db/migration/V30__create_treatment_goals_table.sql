CREATE TABLE IF NOT EXISTS ${db_schema}.treatment_goals (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    treatment_plan_id UUID NOT NULL,
    description TEXT NOT NULL,
    target_date DATE,
    completed_at TIMESTAMPTZ,
    notes TEXT,
    treatment_goal_status_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_treatment_goals_treatment_plan_id FOREIGN KEY (treatment_plan_id) REFERENCES ${db_schema}.treatment_plans (id),
    CONSTRAINT fk_treatment_goals_treatment_goal_status_id FOREIGN KEY (treatment_goal_status_id) REFERENCES ${db_schema}.treatment_goal_statuses (id)
);

CREATE INDEX IF NOT EXISTS idx_treatment_goals_treatment_plan_id ON ${db_schema}.treatment_goals (treatment_plan_id);
CREATE INDEX IF NOT EXISTS idx_treatment_goals_treatment_goal_status_id ON ${db_schema}.treatment_goals (treatment_goal_status_id);

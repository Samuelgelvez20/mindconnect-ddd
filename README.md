# MindConnect – Hexagonal + DDD modular (Spring Boot 4 / Java 25)

Ejercicio de práctica: estructura multimódulo Maven con **Flyway + JPA + PostgreSQL**.
Por ahora solo se crean las **52 tablas** del diagrama mediante migraciones.

## Módulos
- `domain`         → núcleo (sin frameworks)
- `application`    → casos de uso y puertos (depende de `domain`)
- `infrastructure` → Spring Boot, adaptadores, Flyway, JPA (depende de `application`)

## Cómo correrlo
1. Crear la base de datos vacía (solo la BD, el esquema lo crea Flyway):
   ```sql
   CREATE DATABASE mindconnectdb;
   ```
2. Credenciales en `infrastructure/src/main/resources/application-dev.yml` (por defecto `postgres` / `123456`).
3. Desde la raíz: `mvn clean install` y ejecutar `MindConnectApplication` (VS Code: F5 con el launch incluido).
4. En DBeaver: conexión a `mindconnectdb` → esquema **`mindconnect_schema`** → Tables (52) + `flyway_schema_history_mindconnect`.

## Decisiones de normalización (diagrama → proyecto)
| Diagrama | Proyecto |
|---|---|
| `encounter_statusses` | `encounter_statuses` |
| `treatment_statusses` | `treatment_statuses` |
| `treatment_goal_statusses` | `treatment_goal_statuses` |
| `conversations_statuses` | `chat_conversation_statuses` |
| `ai_runs_statuses` | `chat_ai_run_statuses` |
| `escalations_statuses` | `chat_escalation_statuses` |
| `provider_models_ai` (`razon_social`, `sitio_web`, `isActive`) | `ai_providers` (`legal_name`, `website`, `is_active`) |
| columnas `name_country`, `code_country`, `name_region`, `code_citi`, `name_type`, `name_status`, `name_priority`, `name_model` | `name` / `code` |
| `ai_models.provider_model_id` | `ai_models.ai_provider_id` |
| `professionals.professional_type` | `professionals.professional_type_id` |
| `phone_contacts.Column1/Column2` | `created_at` / `updated_at` |

## Convenciones aplicadas
- PK `UUID DEFAULT gen_random_uuid()`; fechas en `TIMESTAMPTZ`.
- FK e índices por cada FK; `created_by`/`updated_by`/`assessed_by`/`recorded_by` apuntan a `professionals`.
- Campos de texto libre, fechas de cierre/fin y auditoría `*_by` opcionales son nullables; el resto `NOT NULL`.
- Únicos añadidos: `(document_type_id, document_number)` en patients y professionals, `license_number`, `patient_id+contact_id`, etc.
- `chat_participants`: CHECK para que no tenga paciente y profesional a la vez.
- `chat_conversations.closed_by` queda como UUID sin FK (el diagrama no indica a qué tabla apunta).
- Catálogos sin datos semilla (pendiente).

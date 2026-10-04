# AGENTS.md – MindConnect DDD (Hexagonal + DDD + Bounded Contexts)

Este archivo lo lee opencode en cada sesión. Es la fuente de reglas del proyecto.
Si una instrucción del chat contradice este archivo, **detente y pregunta**.

## 1. Contexto del proyecto
- Ejercicio académico. Maven multimódulo: `domain` → `application` → `infrastructure`.
- Java 25, Spring Boot 4.0.2, JPA/Hibernate, Flyway, PostgreSQL 16.
- Paquete base: `com.mindconnect`. Clase main: `com.mindconnect.infrastructure.MindConnectApplication`.
- Este repo es un **clon del repo de migraciones** (`mindconnect`) y sobre él se implementa DDD. El repo de migraciones original no se toca.
- BD: contenedor Docker `mindconnect-postgres`, `localhost:5434`, base `mindconnectdb`, esquema `mindconnect_schema`, usuario `postgres` / `123456`. App en puerto `8082`.
- **Las migraciones `V1`…`V52` (`infrastructure/src/main/resources/db/migration`) son la fuente de verdad del esquema.**
  - NUNCA modificar una migración existente. Si hace falta un cambio de esquema: nueva migración `V53+` y avisar antes.
  - `ddl-auto` NO puede ser `create`, `create-drop` ni `update`. Debe ser `none` o `validate`.
- Referencia de estilo: `_reference/demo-ddd/` (repo de ejemplo del profesor; solo lectura, ignorado por git, no es módulo Maven). El slice `country` es la plantilla, con las correcciones de la sección 7.

## 2. Modelo: 9 bounded contexts, 52 aggregates
- Hay **9 bounded contexts** y **52 aggregates** (uno por tabla). Cada aggregate lleva su slice hexagonal completo, igual que `country` en el demo (≈ 23 archivos de código por tabla, ≈ 1.200 en total).
- Los slices viven en `<ctx>/<agg>/…` (un nivel más que el demo) para que, si el profesor exige "un contexto por tabla", se pueda convertir moviendo ese nivel de carpeta sin reescribir código. **No fusionar ni aplanar esta estructura.**
- `<ctx>` = nombre del contexto (tabla de la sección 4). `<agg>` = nombre del aggregate en minúsculas sin separadores (`country`, `citymunicipality`).

## 3. Estructura de paquetes
Reemplaza la estructura vieja `domain/model/aggregates…` (los `.gitkeep` antiguos se eliminan en la Fase 1).

```
domain/src/main/java/com/mindconnect/domain/
  common/{model/AggregateRoot, event/DomainEvent, exception/DomainException}
  <ctx>/<agg>/
    model/aggregate/<X>.java
    model/valueobject/<X>Id.java
    port/repository/<X>Repository.java
    event/<X>RegisteredEvent, <X>UpdatedEvent, <X>DeletedEvent
    exception/            (excepciones propias de ese aggregate, si las hay)

application/src/main/java/com/mindconnect/application/
  common/exception/ApplicationException
  <ctx>/<agg>/
    command/Register<X>Command, Update<X>Command
    dto/<X>Response
    exception/<X>NotFoundApplicationException, <X>AlreadyExistsApplicationException (si hay únicos)
    usecase/{Register,GetById,List,Update,Delete}<X>UseCase

infrastructure/src/main/java/com/mindconnect/infrastructure/
  common/adapters/in/rest/GlobalExceptionHandler.java
  <ctx>/<agg>/
    adapters/in/rest/controllers/<X>Controller
    adapters/in/rest/dtos/Create<X>Request, Update<X>Request
    adapters/out/persistence/entity/<X>JpaEntity
    adapters/out/persistence/mappers/<X>PersistenceMapper
    adapters/out/persistence/repositories/<X>JpaRepository, <X>RepositoryAdapter
    config/<X>BeansConfig
```

Tests (espejo del main): `domain/src/test/...`, `application/src/test/...`.

## 4. Mapa de contextos y tablas
El orden de la tabla es el **orden de implementación**: contextos en orden de dependencias y, dentro de cada contexto, tablas en orden de migración (V). Así cada aggregate solo depende de lo ya implementado.

| # | V | Tabla | Aggregate (paquete) | Endpoint |
|---|---|---|---|---|
| | | **Contexto `referencedata`** | | |
| 1 | V2 | genders | Gender (`gender`) | `/api/genders` |
| 2 | V5 | document_types | DocumentType (`documenttype`) | `/api/document-types` |
| 3 | V6 | countries | Country (`country`) | `/api/countries` |
| 4 | V7 | state_regions | StateRegion (`stateregion`) | `/api/state-regions` |
| 5 | V8 | city_municipalities | CityMunicipality (`citymunicipality`) | `/api/city-municipalities` |
| | | **Contexto `professional`** | | |
| 6 | V3 | professional_types | ProfessionalType (`professionaltype`) | `/api/professional-types` |
| 7 | V4 | studies | Study (`study`) | `/api/studies` |
| 8 | V9 | professionals | Professional (`professional`) | `/api/professionals` |
| 9 | V15 | professional_studies | ProfessionalStudy (`professionalstudy`) | `/api/professional-studies` |
| | | **Contexto `patient`** | | |
| 10 | V10 | patients | Patient (`patient`) | `/api/patients` |
| 11 | V16 | patient_allergies | PatientAllergy (`patientallergy`) | `/api/patient-allergies` |
| | | **Contexto `contact`** | | |
| 12 | V1 | relationship_types | RelationshipType (`relationshiptype`) | `/api/relationship-types` |
| 13 | V11 | contacts | Contact (`contact`) | `/api/contacts` |
| 14 | V12 | patient_contacts | PatientContact (`patientcontact`) | `/api/patient-contacts` |
| 15 | V13 | phone_contacts | PhoneContact (`phonecontact`) | `/api/phone-contacts` |
| 16 | V14 | email_contacts | EmailContact (`emailcontact`) | `/api/email-contacts` |
| | | **Contexto `clinicalrecord`** | | |
| 17 | V17 | clinical_record_statuses | ClinicalRecordStatus (`clinicalrecordstatus`) | `/api/clinical-record-statuses` |
| 18 | V18 | clinical_records | ClinicalRecord (`clinicalrecord`) | `/api/clinical-records` |
| 19 | V19 | encounter_types | EncounterType (`encountertype`) | `/api/encounter-types` |
| 20 | V20 | encounter_modalities | EncounterModality (`encountermodality`) | `/api/encounter-modalities` |
| 21 | V21 | encounter_statuses | EncounterStatus (`encounterstatus`) | `/api/encounter-statuses` |
| 22 | V22 | encounters | Encounter (`encounter`) | `/api/encounters` |
| 23 | V23 | clinical_notes | ClinicalNote (`clinicalnote`) | `/api/clinical-notes` |
| 24 | V24 | mental_status_exams | MentalStatusExam (`mentalstatusexam`) | `/api/mental-status-exams` |
| 25 | V25 | risk_levels | RiskLevel (`risklevel`) | `/api/risk-levels` |
| 26 | V26 | risk_assessments | RiskAssessment (`riskassessment`) | `/api/risk-assessments` |
| | | **Contexto `treatment`** | | |
| 27 | V27 | treatment_statuses | TreatmentStatus (`treatmentstatus`) | `/api/treatment-statuses` |
| 28 | V28 | treatment_plans | TreatmentPlan (`treatmentplan`) | `/api/treatment-plans` |
| 29 | V29 | treatment_goal_statuses | TreatmentGoalStatus (`treatmentgoalstatus`) | `/api/treatment-goal-statuses` |
| 30 | V30 | treatment_goals | TreatmentGoal (`treatmentgoal`) | `/api/treatment-goals` |
| | | **Contexto `clinicalcatalog`** | | |
| 31 | V31 | medication_routes | MedicationRoute (`medicationroute`) | `/api/medication-routes` |
| 32 | V32 | assessment_types | AssessmentType (`assessmenttype`) | `/api/assessment-types` |
| 33 | V33 | consent_types | ConsentType (`consenttype`) | `/api/consent-types` |
| 34 | V34 | diagnostic_systems | DiagnosticSystem (`diagnosticsystem`) | `/api/diagnostic-systems` |
| | | **Contexto `chat`** | | |
| 35 | V35 | sender_types | SenderType (`sendertype`) | `/api/sender-types` |
| 36 | V36 | message_types | MessageType (`messagetype`) | `/api/message-types` |
| 37 | V37 | priorities | Priority (`priority`) | `/api/priorities` |
| 38 | V38 | chat_conversation_statuses | ChatConversationStatus (`chatconversationstatus`) | `/api/chat-conversation-statuses` |
| 39 | V39 | chat_conversations | ChatConversation (`chatconversation`) | `/api/chat-conversations` |
| 40 | V40 | chat_participants | ChatParticipant (`chatparticipant`) | `/api/chat-participants` |
| 41 | V41 | chat_messages | ChatMessage (`chatmessage`) | `/api/chat-messages` |
| 42 | V49 | chat_escalation_statuses | ChatEscalationStatus (`chatescalationstatus`) | `/api/chat-escalation-statuses` |
| 43 | V50 | chat_escalations | ChatEscalation (`chatescalation`) | `/api/chat-escalations` |
| 44 | V51 | chat_escalation_assignments | ChatEscalationAssignment (`chatescalationassignment`) | `/api/chat-escalation-assignments` |
| 45 | V52 | chat_escalation_status_history | ChatEscalationStatusHistory (`chatescalationstatushistory`) | `/api/chat-escalation-status-history` |
| | | **Contexto `ai`** | | |
| 46 | V42 | ai_providers | AiProvider (`aiprovider`) | `/api/ai-providers` |
| 47 | V43 | ai_models | AiModel (`aimodel`) | `/api/ai-models` |
| 48 | V44 | chat_conversation_ai_settings | ChatConversationAiSettings (`chatconversationaisettings`) | `/api/chat-conversation-ai-settings` |
| 49 | V45 | chat_ai_run_statuses | ChatAiRunStatus (`chatairunstatus`) | `/api/chat-ai-run-statuses` |
| 50 | V46 | chat_ai_runs | ChatAiRun (`chatairun`) | `/api/chat-ai-runs` |
| 51 | V47 | chat_ai_run_metrics | ChatAiRunMetrics (`chatairunmetrics`) | `/api/chat-ai-run-metrics` |
| 52 | V48 | chat_ai_run_errors | ChatAiRunError (`chatairunerror`) | `/api/chat-ai-run-errors` |

Dependencias permitidas entre contextos (flecha = puede usar el Id VO de): professional→referencedata; patient→referencedata, professional; contact→referencedata, professional, patient; clinicalrecord→patient, professional; treatment→clinicalrecord, professional; chat→patient, professional; ai→chat. **Nunca al revés ni en ciclo.** `clinicalcatalog` no depende de nadie.

## 5. Reglas de diseño (obligatorias)
1. **Un aggregate por tabla**, cada uno con su repositorio, casos de uso y controller en `<ctx>/<agg>/`.
2. **Entre aggregates solo se referencia por Id VO** (`PatientId`, etc.), también dentro del mismo contexto. Se puede importar el `XId` de otra tabla referenciada por FK y permitida por la sección 4. Jamás importar el aggregate, entity JPA, repositorio ni casos de uso de otro aggregate.
3. **Prohibido `@ManyToOne`/`@OneToMany`** en entidades JPA. Las FK son columnas `UUID` simples; la integridad la garantiza la BD.
4. **domain**: solo JDK (+ JUnit en test). Sin Spring, sin JPA, sin Jackson.
5. **application**: depende solo de `domain`. Sin Spring ni JPA. Casos de uso = clases planas con constructor.
6. **infrastructure**: único lugar con Spring/JPA. Los casos de uso, mappers y adapters se registran como `@Bean` en `<X>BeansConfig` (como en el demo), no con `@Service`/`@Component`.
7. El aggregate se crea con `register(...)`, se reconstruye con `restore(...)`, se modifica con métodos de negocio (`update(...)`, `delete()`), valida invariantes con `Objects.requireNonNull` y/o `DomainException`. Constructor privado. Sin setters.
8. `XId` = `record XId(UUID value)` con `generate()` y validación de nulo.
9. Entidad JPA y mapper escritos a mano (sin MapStruct). Mapper: `toJpa` / `toDomain`.
10. Clases en inglés, singular, PascalCase; endpoint = nombre de tabla en kebab-case (ver tabla).
11. Código, comentarios y mensajes de error en inglés.

## 6. Plantilla por aggregate (mínimo a generar)
- domain: `X`, `XId`, `XRepository` (`save`, `findById`, `findAll`, `delete`, y `existsBy<Campo>` por cada columna UNIQUE), 3 eventos.
- application: 2 commands, `XResponse`, `XNotFoundApplicationException`, `XAlreadyExistsApplicationException` (si hay únicos), 5 casos de uso.
- infrastructure: controller (POST/GET/GET{id}/PUT/DELETE), 2 request DTOs, entity, mapper, JpaRepository, RepositoryAdapter, `<X>BeansConfig`.
- tests: test del aggregate (register + eventos + invariantes) y tests de `Register`, `Update` y `Delete` con repositorio fake (sin Mockito si se puede).
- Los campos salen **de la migración SQL**: mismo nombre de columna, tipo, longitud y nulabilidad.

## 7. Correcciones sobre el demo (NO copiar estos defectos)
1. El demo declara `existsByCode` pero **no lo usa**: nuestro `Register`/`Update` deben validar unicidad y lanzar `...AlreadyExistsApplicationException`.
2. En el demo el `DeletedEvent` lo fabrica el caso de uso. Aquí lo registra el aggregate en `delete()`; el caso de uso llama `x.delete()` y luego `repository.delete(x)`.
3. `CountryNotFoundException` del demo está en `common/` y no se usa. Aquí: excepciones por aggregate, y `DomainException` base en `common`.
4. En el demo la entidad JPA no coincide con la migración del propio demo (longitudes distintas, no mapea `created_at/updated_at`, que son NOT NULL). Aquí la entidad **replica la migración exacta**. Con `ddl-auto: validate` Hibernate debe arrancar sin errores.
5. El demo no tiene manejador global de errores. Aquí `GlobalExceptionHandler` (`@RestControllerAdvice`, `ProblemDetail`): NotFound→404, AlreadyExists→409, DomainException→422, validación (`MethodArgumentNotValidException`)→400, `DataIntegrityViolationException`→409.
6. Los `@Size(max=...)` de los Request DTOs deben coincidir con el `VARCHAR(n)` de la migración.
7. `application-dev.yml` del demo usa `create-drop` y Flyway apagado: **no** copiarlo. Se mantiene el de MindConnect (Flyway activo).

## 8. Decisiones del proyecto (salvo que el usuario cambie alguna)
- **Fechas**: dominio y entidades usan `java.time.Instant` para columnas `TIMESTAMPTZ`; `LocalDate` para `DATE`. El aggregate mantiene `createdAt`/`updatedAt` si la tabla los tiene (`register` los fija, `update` refresca `updatedAt`). `occurredOn` de los eventos también `Instant`.
- **Borrado**: físico (`repository.delete`). Una violación de FK al borrar se traduce a 409.
- **Eventos de dominio**: se registran en el aggregate pero **todavía no se publican** (no hay bus). Fuera de alcance por ahora.
- **Transacciones**: sin `@Transactional` en domain/application. Un decorador transaccional en `infrastructure/common` queda para una fase posterior.
- **JSONB** (`chat_messages.content/metadata`): en la entidad `String` con `@JdbcTypeCode(SqlTypes.JSON)`; en dominio como `String` JSON.
- **Auditoría** `created_by/updated_by/...`: son `ProfessionalId` (nullable cuando la migración lo permite).

## 9. Forma de trabajar
- Rama `feature/ddd-bounded-contexts`. Trabajo por **lotes**: un lote = un contexto (si tiene más de 5 tablas, sublotes de máximo 5 en el orden de la sección 4). **Un commit por lote**: `feat(<ctx>): ...`. El primer lote es solo `country` (piloto, Fase 1).
- Antes de escribir código de un lote: leer las migraciones de sus tablas y listar columnas, tipos, nulabilidad, únicos y FK. Confirmar con el usuario si algo es ambiguo.
- Al terminar cada lote, **obligatorio**: `mvn -q clean verify` en la raíz sin errores, arrancar la app contra la BD y comprobar que Flyway y Hibernate (`validate`) no fallan, y probar con `curl` al menos POST + GET de las tablas del lote. Reportar el resultado real; no declarar éxito sin haberlo ejecutado.
- No tocar migraciones, poms ni archivos fuera del lote actual sin avisar. No hacer refactors "de paso".
- No generar más de un lote por respuesta. Al cerrar uno, resumir qué se creó y esperar confirmación.
- Si no se puede ejecutar algo (Maven, BD, curl), decirlo explícitamente en lugar de suponer que funciona.
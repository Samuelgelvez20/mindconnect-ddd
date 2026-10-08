package com.mindconnect.application.clinicalcatalog.diagnosticsystem;

import com.mindconnect.application.clinicalcatalog.diagnosticsystem.command.RegisterDiagnosticSystemCommand;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.command.UpdateDiagnosticSystemCommand;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.dto.DiagnosticSystemResponse;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.exception.DiagnosticSystemAlreadyExistsApplicationException;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.exception.DiagnosticSystemNotFoundApplicationException;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.DeleteDiagnosticSystemUseCase;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.GetDiagnosticSystemByIdUseCase;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.ListDiagnosticSystemUseCase;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.RegisterDiagnosticSystemUseCase;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.UpdateDiagnosticSystemUseCase;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.port.repository.DiagnosticSystemRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiagnosticSystemUseCaseTest {

    private DiagnosticSystemRepository repository;
    private RegisterDiagnosticSystemUseCase registerUseCase;
    private GetDiagnosticSystemByIdUseCase getByIdUseCase;
    private ListDiagnosticSystemUseCase listUseCase;
    private UpdateDiagnosticSystemUseCase updateUseCase;
    private DeleteDiagnosticSystemUseCase deleteUseCase;

    @BeforeEach
    void setUp() {
        repository = new FakeDiagnosticSystemRepository();
        registerUseCase = new RegisterDiagnosticSystemUseCase(repository);
        getByIdUseCase = new GetDiagnosticSystemByIdUseCase(repository);
        listUseCase = new ListDiagnosticSystemUseCase(repository);
        updateUseCase = new UpdateDiagnosticSystemUseCase(repository);
        deleteUseCase = new DeleteDiagnosticSystemUseCase(repository);
    }

    @Test
    void register_ValidCommand_CreatesSystem() {
        var command = new RegisterDiagnosticSystemCommand("ICD10", "ICD-10", "2019");

        DiagnosticSystemResponse response = registerUseCase.execute(command);

        assertNotNull(response);
        assertEquals("ICD10", response.code());
        assertEquals("ICD-10", response.name());
        assertEquals("2019", response.version());
        assertTrue(response.active());
    }

    @Test
    void register_ValidCommandWithNullVersion_CreatesSystemWithNullVersion() {
        var command = new RegisterDiagnosticSystemCommand("ICD10", "ICD-10", null);

        DiagnosticSystemResponse response = registerUseCase.execute(command);

        assertNotNull(response);
        assertEquals("ICD10", response.code());
        assertEquals("ICD-10", response.name());
        assertNull(response.version());
    }

    @Test
    void register_DuplicateCode_ThrowsException() {
        registerUseCase.execute(new RegisterDiagnosticSystemCommand("ICD10", "ICD-10", "2019"));

        assertThrows(DiagnosticSystemAlreadyExistsApplicationException.class,
                () -> registerUseCase.execute(new RegisterDiagnosticSystemCommand("ICD10", "Different Name", "2022")));
    }

    @Test
    void getById_ExistingSystem_ReturnsSystem() {
        var created = registerUseCase.execute(new RegisterDiagnosticSystemCommand("ICD10", "ICD-10", "2019"));

        DiagnosticSystemResponse response = getByIdUseCase.execute(new DiagnosticSystemId(UUID.fromString(created.id())));

        assertEquals("ICD10", response.code());
        assertEquals("ICD-10", response.name());
        assertEquals("2019", response.version());
    }

    @Test
    void getById_NonExistingSystem_ThrowsException() {
        assertThrows(DiagnosticSystemNotFoundApplicationException.class,
                () -> getByIdUseCase.execute(DiagnosticSystemId.generate()));
    }

    @Test
    void list_ReturnsAllSystems() {
        registerUseCase.execute(new RegisterDiagnosticSystemCommand("ICD10", "ICD-10", "2019"));
        registerUseCase.execute(new RegisterDiagnosticSystemCommand("DSM5", "DSM-5", "2013"));

        List<DiagnosticSystemResponse> systems = listUseCase.execute();

        assertEquals(2, systems.size());
    }

    @Test
    void update_ValidCommand_UpdatesSystem() {
        var created = registerUseCase.execute(new RegisterDiagnosticSystemCommand("ICD10", "ICD-10", "2019"));

        var response = updateUseCase.execute(new DiagnosticSystemId(UUID.fromString(created.id())), new UpdateDiagnosticSystemCommand("ICD11", "ICD-11", "2022", false));

        assertEquals("ICD11", response.code());
        assertEquals("ICD-11", response.name());
        assertEquals("2022", response.version());
        assertFalse(response.active());
    }

    @Test
    void update_WithNullVersion_UpdatesVersionToNull() {
        var created = registerUseCase.execute(new RegisterDiagnosticSystemCommand("ICD10", "ICD-10", "2019"));

        var response = updateUseCase.execute(new DiagnosticSystemId(UUID.fromString(created.id())), new UpdateDiagnosticSystemCommand("ICD11", "ICD-11", null, true));

        assertNull(response.version());
    }

    @Test
    void update_ActiveChange_UpdatesActiveField() {
        var created = registerUseCase.execute(new RegisterDiagnosticSystemCommand("ICD10", "ICD-10", "2019"));
        assertTrue(created.active());

        var response = updateUseCase.execute(new DiagnosticSystemId(UUID.fromString(created.id())), new UpdateDiagnosticSystemCommand("ICD10", "ICD-10", "2019", false));

        assertFalse(response.active());
    }

    @Test
    void update_NonExistingSystem_ThrowsException() {
        assertThrows(DiagnosticSystemNotFoundApplicationException.class,
                () -> updateUseCase.execute(DiagnosticSystemId.generate(), new UpdateDiagnosticSystemCommand("NEW", "New Name", "v1", true)));
    }

    @Test
    void update_DuplicateCodeExcludingSelf_ThrowsException() {
        registerUseCase.execute(new RegisterDiagnosticSystemCommand("ICD10", "ICD-10", "2019"));
        var created = registerUseCase.execute(new RegisterDiagnosticSystemCommand("DSM5", "DSM-5", "2013"));

        assertThrows(DiagnosticSystemAlreadyExistsApplicationException.class,
                () -> updateUseCase.execute(new DiagnosticSystemId(UUID.fromString(created.id())), new UpdateDiagnosticSystemCommand("ICD10", "Duplicate Code", "2022", true)));
    }

    @Test
    void update_SameCodeAsSelf_DoesNotThrowException() {
        var created = registerUseCase.execute(new RegisterDiagnosticSystemCommand("ICD10", "ICD-10", "2019"));

        var response = updateUseCase.execute(new DiagnosticSystemId(UUID.fromString(created.id())), new UpdateDiagnosticSystemCommand("ICD10", "Updated Name", "2022", false));

        assertEquals("ICD10", response.code());
        assertEquals("Updated Name", response.name());
        assertEquals("2022", response.version());
        assertFalse(response.active());
    }

    @Test
    void delete_ExistingSystem_DeletesSystem() {
        var created = registerUseCase.execute(new RegisterDiagnosticSystemCommand("ICD10", "ICD-10", "2019"));

        deleteUseCase.execute(new DiagnosticSystemId(UUID.fromString(created.id())));

        assertThrows(DiagnosticSystemNotFoundApplicationException.class,
                () -> getByIdUseCase.execute(new DiagnosticSystemId(UUID.fromString(created.id()))));
    }

    @Test
    void delete_NonExistingSystem_ThrowsException() {
        assertThrows(DiagnosticSystemNotFoundApplicationException.class,
                () -> deleteUseCase.execute(DiagnosticSystemId.generate()));
    }

    @Test
    void nameNotUnique_MultipleSystemsWithSameNameAllowed() {
        registerUseCase.execute(new RegisterDiagnosticSystemCommand("ICD10", "ICD-10", "2019"));
        var second = registerUseCase.execute(new RegisterDiagnosticSystemCommand("DSM5", "ICD-10", "2013"));

        assertEquals("ICD-10", second.name());
    }

    @Test
    void versionNotUnique_MultipleSystemsWithSameVersionAllowed() {
        registerUseCase.execute(new RegisterDiagnosticSystemCommand("ICD10", "ICD-10", "2019"));
        var second = registerUseCase.execute(new RegisterDiagnosticSystemCommand("DSM5", "DSM-5", "2019"));

        assertEquals("2019", second.version());
    }

    static class FakeDiagnosticSystemRepository implements DiagnosticSystemRepository {

        private final ConcurrentHashMap<UUID, DiagnosticSystem> store = new ConcurrentHashMap<>();
        private final AtomicReference<DiagnosticSystem> lastSaved = new AtomicReference<>();

        @Override
        public DiagnosticSystem save(DiagnosticSystem diagnosticSystem) {
            store.put(diagnosticSystem.id().value(), diagnosticSystem);
            lastSaved.set(diagnosticSystem);
            return diagnosticSystem;
        }

        @Override
        public Optional<DiagnosticSystem> findById(DiagnosticSystemId id) {
            return Optional.ofNullable(store.get(id.value()));
        }

        @Override
        public Optional<DiagnosticSystem> findByCode(String code) {
            return store.values().stream()
                    .filter(r -> r.code().equals(code))
                    .findFirst();
        }

        @Override
        public boolean existsByCodeAndIdNot(String code, DiagnosticSystemId id) {
            return store.values().stream()
                    .anyMatch(r -> r.code().equals(code) && !r.id().equals(id));
        }

        @Override
        public List<DiagnosticSystem> findAll() {
            return List.copyOf(store.values());
        }

        @Override
        public void delete(DiagnosticSystem diagnosticSystem) {
            store.remove(diagnosticSystem.id().value());
        }
    }
}
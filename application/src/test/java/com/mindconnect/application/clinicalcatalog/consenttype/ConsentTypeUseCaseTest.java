package com.mindconnect.application.clinicalcatalog.consenttype;

import com.mindconnect.application.clinicalcatalog.consenttype.command.RegisterConsentTypeCommand;
import com.mindconnect.application.clinicalcatalog.consenttype.command.UpdateConsentTypeCommand;
import com.mindconnect.application.clinicalcatalog.consenttype.dto.ConsentTypeResponse;
import com.mindconnect.application.clinicalcatalog.consenttype.exception.ConsentTypeAlreadyExistsApplicationException;
import com.mindconnect.application.clinicalcatalog.consenttype.exception.ConsentTypeNotFoundApplicationException;
import com.mindconnect.application.clinicalcatalog.consenttype.usecase.DeleteConsentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.consenttype.usecase.GetConsentTypeByIdUseCase;
import com.mindconnect.application.clinicalcatalog.consenttype.usecase.ListConsentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.consenttype.usecase.RegisterConsentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.consenttype.usecase.UpdateConsentTypeUseCase;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.aggregate.ConsentType;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.valueobject.ConsentTypeId;
import com.mindconnect.domain.clinicalcatalog.consenttype.port.repository.ConsentTypeRepository;

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

class ConsentTypeUseCaseTest {

    private ConsentTypeRepository repository;
    private RegisterConsentTypeUseCase registerUseCase;
    private GetConsentTypeByIdUseCase getByIdUseCase;
    private ListConsentTypeUseCase listUseCase;
    private UpdateConsentTypeUseCase updateUseCase;
    private DeleteConsentTypeUseCase deleteUseCase;

    @BeforeEach
    void setUp() {
        repository = new FakeConsentTypeRepository();
        registerUseCase = new RegisterConsentTypeUseCase(repository);
        getByIdUseCase = new GetConsentTypeByIdUseCase(repository);
        listUseCase = new ListConsentTypeUseCase(repository);
        updateUseCase = new UpdateConsentTypeUseCase(repository);
        deleteUseCase = new DeleteConsentTypeUseCase(repository);
    }

    @Test
    void register_ValidCommand_CreatesType() {
        var command = new RegisterConsentTypeCommand("INFORMED", "Informed Consent", "Standard informed consent");

        ConsentTypeResponse response = registerUseCase.execute(command);

        assertNotNull(response);
        assertEquals("INFORMED", response.code());
        assertEquals("Informed Consent", response.name());
        assertEquals("Standard informed consent", response.description());
        assertTrue(response.active());
    }

    @Test
    void register_ValidCommandWithNullDescription_CreatesTypeWithNullDescription() {
        var command = new RegisterConsentTypeCommand("INFORMED", "Informed Consent", null);

        ConsentTypeResponse response = registerUseCase.execute(command);

        assertNotNull(response);
        assertEquals("INFORMED", response.code());
        assertEquals("Informed Consent", response.name());
        assertNull(response.description());
    }

    @Test
    void register_DuplicateCode_ThrowsException() {
        registerUseCase.execute(new RegisterConsentTypeCommand("INFORMED", "Informed Consent", "desc"));

        assertThrows(ConsentTypeAlreadyExistsApplicationException.class,
                () -> registerUseCase.execute(new RegisterConsentTypeCommand("INFORMED", "Different Name", "desc")));
    }

    @Test
    void getById_ExistingType_ReturnsType() {
        var created = registerUseCase.execute(new RegisterConsentTypeCommand("INFORMED", "Informed Consent", "desc"));

        ConsentTypeResponse response = getByIdUseCase.execute(new ConsentTypeId(UUID.fromString(created.id())));

        assertEquals("INFORMED", response.code());
        assertEquals("Informed Consent", response.name());
        assertEquals("desc", response.description());
    }

    @Test
    void getById_NonExistingType_ThrowsException() {
        assertThrows(ConsentTypeNotFoundApplicationException.class,
                () -> getByIdUseCase.execute(ConsentTypeId.generate()));
    }

    @Test
    void list_ReturnsAllTypes() {
        registerUseCase.execute(new RegisterConsentTypeCommand("INFORMED", "Informed Consent", "desc1"));
        registerUseCase.execute(new RegisterConsentTypeCommand("WITHDRAWAL", "Withdrawal Consent", "desc2"));

        List<ConsentTypeResponse> types = listUseCase.execute();

        assertEquals(2, types.size());
    }

    @Test
    void update_ValidCommand_UpdatesType() {
        var created = registerUseCase.execute(new RegisterConsentTypeCommand("INFORMED", "Informed Consent", "Old desc"));

        var response = updateUseCase.execute(new ConsentTypeId(UUID.fromString(created.id())), new UpdateConsentTypeCommand("WITHDRAWAL", "Withdrawal Consent", "New desc", false));

        assertEquals("WITHDRAWAL", response.code());
        assertEquals("Withdrawal Consent", response.name());
        assertEquals("New desc", response.description());
        assertFalse(response.active());
    }

    @Test
    void update_ActiveChange_UpdatesActiveField() {
        var created = registerUseCase.execute(new RegisterConsentTypeCommand("INFORMED", "Informed Consent", "desc"));
        assertTrue(created.active());

        var response = updateUseCase.execute(new ConsentTypeId(UUID.fromString(created.id())), new UpdateConsentTypeCommand("INFORMED", "Informed Consent", "desc", false));

        assertFalse(response.active());
    }

    @Test
    void update_NonExistingType_ThrowsException() {
        assertThrows(ConsentTypeNotFoundApplicationException.class,
                () -> updateUseCase.execute(ConsentTypeId.generate(), new UpdateConsentTypeCommand("NEW", "New Name", "desc", true)));
    }

    @Test
    void update_DuplicateCodeExcludingSelf_ThrowsException() {
        registerUseCase.execute(new RegisterConsentTypeCommand("INFORMED", "Informed Consent", "desc1"));
        var created = registerUseCase.execute(new RegisterConsentTypeCommand("WITHDRAWAL", "Withdrawal Consent", "desc2"));

        assertThrows(ConsentTypeAlreadyExistsApplicationException.class,
                () -> updateUseCase.execute(new ConsentTypeId(UUID.fromString(created.id())), new UpdateConsentTypeCommand("INFORMED", "Duplicate Code", "desc", true)));
    }

    @Test
    void update_SameCodeAsSelf_DoesNotThrowException() {
        var created = registerUseCase.execute(new RegisterConsentTypeCommand("INFORMED", "Informed Consent", "desc1"));

        var response = updateUseCase.execute(new ConsentTypeId(UUID.fromString(created.id())), new UpdateConsentTypeCommand("INFORMED", "Updated Name", "desc2", false));

        assertEquals("INFORMED", response.code());
        assertEquals("Updated Name", response.name());
        assertEquals("desc2", response.description());
        assertFalse(response.active());
    }

    @Test
    void delete_ExistingType_DeletesType() {
        var created = registerUseCase.execute(new RegisterConsentTypeCommand("INFORMED", "Informed Consent", "desc"));

        deleteUseCase.execute(new ConsentTypeId(UUID.fromString(created.id())));

        assertThrows(ConsentTypeNotFoundApplicationException.class,
                () -> getByIdUseCase.execute(new ConsentTypeId(UUID.fromString(created.id()))));
    }

    @Test
    void delete_NonExistingType_ThrowsException() {
        assertThrows(ConsentTypeNotFoundApplicationException.class,
                () -> deleteUseCase.execute(ConsentTypeId.generate()));
    }

    @Test
    void nameNotUnique_MultipleTypesWithSameNameAllowed() {
        registerUseCase.execute(new RegisterConsentTypeCommand("INFORMED", "Informed Consent", "desc1"));
        var second = registerUseCase.execute(new RegisterConsentTypeCommand("WITHDRAWAL", "Informed Consent", "desc2"));

        assertEquals("Informed Consent", second.name());
    }

    static class FakeConsentTypeRepository implements ConsentTypeRepository {

        private final ConcurrentHashMap<UUID, ConsentType> store = new ConcurrentHashMap<>();
        private final AtomicReference<ConsentType> lastSaved = new AtomicReference<>();

        @Override
        public ConsentType save(ConsentType consentType) {
            store.put(consentType.id().value(), consentType);
            lastSaved.set(consentType);
            return consentType;
        }

        @Override
        public Optional<ConsentType> findById(ConsentTypeId id) {
            return Optional.ofNullable(store.get(id.value()));
        }

        @Override
        public Optional<ConsentType> findByCode(String code) {
            return store.values().stream()
                    .filter(r -> r.code().equals(code))
                    .findFirst();
        }

        @Override
        public boolean existsByCodeAndIdNot(String code, ConsentTypeId id) {
            return store.values().stream()
                    .anyMatch(r -> r.code().equals(code) && !r.id().equals(id));
        }

        @Override
        public List<ConsentType> findAll() {
            return List.copyOf(store.values());
        }

        @Override
        public void delete(ConsentType consentType) {
            store.remove(consentType.id().value());
        }
    }
}
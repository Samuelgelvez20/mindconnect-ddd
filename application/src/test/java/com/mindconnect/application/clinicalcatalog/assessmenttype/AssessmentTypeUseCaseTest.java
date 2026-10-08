package com.mindconnect.application.clinicalcatalog.assessmenttype;

import com.mindconnect.application.clinicalcatalog.assessmenttype.command.RegisterAssessmentTypeCommand;
import com.mindconnect.application.clinicalcatalog.assessmenttype.command.UpdateAssessmentTypeCommand;
import com.mindconnect.application.clinicalcatalog.assessmenttype.dto.AssessmentTypeResponse;
import com.mindconnect.application.clinicalcatalog.assessmenttype.exception.AssessmentTypeAlreadyExistsApplicationException;
import com.mindconnect.application.clinicalcatalog.assessmenttype.exception.AssessmentTypeNotFoundApplicationException;
import com.mindconnect.application.clinicalcatalog.assessmenttype.usecase.DeleteAssessmentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.assessmenttype.usecase.GetAssessmentTypeByIdUseCase;
import com.mindconnect.application.clinicalcatalog.assessmenttype.usecase.ListAssessmentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.assessmenttype.usecase.RegisterAssessmentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.assessmenttype.usecase.UpdateAssessmentTypeUseCase;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.aggregate.AssessmentType;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.port.repository.AssessmentTypeRepository;

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

class AssessmentTypeUseCaseTest {

    private AssessmentTypeRepository repository;
    private RegisterAssessmentTypeUseCase registerUseCase;
    private GetAssessmentTypeByIdUseCase getByIdUseCase;
    private ListAssessmentTypeUseCase listUseCase;
    private UpdateAssessmentTypeUseCase updateUseCase;
    private DeleteAssessmentTypeUseCase deleteUseCase;

    @BeforeEach
    void setUp() {
        repository = new FakeAssessmentTypeRepository();
        registerUseCase = new RegisterAssessmentTypeUseCase(repository);
        getByIdUseCase = new GetAssessmentTypeByIdUseCase(repository);
        listUseCase = new ListAssessmentTypeUseCase(repository);
        updateUseCase = new UpdateAssessmentTypeUseCase(repository);
        deleteUseCase = new DeleteAssessmentTypeUseCase(repository);
    }

    @Test
    void register_ValidCommand_CreatesType() {
        var command = new RegisterAssessmentTypeCommand("PHQ9", "PHQ-9", "Patient Health Questionnaire");

        AssessmentTypeResponse response = registerUseCase.execute(command);

        assertNotNull(response);
        assertEquals("PHQ9", response.code());
        assertEquals("PHQ-9", response.name());
        assertEquals("Patient Health Questionnaire", response.description());
        assertTrue(response.active());
    }

    @Test
    void register_ValidCommandWithNullDescription_CreatesTypeWithNullDescription() {
        var command = new RegisterAssessmentTypeCommand("PHQ9", "PHQ-9", null);

        AssessmentTypeResponse response = registerUseCase.execute(command);

        assertNotNull(response);
        assertEquals("PHQ9", response.code());
        assertEquals("PHQ-9", response.name());
        assertNull(response.description());
    }

    @Test
    void register_DuplicateCode_ThrowsException() {
        registerUseCase.execute(new RegisterAssessmentTypeCommand("PHQ9", "PHQ-9", "desc"));

        assertThrows(AssessmentTypeAlreadyExistsApplicationException.class,
                () -> registerUseCase.execute(new RegisterAssessmentTypeCommand("PHQ9", "Different Name", "desc")));
    }

    @Test
    void getById_ExistingType_ReturnsType() {
        var created = registerUseCase.execute(new RegisterAssessmentTypeCommand("PHQ9", "PHQ-9", "desc"));

        AssessmentTypeResponse response = getByIdUseCase.execute(new AssessmentTypeId(UUID.fromString(created.id())));

        assertEquals("PHQ9", response.code());
        assertEquals("PHQ-9", response.name());
        assertEquals("desc", response.description());
    }

    @Test
    void getById_NonExistingType_ThrowsException() {
        assertThrows(AssessmentTypeNotFoundApplicationException.class,
                () -> getByIdUseCase.execute(AssessmentTypeId.generate()));
    }

    @Test
    void list_ReturnsAllTypes() {
        registerUseCase.execute(new RegisterAssessmentTypeCommand("PHQ9", "PHQ-9", "desc1"));
        registerUseCase.execute(new RegisterAssessmentTypeCommand("GAD7", "GAD-7", "desc2"));

        List<AssessmentTypeResponse> types = listUseCase.execute();

        assertEquals(2, types.size());
    }

    @Test
    void update_ValidCommand_UpdatesType() {
        var created = registerUseCase.execute(new RegisterAssessmentTypeCommand("PHQ9", "PHQ-9", "Old desc"));

        var response = updateUseCase.execute(new AssessmentTypeId(UUID.fromString(created.id())), new UpdateAssessmentTypeCommand("GAD7", "GAD-7", "New desc", false));

        assertEquals("GAD7", response.code());
        assertEquals("GAD-7", response.name());
        assertEquals("New desc", response.description());
        assertFalse(response.active());
    }

    @Test
    void update_ActiveChange_UpdatesActiveField() {
        var created = registerUseCase.execute(new RegisterAssessmentTypeCommand("PHQ9", "PHQ-9", "desc"));
        assertTrue(created.active());

        var response = updateUseCase.execute(new AssessmentTypeId(UUID.fromString(created.id())), new UpdateAssessmentTypeCommand("PHQ9", "PHQ-9", "desc", false));

        assertFalse(response.active());
    }

    @Test
    void update_NonExistingType_ThrowsException() {
        assertThrows(AssessmentTypeNotFoundApplicationException.class,
                () -> updateUseCase.execute(AssessmentTypeId.generate(), new UpdateAssessmentTypeCommand("NEW", "New Name", "desc", true)));
    }

    @Test
    void update_DuplicateCodeExcludingSelf_ThrowsException() {
        registerUseCase.execute(new RegisterAssessmentTypeCommand("PHQ9", "PHQ-9", "desc1"));
        var created = registerUseCase.execute(new RegisterAssessmentTypeCommand("GAD7", "GAD-7", "desc2"));

        assertThrows(AssessmentTypeAlreadyExistsApplicationException.class,
                () -> updateUseCase.execute(new AssessmentTypeId(UUID.fromString(created.id())), new UpdateAssessmentTypeCommand("PHQ9", "Duplicate Code", "desc", true)));
    }

    @Test
    void update_SameCodeAsSelf_DoesNotThrowException() {
        var created = registerUseCase.execute(new RegisterAssessmentTypeCommand("PHQ9", "PHQ-9", "desc1"));

        var response = updateUseCase.execute(new AssessmentTypeId(UUID.fromString(created.id())), new UpdateAssessmentTypeCommand("PHQ9", "Updated Name", "desc2", false));

        assertEquals("PHQ9", response.code());
        assertEquals("Updated Name", response.name());
        assertEquals("desc2", response.description());
        assertFalse(response.active());
    }

    @Test
    void delete_ExistingType_DeletesType() {
        var created = registerUseCase.execute(new RegisterAssessmentTypeCommand("PHQ9", "PHQ-9", "desc"));

        deleteUseCase.execute(new AssessmentTypeId(UUID.fromString(created.id())));

        assertThrows(AssessmentTypeNotFoundApplicationException.class,
                () -> getByIdUseCase.execute(new AssessmentTypeId(UUID.fromString(created.id()))));
    }

    @Test
    void delete_NonExistingType_ThrowsException() {
        assertThrows(AssessmentTypeNotFoundApplicationException.class,
                () -> deleteUseCase.execute(AssessmentTypeId.generate()));
    }

    @Test
    void nameNotUnique_MultipleTypesWithSameNameAllowed() {
        registerUseCase.execute(new RegisterAssessmentTypeCommand("PHQ9", "PHQ-9", "desc1"));
        var second = registerUseCase.execute(new RegisterAssessmentTypeCommand("GAD7", "PHQ-9", "desc2"));

        assertEquals("PHQ-9", second.name());
    }

    static class FakeAssessmentTypeRepository implements AssessmentTypeRepository {

        private final ConcurrentHashMap<UUID, AssessmentType> store = new ConcurrentHashMap<>();
        private final AtomicReference<AssessmentType> lastSaved = new AtomicReference<>();

        @Override
        public AssessmentType save(AssessmentType assessmentType) {
            store.put(assessmentType.id().value(), assessmentType);
            lastSaved.set(assessmentType);
            return assessmentType;
        }

        @Override
        public Optional<AssessmentType> findById(AssessmentTypeId id) {
            return Optional.ofNullable(store.get(id.value()));
        }

        @Override
        public Optional<AssessmentType> findByCode(String code) {
            return store.values().stream()
                    .filter(r -> r.code().equals(code))
                    .findFirst();
        }

        @Override
        public boolean existsByCodeAndIdNot(String code, AssessmentTypeId id) {
            return store.values().stream()
                    .anyMatch(r -> r.code().equals(code) && !r.id().equals(id));
        }

        @Override
        public List<AssessmentType> findAll() {
            return List.copyOf(store.values());
        }

        @Override
        public void delete(AssessmentType assessmentType) {
            store.remove(assessmentType.id().value());
        }
    }
}
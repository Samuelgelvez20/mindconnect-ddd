package com.mindconnect.application.clinicalcatalog.medicationroute;

import com.mindconnect.application.clinicalcatalog.medicationroute.command.RegisterMedicationRouteCommand;
import com.mindconnect.application.clinicalcatalog.medicationroute.command.UpdateMedicationRouteCommand;
import com.mindconnect.application.clinicalcatalog.medicationroute.dto.MedicationRouteResponse;
import com.mindconnect.application.clinicalcatalog.medicationroute.exception.MedicationRouteAlreadyExistsApplicationException;
import com.mindconnect.application.clinicalcatalog.medicationroute.exception.MedicationRouteNotFoundApplicationException;
import com.mindconnect.application.clinicalcatalog.medicationroute.usecase.DeleteMedicationRouteUseCase;
import com.mindconnect.application.clinicalcatalog.medicationroute.usecase.GetMedicationRouteByIdUseCase;
import com.mindconnect.application.clinicalcatalog.medicationroute.usecase.ListMedicationRouteUseCase;
import com.mindconnect.application.clinicalcatalog.medicationroute.usecase.RegisterMedicationRouteUseCase;
import com.mindconnect.application.clinicalcatalog.medicationroute.usecase.UpdateMedicationRouteUseCase;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.valueobject.MedicationRouteId;
import com.mindconnect.domain.clinicalcatalog.medicationroute.port.repository.MedicationRouteRepository;

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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MedicationRouteUseCaseTest {

    private MedicationRouteRepository repository;
    private RegisterMedicationRouteUseCase registerUseCase;
    private GetMedicationRouteByIdUseCase getByIdUseCase;
    private ListMedicationRouteUseCase listUseCase;
    private UpdateMedicationRouteUseCase updateUseCase;
    private DeleteMedicationRouteUseCase deleteUseCase;

    @BeforeEach
    void setUp() {
        repository = new FakeMedicationRouteRepository();
        registerUseCase = new RegisterMedicationRouteUseCase(repository);
        getByIdUseCase = new GetMedicationRouteByIdUseCase(repository);
        listUseCase = new ListMedicationRouteUseCase(repository);
        updateUseCase = new UpdateMedicationRouteUseCase(repository);
        deleteUseCase = new DeleteMedicationRouteUseCase(repository);
    }

    @Test
    void register_ValidCommand_CreatesRoute() {
        var command = new RegisterMedicationRouteCommand("ORAL", "Oral Route");

        MedicationRouteResponse response = registerUseCase.execute(command);

        assertNotNull(response);
        assertEquals("ORAL", response.code());
        assertEquals("Oral Route", response.name());
        assertTrue(response.active());
    }

    @Test
    void register_DuplicateCode_ThrowsException() {
        registerUseCase.execute(new RegisterMedicationRouteCommand("ORAL", "Oral Route"));

        assertThrows(MedicationRouteAlreadyExistsApplicationException.class,
                () -> registerUseCase.execute(new RegisterMedicationRouteCommand("ORAL", "Different Name")));
    }

    @Test
    void getById_ExistingRoute_ReturnsRoute() {
        var created = registerUseCase.execute(new RegisterMedicationRouteCommand("ORAL", "Oral Route"));

        MedicationRouteResponse response = getByIdUseCase.execute(new MedicationRouteId(UUID.fromString(created.id())));

        assertEquals("ORAL", response.code());
        assertEquals("Oral Route", response.name());
    }

    @Test
    void getById_NonExistingRoute_ThrowsException() {
        assertThrows(MedicationRouteNotFoundApplicationException.class,
                () -> getByIdUseCase.execute(MedicationRouteId.generate()));
    }

    @Test
    void list_ReturnsAllRoutes() {
        registerUseCase.execute(new RegisterMedicationRouteCommand("ORAL", "Oral Route"));
        registerUseCase.execute(new RegisterMedicationRouteCommand("IV", "Intravenous Route"));

        List<MedicationRouteResponse> routes = listUseCase.execute();

        assertEquals(2, routes.size());
    }

    @Test
    void update_ValidCommand_UpdatesRoute() {
        var created = registerUseCase.execute(new RegisterMedicationRouteCommand("ORAL", "Oral Route"));

        var response = updateUseCase.execute(new MedicationRouteId(UUID.fromString(created.id())), new UpdateMedicationRouteCommand("IV", "Intravenous Route", false));

        assertEquals("IV", response.code());
        assertEquals("Intravenous Route", response.name());
        assertFalse(response.active());
    }

    @Test
    void update_ActiveChange_UpdatesActiveField() {
        var created = registerUseCase.execute(new RegisterMedicationRouteCommand("ORAL", "Oral Route"));
        assertTrue(created.active());

        var response = updateUseCase.execute(new MedicationRouteId(UUID.fromString(created.id())), new UpdateMedicationRouteCommand("ORAL", "Oral Route", false));

        assertFalse(response.active());
    }

    @Test
    void update_NonExistingRoute_ThrowsException() {
        assertThrows(MedicationRouteNotFoundApplicationException.class,
                () -> updateUseCase.execute(MedicationRouteId.generate(), new UpdateMedicationRouteCommand("NEW", "New Name", true)));
    }

    @Test
    void update_DuplicateCodeExcludingSelf_ThrowsException() {
        registerUseCase.execute(new RegisterMedicationRouteCommand("ORAL", "Oral Route"));
        var created = registerUseCase.execute(new RegisterMedicationRouteCommand("IV", "Intravenous Route"));

        assertThrows(MedicationRouteAlreadyExistsApplicationException.class,
                () -> updateUseCase.execute(new MedicationRouteId(UUID.fromString(created.id())), new UpdateMedicationRouteCommand("ORAL", "Duplicate Code", true)));
    }

    @Test
    void update_SameCodeAsSelf_DoesNotThrowException() {
        var created = registerUseCase.execute(new RegisterMedicationRouteCommand("ORAL", "Oral Route"));

        var response = updateUseCase.execute(new MedicationRouteId(UUID.fromString(created.id())), new UpdateMedicationRouteCommand("ORAL", "Updated Name", false));

        assertEquals("ORAL", response.code());
        assertEquals("Updated Name", response.name());
        assertFalse(response.active());
    }

    @Test
    void delete_ExistingRoute_DeletesRoute() {
        var created = registerUseCase.execute(new RegisterMedicationRouteCommand("ORAL", "Oral Route"));

        deleteUseCase.execute(new MedicationRouteId(UUID.fromString(created.id())));

        assertThrows(MedicationRouteNotFoundApplicationException.class,
                () -> getByIdUseCase.execute(new MedicationRouteId(UUID.fromString(created.id()))));
    }

    @Test
    void delete_NonExistingRoute_ThrowsException() {
        assertThrows(MedicationRouteNotFoundApplicationException.class,
                () -> deleteUseCase.execute(MedicationRouteId.generate()));
    }

    static class FakeMedicationRouteRepository implements MedicationRouteRepository {

        private final ConcurrentHashMap<UUID, MedicationRoute> store = new ConcurrentHashMap<>();
        private final AtomicReference<MedicationRoute> lastSaved = new AtomicReference<>();

        @Override
        public MedicationRoute save(MedicationRoute medicationRoute) {
            store.put(medicationRoute.id().value(), medicationRoute);
            lastSaved.set(medicationRoute);
            return medicationRoute;
        }

        @Override
        public Optional<MedicationRoute> findById(MedicationRouteId id) {
            return Optional.ofNullable(store.get(id.value()));
        }

        @Override
        public Optional<MedicationRoute> findByCode(String code) {
            return store.values().stream()
                    .filter(r -> r.code().equals(code))
                    .findFirst();
        }

        @Override
        public boolean existsByCodeAndIdNot(String code, MedicationRouteId id) {
            return store.values().stream()
                    .anyMatch(r -> r.code().equals(code) && !r.id().equals(id));
        }

        @Override
        public List<MedicationRoute> findAll() {
            return List.copyOf(store.values());
        }

        @Override
        public void delete(MedicationRoute medicationRoute) {
            store.remove(medicationRoute.id().value());
        }
    }
}
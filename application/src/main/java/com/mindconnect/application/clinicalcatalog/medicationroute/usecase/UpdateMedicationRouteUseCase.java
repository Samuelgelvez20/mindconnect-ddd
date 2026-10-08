package com.mindconnect.application.clinicalcatalog.medicationroute.usecase;

import com.mindconnect.application.clinicalcatalog.medicationroute.dto.MedicationRouteResponse;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.valueobject.MedicationRouteId;
import com.mindconnect.application.clinicalcatalog.medicationroute.command.UpdateMedicationRouteCommand;
import com.mindconnect.application.clinicalcatalog.medicationroute.exception.MedicationRouteNotFoundApplicationException;
import com.mindconnect.application.clinicalcatalog.medicationroute.exception.MedicationRouteAlreadyExistsApplicationException;
import com.mindconnect.domain.clinicalcatalog.medicationroute.port.repository.MedicationRouteRepository;

public class UpdateMedicationRouteUseCase {

    private final MedicationRouteRepository repository;

    public UpdateMedicationRouteUseCase(MedicationRouteRepository repository) {
        this.repository = repository;
    }

    public MedicationRouteResponse execute(MedicationRouteId id, UpdateMedicationRouteCommand command) {
        MedicationRoute route = repository.findById(id)
                .orElseThrow(() -> new MedicationRouteNotFoundApplicationException(id.value().toString()));

        if (repository.existsByCodeAndIdNot(command.code(), id)) {
            throw new MedicationRouteAlreadyExistsApplicationException(command.code());
        }

        route.update(command.code(), command.name(), command.active());

        repository.save(route);

        return MedicationRouteResponse.from(route);
    }
}
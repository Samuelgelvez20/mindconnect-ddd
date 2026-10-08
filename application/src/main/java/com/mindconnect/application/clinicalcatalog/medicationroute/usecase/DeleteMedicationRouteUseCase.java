package com.mindconnect.application.clinicalcatalog.medicationroute.usecase;

import com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.valueobject.MedicationRouteId;
import com.mindconnect.application.clinicalcatalog.medicationroute.exception.MedicationRouteNotFoundApplicationException;
import com.mindconnect.domain.clinicalcatalog.medicationroute.port.repository.MedicationRouteRepository;

public class DeleteMedicationRouteUseCase {

    private final MedicationRouteRepository repository;

    public DeleteMedicationRouteUseCase(MedicationRouteRepository repository) {
        this.repository = repository;
    }

    public void execute(MedicationRouteId id) {
        MedicationRoute route = repository.findById(id)
                .orElseThrow(() -> new MedicationRouteNotFoundApplicationException(id.value().toString()));

        repository.delete(route);
    }
}
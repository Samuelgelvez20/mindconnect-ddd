package com.mindconnect.application.clinicalcatalog.medicationroute.usecase;

import com.mindconnect.application.clinicalcatalog.medicationroute.dto.MedicationRouteResponse;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.valueobject.MedicationRouteId;
import com.mindconnect.application.clinicalcatalog.medicationroute.exception.MedicationRouteNotFoundApplicationException;
import com.mindconnect.domain.clinicalcatalog.medicationroute.port.repository.MedicationRouteRepository;

public class GetMedicationRouteByIdUseCase {

    private final MedicationRouteRepository repository;

    public GetMedicationRouteByIdUseCase(MedicationRouteRepository repository) {
        this.repository = repository;
    }

    public MedicationRouteResponse execute(MedicationRouteId id) {
        MedicationRoute route = repository.findById(id)
                .orElseThrow(() -> new MedicationRouteNotFoundApplicationException(id.value().toString()));

        return MedicationRouteResponse.from(route);
    }
}
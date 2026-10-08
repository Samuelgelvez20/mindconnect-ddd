package com.mindconnect.application.clinicalcatalog.medicationroute.usecase;

import com.mindconnect.application.clinicalcatalog.medicationroute.dto.MedicationRouteResponse;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute;
import com.mindconnect.domain.clinicalcatalog.medicationroute.port.repository.MedicationRouteRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListMedicationRouteUseCase {

    private final MedicationRouteRepository repository;

    public ListMedicationRouteUseCase(MedicationRouteRepository repository) {
        this.repository = repository;
    }

    public List<MedicationRouteResponse> execute() {
        return repository.findAll().stream()
                .map(MedicationRouteResponse::from)
                .collect(Collectors.toList());
    }
}
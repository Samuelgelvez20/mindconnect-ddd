package com.mindconnect.application.clinicalcatalog.medicationroute.usecase;

import com.mindconnect.application.clinicalcatalog.medicationroute.dto.MedicationRouteResponse;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.valueobject.MedicationRouteId;
import com.mindconnect.application.clinicalcatalog.medicationroute.command.RegisterMedicationRouteCommand;
import com.mindconnect.application.clinicalcatalog.medicationroute.exception.MedicationRouteAlreadyExistsApplicationException;
import com.mindconnect.domain.clinicalcatalog.medicationroute.port.repository.MedicationRouteRepository;

import java.util.Optional;

public class RegisterMedicationRouteUseCase {

    private final MedicationRouteRepository repository;

    public RegisterMedicationRouteUseCase(MedicationRouteRepository repository) {
        this.repository = repository;
    }

    public MedicationRouteResponse execute(RegisterMedicationRouteCommand command) {
        Optional<MedicationRoute> existing = repository.findByCode(command.code());

        if (existing.isPresent()) {
            throw new MedicationRouteAlreadyExistsApplicationException(command.code());
        }

        MedicationRoute route = MedicationRoute.register(command.code(), command.name());
        repository.save(route);

        return MedicationRouteResponse.from(route);
    }
}
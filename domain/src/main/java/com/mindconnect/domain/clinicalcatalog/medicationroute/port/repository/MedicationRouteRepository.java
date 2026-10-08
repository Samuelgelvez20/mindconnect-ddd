package com.mindconnect.domain.clinicalcatalog.medicationroute.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.valueobject.MedicationRouteId;

public interface MedicationRouteRepository {

    MedicationRoute save(MedicationRoute medicationRoute);

    Optional<MedicationRoute> findById(MedicationRouteId id);

    Optional<MedicationRoute> findByCode(String code);

    boolean existsByCodeAndIdNot(String code, MedicationRouteId id);

    List<MedicationRoute> findAll();

    void delete(MedicationRoute medicationRoute);
}
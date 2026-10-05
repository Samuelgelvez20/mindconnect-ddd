package com.mindconnect.infrastructure.patient.patientallergy.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.patient.patientallergy.model.aggregate.PatientAllergy;
import com.mindconnect.domain.patient.patientallergy.model.valueobject.PatientAllergyId;
import com.mindconnect.domain.patient.patientallergy.port.repository.PatientAllergyRepository;
import com.mindconnect.infrastructure.patient.patientallergy.adapters.out.persistence.mappers.PatientAllergyPersistenceMapper;

public class PatientAllergyRepositoryAdapter implements PatientAllergyRepository {

    private final PatientAllergyJpaRepository patientAllergyJpaRepository;
    private final PatientAllergyPersistenceMapper mapper;

    public PatientAllergyRepositoryAdapter(
            PatientAllergyJpaRepository patientAllergyJpaRepository,
            PatientAllergyPersistenceMapper mapper) {
        this.patientAllergyJpaRepository = patientAllergyJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public PatientAllergy save(PatientAllergy patientAllergy) {
        return mapper.toDomain(patientAllergyJpaRepository.save(mapper.toJpa(patientAllergy)));
    }

    @Override
    public Optional<PatientAllergy> findById(PatientAllergyId id) {
        return patientAllergyJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<PatientAllergy> findAll() {
        return patientAllergyJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(PatientAllergy patientAllergy) {
        patientAllergyJpaRepository.deleteById(patientAllergy.id().value());
    }
}
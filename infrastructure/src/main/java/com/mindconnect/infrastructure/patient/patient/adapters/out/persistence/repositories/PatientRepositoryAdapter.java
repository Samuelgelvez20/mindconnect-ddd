package com.mindconnect.infrastructure.patient.patient.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.patient.patient.model.aggregate.Patient;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.patient.patient.port.repository.PatientRepository;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.infrastructure.patient.patient.adapters.out.persistence.mappers.PatientPersistenceMapper;

public class PatientRepositoryAdapter implements PatientRepository {

    private final PatientJpaRepository patientJpaRepository;
    private final PatientPersistenceMapper mapper;

    public PatientRepositoryAdapter(
            PatientJpaRepository patientJpaRepository,
            PatientPersistenceMapper mapper) {
        this.patientJpaRepository = patientJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Patient save(Patient patient) {
        return mapper.toDomain(patientJpaRepository.save(mapper.toJpa(patient)));
    }

    @Override
    public Optional<Patient> findById(PatientId id) {
        return patientJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<Patient> findAll() {
        return patientJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return patientJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, PatientId id) {
        return patientJpaRepository.existsByEmailAndIdNot(email, id.value());
    }

    @Override
    public boolean existsByDocumentTypeIdAndDocumentNumber(
            com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId documentTypeId,
            String documentNumber) {
        return patientJpaRepository.existsByDocumentTypeIdAndDocumentNumber(documentTypeId.value(), documentNumber);
    }

    @Override
    public boolean existsByDocumentTypeIdAndDocumentNumberAndIdNot(
            com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId documentTypeId,
            String documentNumber,
            PatientId id) {
        return patientJpaRepository.existsByDocumentTypeIdAndDocumentNumberAndIdNot(documentTypeId.value(), documentNumber, id.value());
    }

    @Override
    public void delete(Patient patient) {
        patientJpaRepository.deleteById(patient.id().value());
    }
}
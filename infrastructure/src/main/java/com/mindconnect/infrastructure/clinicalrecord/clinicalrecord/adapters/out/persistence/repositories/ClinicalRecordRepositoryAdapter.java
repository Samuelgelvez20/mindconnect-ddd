package com.mindconnect.infrastructure.clinicalrecord.clinicalrecord.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.aggregate.ClinicalRecord;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.port.repository.ClinicalRecordRepository;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.infrastructure.clinicalrecord.clinicalrecord.adapters.out.persistence.mappers.ClinicalRecordPersistenceMapper;

public class ClinicalRecordRepositoryAdapter implements ClinicalRecordRepository {

    private final ClinicalRecordJpaRepository jpaRepository;
    private final ClinicalRecordPersistenceMapper mapper;

    public ClinicalRecordRepositoryAdapter(
            ClinicalRecordJpaRepository jpaRepository,
            ClinicalRecordPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ClinicalRecord save(ClinicalRecord clinicalRecord) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpa(clinicalRecord)));
    }

    @Override
    public Optional<ClinicalRecord> findById(ClinicalRecordId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<ClinicalRecord> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<ClinicalRecord> findByPatientId(PatientId patientId) {
        return jpaRepository.findByPatientId(patientId.value()).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(ClinicalRecord clinicalRecord) {
        jpaRepository.deleteById(clinicalRecord.id().value());
    }
}
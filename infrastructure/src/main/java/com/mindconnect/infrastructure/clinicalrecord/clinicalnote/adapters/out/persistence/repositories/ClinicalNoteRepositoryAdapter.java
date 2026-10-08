package com.mindconnect.infrastructure.clinicalrecord.clinicalnote.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.clinicalnote.model.aggregate.ClinicalNote;
import com.mindconnect.domain.clinicalrecord.clinicalnote.model.valueobject.ClinicalNoteId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.clinicalnote.port.repository.ClinicalNoteRepository;
import com.mindconnect.infrastructure.clinicalrecord.clinicalnote.adapters.out.persistence.mappers.ClinicalNotePersistenceMapper;

public class ClinicalNoteRepositoryAdapter implements ClinicalNoteRepository {

    private final ClinicalNoteJpaRepository clinicalNoteJpaRepository;
    private final ClinicalNotePersistenceMapper mapper;

    public ClinicalNoteRepositoryAdapter(
            ClinicalNoteJpaRepository clinicalNoteJpaRepository,
            ClinicalNotePersistenceMapper mapper) {
        this.clinicalNoteJpaRepository = clinicalNoteJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ClinicalNote save(ClinicalNote clinicalNote) {
        return mapper.toDomain(clinicalNoteJpaRepository.save(mapper.toJpa(clinicalNote)));
    }

    @Override
    public Optional<ClinicalNote> findById(ClinicalNoteId id) {
        return clinicalNoteJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<ClinicalNote> findAll() {
        return clinicalNoteJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<ClinicalNote> findByEncounterId(EncounterId encounterId) {
        return clinicalNoteJpaRepository.findByEncounterId(encounterId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(ClinicalNote clinicalNote) {
        clinicalNoteJpaRepository.deleteById(clinicalNote.id().value());
    }
}
package com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.out.persistence.repositories;

import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.aggregate.MentalStatusExam;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.port.repository.MentalStatusExamRepository;
import com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.out.persistence.entity.MentalStatusExamJpaEntity;
import com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.out.persistence.mappers.MentalStatusExamPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MentalStatusExamRepositoryAdapter implements MentalStatusExamRepository {

    private final MentalStatusExamJpaRepository jpaRepository;
    private final MentalStatusExamPersistenceMapper mapper;

    public MentalStatusExamRepositoryAdapter(MentalStatusExamJpaRepository jpaRepository, MentalStatusExamPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public MentalStatusExam save(MentalStatusExam mentalStatusExam) {
        MentalStatusExamJpaEntity entity = mapper.toJpa(mentalStatusExam);
        MentalStatusExamJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<MentalStatusExam> findById(MentalStatusExamId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<MentalStatusExam> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(MentalStatusExam mentalStatusExam) {
        jpaRepository.delete(mapper.toJpa(mentalStatusExam));
    }

    @Override
    public List<MentalStatusExam> findByEncounterId(EncounterId encounterId) {
        return jpaRepository.findByEncounterId(encounterId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
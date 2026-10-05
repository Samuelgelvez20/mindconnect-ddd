package com.mindconnect.infrastructure.professional.professional.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.professional.professional.model.aggregate.Professional;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.professional.professional.port.repository.ProfessionalRepository;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.infrastructure.professional.professional.adapters.out.persistence.mappers.ProfessionalPersistenceMapper;

public class ProfessionalRepositoryAdapter implements ProfessionalRepository {

    private final ProfessionalJpaRepository professionalJpaRepository;
    private final ProfessionalPersistenceMapper mapper;

    public ProfessionalRepositoryAdapter(
            ProfessionalJpaRepository professionalJpaRepository,
            ProfessionalPersistenceMapper mapper) {
        this.professionalJpaRepository = professionalJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Professional save(Professional professional) {
        return mapper.toDomain(professionalJpaRepository.save(mapper.toJpa(professional)));
    }

    @Override
    public Optional<Professional> findById(ProfessionalId id) {
        return professionalJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<Professional> findAll() {
        return professionalJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByLicenseNumber(String licenseNumber) {
        return professionalJpaRepository.existsByLicenseNumber(licenseNumber);
    }

    @Override
    public boolean existsByLicenseNumberAndIdNot(String licenseNumber, ProfessionalId id) {
        return professionalJpaRepository.existsByLicenseNumberAndIdNot(licenseNumber, id.value());
    }

    @Override
    public boolean existsByDocumentTypeIdAndDocumentNumber(
            com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId documentTypeId,
            String documentNumber) {
        return professionalJpaRepository.existsByDocumentTypeIdAndDocumentNumber(documentTypeId.value(), documentNumber);
    }

    @Override
    public boolean existsByDocumentTypeIdAndDocumentNumberAndIdNot(
            com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId documentTypeId,
            String documentNumber,
            ProfessionalId id) {
        return professionalJpaRepository.existsByDocumentTypeIdAndDocumentNumberAndIdNot(documentTypeId.value(), documentNumber, id.value());
    }

    @Override
    public void delete(Professional professional) {
        professionalJpaRepository.deleteById(professional.id().value());
    }
}
package com.mindconnect.domain.professional.professional.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.professional.professional.model.aggregate.Professional;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

public interface ProfessionalRepository {

    Professional save(Professional professional);

    Optional<Professional> findById(ProfessionalId id);

    List<Professional> findAll();

    boolean existsByLicenseNumber(String licenseNumber);

    boolean existsByLicenseNumberAndIdNot(String licenseNumber, ProfessionalId id);

    boolean existsByDocumentTypeIdAndDocumentNumber(
            com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId documentTypeId,
            String documentNumber);

    boolean existsByDocumentTypeIdAndDocumentNumberAndIdNot(
            com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId documentTypeId,
            String documentNumber,
            ProfessionalId id);

    void delete(Professional professional);
}
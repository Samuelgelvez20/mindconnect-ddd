package com.mindconnect.application.professional.professional.usecase;

import com.mindconnect.application.professional.professional.command.RegisterProfessionalCommand;
import com.mindconnect.application.professional.professional.dto.ProfessionalResponse;
import com.mindconnect.application.professional.professional.exception.ProfessionalAlreadyExistsApplicationException;
import com.mindconnect.domain.professional.professional.model.aggregate.Professional;
import com.mindconnect.domain.professional.professional.port.repository.ProfessionalRepository;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

public class RegisterProfessionalUseCase {

    private final ProfessionalRepository professionalRepository;

    public RegisterProfessionalUseCase(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public ProfessionalResponse execute(RegisterProfessionalCommand command) {

        Professional professional = Professional.register(
                command.documentTypeId(),
                command.documentNumber(),
                command.firstName(),
                command.lastName(),
                command.professionalTypeId(),
                command.licenseNumber(),
                command.cityId());

        if (professionalRepository.existsByLicenseNumber(professional.licenseNumber())) {
            throw new ProfessionalAlreadyExistsApplicationException(professional.licenseNumber());
        }

        if (professionalRepository.existsByDocumentTypeIdAndDocumentNumber(
                professional.documentTypeId(), professional.documentNumber())) {
            throw new ProfessionalAlreadyExistsApplicationException(
                    professional.documentTypeId(), professional.documentNumber());
        }

        return ProfessionalResponse.from(professionalRepository.save(professional));
    }
}
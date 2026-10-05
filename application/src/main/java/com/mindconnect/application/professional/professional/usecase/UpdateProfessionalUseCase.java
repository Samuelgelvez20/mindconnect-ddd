package com.mindconnect.application.professional.professional.usecase;

import com.mindconnect.application.professional.professional.command.UpdateProfessionalCommand;
import com.mindconnect.application.professional.professional.dto.ProfessionalResponse;
import com.mindconnect.application.professional.professional.exception.ProfessionalAlreadyExistsApplicationException;
import com.mindconnect.application.professional.professional.exception.ProfessionalNotFoundApplicationException;
import com.mindconnect.domain.professional.professional.port.repository.ProfessionalRepository;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

public class UpdateProfessionalUseCase {

    private final ProfessionalRepository professionalRepository;

    public UpdateProfessionalUseCase(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public ProfessionalResponse execute(UpdateProfessionalCommand command) {

        var professional = professionalRepository.findById(command.id())
                .orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.id()));

        professional.update(
                command.documentTypeId(),
                command.documentNumber(),
                command.firstName(),
                command.lastName(),
                command.professionalTypeId(),
                command.licenseNumber(),
                command.active(),
                command.cityId());

        if (professionalRepository.existsByLicenseNumberAndIdNot(
                professional.licenseNumber(), professional.id())) {
            throw new ProfessionalAlreadyExistsApplicationException(professional.licenseNumber());
        }

        if (professionalRepository.existsByDocumentTypeIdAndDocumentNumberAndIdNot(
                professional.documentTypeId(), professional.documentNumber(), professional.id())) {
            throw new ProfessionalAlreadyExistsApplicationException(
                    professional.documentTypeId(), professional.documentNumber());
        }

        return ProfessionalResponse.from(professionalRepository.save(professional));
    }
}
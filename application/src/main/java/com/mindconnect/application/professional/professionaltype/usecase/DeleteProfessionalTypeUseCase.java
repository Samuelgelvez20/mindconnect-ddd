package com.mindconnect.application.professional.professionaltype.usecase;

import com.mindconnect.application.professional.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;
import com.mindconnect.domain.professional.professionaltype.port.repository.ProfessionalTypeRepository;

public class DeleteProfessionalTypeUseCase {

    private final ProfessionalTypeRepository professionalTypeRepository;

    public DeleteProfessionalTypeUseCase(ProfessionalTypeRepository professionalTypeRepository) {
        this.professionalTypeRepository = professionalTypeRepository;
    }

    public void execute(ProfessionalTypeId id) {

        var professionalType = professionalTypeRepository.findById(id)
                .orElseThrow(() -> new ProfessionalTypeNotFoundApplicationException(id));

        professionalType.delete();
        professionalTypeRepository.delete(professionalType);
    }
}
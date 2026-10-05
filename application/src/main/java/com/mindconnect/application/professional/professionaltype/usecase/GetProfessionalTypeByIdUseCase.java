package com.mindconnect.application.professional.professionaltype.usecase;

import com.mindconnect.application.professional.professionaltype.dto.ProfessionalTypeResponse;
import com.mindconnect.application.professional.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;
import com.mindconnect.domain.professional.professionaltype.port.repository.ProfessionalTypeRepository;

public class GetProfessionalTypeByIdUseCase {

    private final ProfessionalTypeRepository professionalTypeRepository;

    public GetProfessionalTypeByIdUseCase(ProfessionalTypeRepository professionalTypeRepository) {
        this.professionalTypeRepository = professionalTypeRepository;
    }

    public ProfessionalTypeResponse execute(ProfessionalTypeId id) {
        return professionalTypeRepository.findById(id)
                .map(ProfessionalTypeResponse::from)
                .orElseThrow(() -> new ProfessionalTypeNotFoundApplicationException(id));
    }
}
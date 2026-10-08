package com.mindconnect.application.professional.professional.usecase;

import com.mindconnect.application.professional.professional.dto.ProfessionalResponse;
import com.mindconnect.application.professional.professional.exception.ProfessionalNotFoundApplicationException;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.professional.professional.port.repository.ProfessionalRepository;

public class GetProfessionalByIdUseCase {

    private final ProfessionalRepository professionalRepository;

    public GetProfessionalByIdUseCase(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public ProfessionalResponse execute(ProfessionalId id) {
        return professionalRepository.findById(id)
                .map(ProfessionalResponse::from)
                .orElseThrow(() -> new ProfessionalNotFoundApplicationException(id));
    }
}
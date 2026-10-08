package com.mindconnect.application.professional.professionalstudy.usecase;

import com.mindconnect.application.professional.professionalstudy.dto.ProfessionalStudyResponse;
import com.mindconnect.application.professional.professionalstudy.exception.ProfessionalStudyNotFoundApplicationException;
import com.mindconnect.domain.professional.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.mindconnect.domain.professional.professionalstudy.port.repository.ProfessionalStudyRepository;

public class GetProfessionalStudyByIdUseCase {

    private final ProfessionalStudyRepository professionalStudyRepository;

    public GetProfessionalStudyByIdUseCase(ProfessionalStudyRepository professionalStudyRepository) {
        this.professionalStudyRepository = professionalStudyRepository;
    }

    public ProfessionalStudyResponse execute(ProfessionalStudyId id) {
        return professionalStudyRepository.findById(id)
                .map(ProfessionalStudyResponse::from)
                .orElseThrow(() -> new ProfessionalStudyNotFoundApplicationException(id));
    }
}
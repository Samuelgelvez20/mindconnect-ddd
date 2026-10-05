package com.mindconnect.application.professional.professionalstudy.usecase;

import com.mindconnect.application.professional.professionalstudy.exception.ProfessionalStudyNotFoundApplicationException;
import com.mindconnect.domain.professional.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.mindconnect.domain.professional.professionalstudy.port.repository.ProfessionalStudyRepository;

public class DeleteProfessionalStudyUseCase {

    private final ProfessionalStudyRepository professionalStudyRepository;

    public DeleteProfessionalStudyUseCase(ProfessionalStudyRepository professionalStudyRepository) {
        this.professionalStudyRepository = professionalStudyRepository;
    }

    public void execute(ProfessionalStudyId id) {

        var professionalStudy = professionalStudyRepository.findById(id)
                .orElseThrow(() -> new ProfessionalStudyNotFoundApplicationException(id));

        professionalStudy.delete();
        professionalStudyRepository.delete(professionalStudy);
    }
}
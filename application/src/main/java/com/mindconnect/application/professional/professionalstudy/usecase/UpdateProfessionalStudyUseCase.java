package com.mindconnect.application.professional.professionalstudy.usecase;

import com.mindconnect.application.professional.professionalstudy.command.UpdateProfessionalStudyCommand;
import com.mindconnect.application.professional.professionalstudy.dto.ProfessionalStudyResponse;
import com.mindconnect.application.professional.professionalstudy.exception.ProfessionalStudyNotFoundApplicationException;
import com.mindconnect.domain.professional.professionalstudy.port.repository.ProfessionalStudyRepository;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

public class UpdateProfessionalStudyUseCase {

    private final ProfessionalStudyRepository professionalStudyRepository;

    public UpdateProfessionalStudyUseCase(ProfessionalStudyRepository professionalStudyRepository) {
        this.professionalStudyRepository = professionalStudyRepository;
    }

    public ProfessionalStudyResponse execute(UpdateProfessionalStudyCommand command) {

        var professionalStudy = professionalStudyRepository.findById(command.id())
                .orElseThrow(() -> new ProfessionalStudyNotFoundApplicationException(command.id()));

        professionalStudy.update(
                command.title(),
                command.university(),
                command.isValid(),
                command.resolutionNumber(),
                command.countryId());

        return ProfessionalStudyResponse.from(professionalStudyRepository.save(professionalStudy));
    }
}
package com.mindconnect.application.professional.professionalstudy.usecase;

import com.mindconnect.application.professional.professionalstudy.command.RegisterProfessionalStudyCommand;
import com.mindconnect.application.professional.professionalstudy.dto.ProfessionalStudyResponse;
import com.mindconnect.domain.professional.professionalstudy.model.aggregate.ProfessionalStudy;
import com.mindconnect.domain.professional.professionalstudy.port.repository.ProfessionalStudyRepository;

public class RegisterProfessionalStudyUseCase {

    private final ProfessionalStudyRepository professionalStudyRepository;

    public RegisterProfessionalStudyUseCase(ProfessionalStudyRepository professionalStudyRepository) {
        this.professionalStudyRepository = professionalStudyRepository;
    }

    public ProfessionalStudyResponse execute(RegisterProfessionalStudyCommand command) {

        ProfessionalStudy professionalStudy = ProfessionalStudy.register(
                command.studyId(),
                command.professionalId(),
                command.title(),
                command.university(),
                command.countryId());

        return ProfessionalStudyResponse.from(professionalStudyRepository.save(professionalStudy));
    }
}
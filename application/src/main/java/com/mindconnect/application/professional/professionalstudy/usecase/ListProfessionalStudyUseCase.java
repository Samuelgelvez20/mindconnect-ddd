package com.mindconnect.application.professional.professionalstudy.usecase;

import java.util.List;

import com.mindconnect.application.professional.professionalstudy.dto.ProfessionalStudyResponse;
import com.mindconnect.domain.professional.professionalstudy.port.repository.ProfessionalStudyRepository;

public class ListProfessionalStudyUseCase {

    private final ProfessionalStudyRepository professionalStudyRepository;

    public ListProfessionalStudyUseCase(ProfessionalStudyRepository professionalStudyRepository) {
        this.professionalStudyRepository = professionalStudyRepository;
    }

    public List<ProfessionalStudyResponse> execute() {
        return professionalStudyRepository.findAll()
                .stream()
                .map(ProfessionalStudyResponse::from)
                .toList();
    }
}
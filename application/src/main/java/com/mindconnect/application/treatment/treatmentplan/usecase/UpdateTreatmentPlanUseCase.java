package com.mindconnect.application.treatment.treatmentplan.usecase;

import com.mindconnect.application.treatment.treatmentplan.dto.TreatmentPlanResponse;
import com.mindconnect.domain.treatment.treatmentplan.model.aggregate.TreatmentPlan;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.mindconnect.application.treatment.treatmentplan.command.UpdateTreatmentPlanCommand;
import com.mindconnect.application.treatment.treatmentplan.exception.TreatmentPlanNotFoundApplicationException;
import com.mindconnect.domain.treatment.treatmentplan.port.repository.TreatmentPlanRepository;

public class UpdateTreatmentPlanUseCase {

    private final TreatmentPlanRepository repository;

    public UpdateTreatmentPlanUseCase(TreatmentPlanRepository repository) {
        this.repository = repository;
    }

    public TreatmentPlanResponse execute(UpdateTreatmentPlanCommand command) {

        TreatmentPlanId id = new TreatmentPlanId(java.util.UUID.fromString(command.id()));

        TreatmentPlan plan = repository.findById(id)
                .orElseThrow(() -> new TreatmentPlanNotFoundApplicationException("TreatmentPlan with id " + id.value() + " not found"));

        EncounterId encounterId = new EncounterId(java.util.UUID.fromString(command.encounterId()));
        ProfessionalId professionalId = new ProfessionalId(java.util.UUID.fromString(command.professionalId()));
        TreatmentStatusId treatmentStatusId = new TreatmentStatusId(java.util.UUID.fromString(command.treatmentStatusId()));

        plan.update(encounterId, professionalId, command.title(), command.description(),
                java.time.LocalDate.parse(command.startDate()), java.time.LocalDate.parse(command.endDate()), treatmentStatusId);

        repository.save(plan);

        return TreatmentPlanResponse.from(plan);
    }
}
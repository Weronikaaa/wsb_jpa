package com.jpacourse.mapper;

import com.jpacourse.dto.MedicalTreatmentTO;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;

public final class MedicalTreatmentMapper {

    public static MedicalTreatmentTO mapToTO(final MedicalTreatmentEntity treatmentEntity) {
        if (treatmentEntity == null) {
            return null;
        }
        
        final MedicalTreatmentTO treatmentTO = new MedicalTreatmentTO();
        treatmentTO.setId(treatmentEntity.getId());
        treatmentTO.setDescription(treatmentEntity.getDescription());
        treatmentTO.setType(treatmentEntity.getType());
        
        if (treatmentEntity.getVisit() != null) {
            treatmentTO.setVisitId(treatmentEntity.getVisit().getId());
        }
        
        return treatmentTO;
    }

    public static MedicalTreatmentEntity mapToEntity(final MedicalTreatmentTO treatmentTO) {
        if (treatmentTO == null) {
            return null;
        }
        
        final MedicalTreatmentEntity treatmentEntity = new MedicalTreatmentEntity();
        treatmentEntity.setId(treatmentTO.getId());
        treatmentEntity.setDescription(treatmentTO.getDescription());
        treatmentEntity.setType(treatmentTO.getType());
        
        return treatmentEntity;
    }
}
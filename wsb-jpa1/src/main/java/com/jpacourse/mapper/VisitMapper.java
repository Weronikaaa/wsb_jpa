package com.jpacourse.mapper;

import java.util.stream.Collectors;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.VisitEntity;

public final class VisitMapper {

    public static VisitTO mapToTO(final VisitEntity visitEntity) {
        if (visitEntity == null) {
            return null;
        }
        
        final VisitTO visitTO = new VisitTO();
        visitTO.setId(visitEntity.getId());
        visitTO.setDescription(visitEntity.getDescription());
        visitTO.setTime(visitEntity.getTime());
        
        // Mapowanie relacji przez ID
        if (visitEntity.getDoctor() != null) {
            visitTO.setDoctorId(visitEntity.getDoctor().getId());
        }
        
        if (visitEntity.getPatient() != null) {
            visitTO.setPatientId(visitEntity.getPatient().getId());
        }
        
        // Mapowanie listy zabiegów
        if (visitEntity.getTreatments() != null) {
            visitTO.setTreatmentIds(
                visitEntity.getTreatments().stream()
                    .map(treatment -> treatment.getId())
                    .collect(Collectors.toList())
            );
        }
        
        return visitTO;
    }

    public static VisitEntity mapToEntity(final VisitTO visitTO) {
        if (visitTO == null) {
            return null;
        }
        
        final VisitEntity visitEntity = new VisitEntity();
        visitEntity.setId(visitTO.getId());
        visitEntity.setDescription(visitTO.getDescription());
        visitEntity.setTime(visitTO.getTime());
        
        return visitEntity;
    }
}
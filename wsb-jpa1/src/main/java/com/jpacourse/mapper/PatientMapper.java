package com.jpacourse.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.PatientEntity;

public final class PatientMapper {

    public static PatientTO mapToTO(final PatientEntity patientEntity) {
        if (patientEntity == null) {
            return null;
        }
        
        List<VisitTO> visits = patientEntity.getVisits() != null ?
        patientEntity.getVisits().stream()
            .map(VisitMapper::mapToTO)
            .collect(Collectors.toList()) :
        new ArrayList<>();
    
    return new PatientTO(
        patientEntity.getId(),
        patientEntity.getFirstName(),
        patientEntity.getLastName(),
        patientEntity.getTelephoneNumber(),
        patientEntity.getEmail(),
        patientEntity.getPatientNumber(),
        patientEntity.getDateOfBirth(),
        patientEntity.getRegistrationDate(),
        visits
    );
}

public static PatientEntity mapToEntity(final PatientTO patientTO) {
    if (patientTO == null) {
        return null;
    }
    
    final PatientEntity patientEntity = new PatientEntity();
    patientEntity.setId(patientTO.getId());
    patientEntity.setFirstName(patientTO.getFirstName());
    patientEntity.setLastName(patientTO.getLastName());
    patientEntity.setTelephoneNumber(patientTO.getTelephoneNumber());
    patientEntity.setEmail(patientTO.getEmail());
    patientEntity.setPatientNumber(patientTO.getPatientNumber());
    patientEntity.setDateOfBirth(patientTO.getDateOfBirth());
    
    return patientEntity;
}
}
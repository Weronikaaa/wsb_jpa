package com.jpacourse.mapper;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistance.entity.PatientEntity;

public class PatientMapper {

    public static PatientTO mapToTO(final PatientEntity patientEntity)
    {
        if (patientEntity == null) return null;
        final PatientTO patientTO = new PatientTO();
        patientTO.setId(patientEntity.getId());
        patientTO.setFirstName(patientEntity.getFirstName());
        patientTO.setLastName(patientEntity.getLastName());
        patientTO.setTelephoneNumber(patientEntity.getTelephoneNumber());
        patientTO.setEmail(patientEntity.getEmail());
        patientTO.setPatientNumber(patientEntity.getPatientNumber());
        patientTO.setDateOfBirth(patientEntity.getDateOfBirth());
        patientTO.setVisits(VisitMapper.mapToTOs(patientEntity.getVisits()));
        return patientTO;
    }
}
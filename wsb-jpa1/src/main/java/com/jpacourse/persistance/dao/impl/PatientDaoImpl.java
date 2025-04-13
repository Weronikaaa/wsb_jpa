package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.rest.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @Autowired
    private DoctorDao doctorDao;

    @Override
    public void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription){
        PatientEntity patient = findOne(patientId);
        DoctorEntity doctor = doctorDao.findOne(doctorId);

        if(patient != null && doctor != null){
            VisitEntity visit = new VisitEntity();
            visit.setDoctor(doctor);
            visit.setPatient(patient);
            visit.setTime(visitDate);
            visit.setDescription(visitDescription);

            patient.getVisits().add(visit);

            update(patient);
        } else {
            throw new EntityNotFoundException(patientId);
        }
    }
}



package com.jpacourse.service.impl;

import com.jpacourse.mapper.VisitMapper;
import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.rest.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.service.PatientService;

import java.util.List;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientDao patientDao;
    private final VisitDao visitDao;


    @Autowired
    public PatientServiceImpl(PatientDao patientDao, VisitDao visitDao) { this.patientDao = patientDao;this.visitDao = visitDao; }


    @Override
    public PatientTO findPatientById(Long id) {
        final PatientEntity entity = patientDao.findOne(id);
        return PatientMapper.mapToTO(entity);
    }


    @Override
    public void deletePatient(Long id) {
        PatientEntity patient = patientDao.findOne(id);
        if (patient == null) {
            throw new EntityNotFoundException(id);
        }
        patientDao.delete(patient); // To powinno wywołać kaskadowe usunięcie wizyt
    }

    @Override
    public List<VisitTO> getVisitsByPatientId(long patientId) {
        return visitDao.findByPatientId(patientId)
                .stream()
                .map(VisitMapper::mapToTO)
                .toList();
    }
}
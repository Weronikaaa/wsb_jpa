package com.jpacourse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpacourse.dto.MedicalTreatmentTO;
import com.jpacourse.mapper.MedicalTreatmentMapper;
import com.jpacourse.persistance.dao.MedicalTreatmentDao;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import com.jpacourse.service.MedicalTreatmentService;


@Service
@Transactional
public class MedicalTreatmentServiceImpl implements MedicalTreatmentService
{
    private final MedicalTreatmentDao medicalTreatmentDao;

    @Autowired
    public MedicalTreatmentServiceImpl(MedicalTreatmentDao pMedicalTreatmentDao)
    {
        medicalTreatmentDao = pMedicalTreatmentDao;
    }


    @Override
    public MedicalTreatmentTO findById(Long id) {
        final MedicalTreatmentEntity entity = medicalTreatmentDao.findOne(id);
        return MedicalTreatmentMapper.mapToTO(entity);
    }
}

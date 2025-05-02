package com.jpacourse.service;

import com.jpacourse.dto.DoctorTO;
import com.jpacourse.persistance.entity.DoctorEntity;

public interface DoctorService
{
    DoctorTO findDoctorById(final Long id);
}
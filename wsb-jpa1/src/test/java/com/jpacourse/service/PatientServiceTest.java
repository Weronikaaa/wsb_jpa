package com.jpacourse.service;
;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;

import com.jpacourse.rest.exception.EntityNotFoundException;
import com.jpacourse.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
//import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientDao patientDao;

    @Mock
    private VisitDao visitDao;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void shouldFindAllVisitsByPatientId() {
        // given
        Long patientId = 1L;
        PatientEntity patient = new PatientEntity();
        patient.setId(patientId);

        VisitEntity visit1 = new VisitEntity();
        visit1.setId(1L);
        visit1.setTime(LocalDateTime.now());

        VisitEntity visit2 = new VisitEntity();
        visit2.setId(2L);
        visit2.setTime(LocalDateTime.now().minusDays(1));

        when(patientDao.findOne(patientId)).thenReturn(patient);
        when(visitDao.findByPatientId(patientId)).thenReturn(List.of(visit1, visit2));

        // when
        List<VisitTO> result = patientService.findVisitsByPatientId(patientId);

        // then
        assertThat(result)
                .hasSize(2)
                .extracting(VisitTO::getId)
                .containsExactly(1L, 2L);
    }

    @Test
    void shouldThrowExceptionWhenPatientNotFound() {
        // given
        Long patientId = 99L;
        when(patientDao.findOne(patientId)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> patientService.findVisitsByPatientId(patientId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Patient not found with id: " + patientId);
    }
}
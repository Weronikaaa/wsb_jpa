package com.jpacourse.persistance.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.jpacourse.persistance.entity.PatientEntity;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Transactional
    @Rollback
    @Test
    public void testShouldFindPatientById() {
        // given
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Liam");
        patient.setLastName("Davis");
        patient.setTelephoneNumber("555123456");
        patient.setDateOfBirth(LocalDate.of(1980, 1, 1));
        PatientEntity savedPatient = patientDao.save(patient);

        // when
        PatientEntity patientEntity = patientDao.findOne(savedPatient.getId());

        // then
        assertThat(patientEntity).isNotNull();
        assertThat(patientEntity.getFirstName()).isEqualTo("Liam");
        assertThat(patientEntity.getLastName()).isEqualTo("Davis");
        assertThat(patientEntity.getTelephoneNumber()).isEqualTo("555123456");
        assertThat(patientEntity.getDateOfBirth()).isEqualTo((LocalDate.of(1980, 1, 1)));

    }

    @Transactional
    @Rollback
    @Test
    public void testShouldFindPatientByLastName() {
        // given
        String commonLastName = "Smith";

        PatientEntity patient1 = new PatientEntity();
        patient1.setFirstName("John");
        patient1.setLastName(commonLastName);
        patient1.setTelephoneNumber("555111222");
        patient1.setDateOfBirth(LocalDate.of(1985, 5, 15));
        patientDao.save(patient1);

        // when
        List<PatientEntity> foundPatients = patientDao.findByLastName(commonLastName);
        // then
        assertThat(foundPatients)
                .hasSize(1)
                .extracting(PatientEntity::getLastName)
                .containsOnly(commonLastName);

        assertThat(foundPatients)
                .extracting(PatientEntity::getFirstName)
                .containsExactlyInAnyOrder("John");

    }

}
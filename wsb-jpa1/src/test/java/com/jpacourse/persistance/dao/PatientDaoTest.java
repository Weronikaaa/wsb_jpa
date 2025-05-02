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

//    @Transactional
//    @Rollback
//    @Test
//    public void testShouldSaveAddress() {
//        // given
//        AddressEntity addressEntity = new AddressEntity();
//        addressEntity.setAddressLine1("line1");
//        addressEntity.setAddressLine2("line2");
//        addressEntity.setCity("City1");
//        addressEntity.setPostalCode("66-666");
//        long entitiesNumBefore = addressDao.count();
//
//        // when
//        final AddressEntity saved = addressDao.save(addressEntity);
//
//        // then
//        assertThat(saved).isNotNull();
//        assertThat(saved.getId()).isNotNull();
//        assertThat(addressDao.count()).isEqualTo(entitiesNumBefore + 1);
//    }
//
//    @Transactional
//    @Rollback
//    @Test
//    public void testShouldSaveAndRemoveAddress() {
//        // given
//        AddressEntity addressEntity = new AddressEntity();
//        addressEntity.setAddressLine1("line1");
//        addressEntity.setAddressLine2("line2");
//        addressEntity.setCity("City1");
//        addressEntity.setPostalCode("66-666");
//
//        // when
//        final AddressEntity saved = addressDao.save(addressEntity);
//        assertThat(saved.getId()).isNotNull();
//        final AddressEntity newSaved = addressDao.findOne(saved.getId());
//        assertThat(newSaved).isNotNull();
//
//        addressDao.delete(saved.getId());
//
//        // then
//        final AddressEntity removed = addressDao.findOne(saved.getId());
//        assertThat(removed).isNull();
//    }
}
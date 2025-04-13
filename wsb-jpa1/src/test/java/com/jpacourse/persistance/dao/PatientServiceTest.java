package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Test
    public void testShouldSavePatientWithAddress() {
        // given
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirstName("Pam");
        patientEntity.setLastName("Beesly");
        patientEntity.setTelephoneNumber("123456789");
        patientEntity.setEmail("pam.beesly@dundermifflin.com");
        patientEntity.setPatientNumber("P12345");
        patientEntity.setDateOfBirth(LocalDate.of(1990, 5, 10));

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("456 Scranton Ave");
        addressEntity.setCity("Scranton");
        addressEntity.setPostalCode("18503");

        // powiązanie pacjenta z adresem
        patientEntity.setAddress(addressEntity);

        // when
        PatientEntity savedPatient = patientDao.save(patientEntity);

        // then
        assertThat(savedPatient.getId()).isNotNull();
        assertThat(savedPatient.getAddress().getId()).isNotNull();
        assertThat(savedPatient.getFirstName()).isEqualTo("Pam");
        assertThat(savedPatient.getAddress().getAddressLine1()).isEqualTo("456 Scranton Ave");
    }
}
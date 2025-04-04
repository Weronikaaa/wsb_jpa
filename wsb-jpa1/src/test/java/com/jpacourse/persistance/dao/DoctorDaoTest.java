package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.enums.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class DoctorDaoTest
{

    @Autowired
    private DoctorDao doctorDao;
    @Test
    public void testShouldSaveCascade() {
        // given
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setFirstName("John");
        doctorEntity.setSpecialization(Specialization.GP);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("123 Main St");

        //doctorEntity.setAddressEntity(addressEntity); ///blad???

        // when
        doctorDao.save(doctorEntity);

        // then
        assertThat(doctorEntity.getId()).isNotNull();
        assertThat(addressEntity.getId()).isNotNull();
    }
}

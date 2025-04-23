package com.jpacourse.persistance.dao.impl;

import org.springframework.stereotype.Repository;

import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.persistance.entity.VisitEntity;

@Repository
public class VisitDaoImpl extends AbstractDao<VisitEntity, Long> implements VisitDao
{

}
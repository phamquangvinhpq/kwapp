package com.ttcsolutions.kwapp.core.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

public class InsertUpdateRepositoryImpl<T> implements InsertUpdateRepository<T> {
  @Autowired
  private EntityManager entityManager;

  @Transactional
  @Override
  public <S extends T> S insert(S entity) {
    entityManager.persist(entity);
    return entity;
  }

  @Transactional
  @Override
  public <S extends T> S update(S entity) {
    return entityManager.merge(entity);
  }

}

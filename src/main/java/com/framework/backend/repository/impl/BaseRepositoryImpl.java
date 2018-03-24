package com.framework.backend.repository.impl;

import com.framework.backend.entities.BaseEntity;
import com.framework.backend.repository.core.BaseRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class BaseRepositoryImpl<T extends BaseEntity> extends SimpleJpaRepository<T, Integer> implements BaseRepository<T> {

    private EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }
}

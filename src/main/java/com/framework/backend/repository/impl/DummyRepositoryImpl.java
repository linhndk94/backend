package com.framework.backend.repository.impl;

import com.framework.backend.entities.Dummy;
import com.framework.backend.repository.core.DummyRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.persistence.EntityManager;

public class DummyRepositoryImpl extends BaseRepositoryImpl<Dummy> implements DummyRepository {

    public DummyRepositoryImpl(JpaEntityInformation<Dummy, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public Dummy getByUsername(String username) {
        Dummy dummy = new Dummy();
        dummy.setUsername(username);
        return findOne(Example.of(dummy)).isPresent() ? findOne(Example.of(dummy)).get() : null;
    }
}

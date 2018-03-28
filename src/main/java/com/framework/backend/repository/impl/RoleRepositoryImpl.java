package com.framework.backend.repository.impl;

import com.framework.backend.entities.Role;
import com.framework.backend.repository.core.RoleRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.persistence.EntityManager;

public class RoleRepositoryImpl extends BaseRepositoryImpl<Role> implements RoleRepository {
    public RoleRepositoryImpl(JpaEntityInformation<Role, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }
}

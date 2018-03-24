package com.framework.backend.service.impl;

import com.framework.backend.entities.BaseEntity;
import com.framework.backend.exception.DataAccessException;
import com.framework.backend.repository.core.BaseRepository;
import com.framework.backend.service.core.BaseService;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    protected abstract Logger getLogger();

    protected abstract BaseRepository<T> getBaseRepository();

    protected void beforeCreate() {
    }

    protected void beforeRetrieve() {
    }

    protected void beforeUpdate() {
    }

    protected void beforeDelete() {
    }

    protected void afterCreate() {
    }

    protected void afterRetrieve() {
    }

    protected void afterUpdate() {
    }

    protected void afterDelete() {
    }


    @Override
    public long count() {
        beforeRetrieve();
        long temp = getBaseRepository().count();
        afterRetrieve();
        return temp;
    }

    @Override
    public <S extends T> S save(S entity) {
        beforeCreate();
        entity.setId(null);
        entity = getBaseRepository().save(entity);
        afterCreate();
        return entity;
    }

    @Override
    public T update(T t) {
        beforeUpdate();
        checkExists(t.getId());
        t = getBaseRepository().save(t);
        afterUpdate();
        return t;
    }

    @Override
    public List<T> findAll() {
        beforeRetrieve();
        List<T> tList = getBaseRepository().findAll();
        afterRetrieve();
        return tList;
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        beforeRetrieve();
        List<S> result = getBaseRepository().findAll(example);
        afterRetrieve();
        return result;
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        beforeRetrieve();
        List<S> result = getBaseRepository().findAll(example, sort);
        afterRetrieve();
        return result;
    }

    @Override
    public List<T> findAll(Sort sort) {
        beforeRetrieve();
        List<T> result = getBaseRepository().findAll(sort);
        afterRetrieve();
        return result;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        beforeRetrieve();
        Page<T> result = getBaseRepository().findAll(pageable);
        afterRetrieve();
        return result;
    }

    @Override
    public List<T> findAllById(Iterable<Integer> ids) {
        beforeRetrieve();
        List<T> result = getBaseRepository().findAllById(ids);
        afterRetrieve();
        return result;
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        beforeCreate();
        List<S> result = getBaseRepository().saveAll(entities);
        afterCreate();
        return result;
    }

    @Override
    public List<T> updateAll(Iterable<T> entities) {
        beforeUpdate();
        checkExists(entities);
        List<T> result = getBaseRepository().saveAll(entities);
        afterUpdate();
        return result;
    }

    @Override
    public void flush() {
        beforeUpdate();
        getBaseRepository().flush();
        afterUpdate();
    }


    @Override
    public <S extends T> S saveAndFlush(S entity) {
        beforeCreate();
        entity.setId(null);
        entity = getBaseRepository().saveAndFlush(entity);
        afterCreate();
        return entity;
    }

    @Override
    public T updateAndFlush(T t) {
        beforeUpdate();
        checkExists(t);
        t = getBaseRepository().saveAndFlush(t);
        afterUpdate();
        return t;
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {
        beforeDelete();
        getBaseRepository().deleteInBatch(entities);
        afterDelete();
    }

    @Override
    public void delete(T t) {
        beforeDelete();
        getBaseRepository().delete(t);
        afterDelete();
    }

    @Override
    public void deleteById(Integer id) {
        beforeDelete();
        getBaseRepository().deleteById(id);
        afterDelete();
    }

    @Override
    public void deleteAllInBatch() {
        beforeDelete();
        getBaseRepository().deleteAllInBatch();
        afterDelete();
    }

    @Override
    public T getOne(Integer id) {
        beforeRetrieve();
        checkExists(id);
        T t = getBaseRepository().getOne(id);
        afterRetrieve();
        return t;
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        beforeRetrieve();
        boolean temp = getBaseRepository().exists(example);
        afterRetrieve();
        return temp;
    }

    @Override
    public boolean existsById(Integer id) {
        beforeRetrieve();
        boolean temp = getBaseRepository().existsById(id);
        afterRetrieve();
        return temp;
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        beforeRetrieve();
        long result = getBaseRepository().count(example);
        afterRetrieve();
        return result;
    }


//    @Override
//    public long count(Specification<T> spec) {
//        return 0;
//    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        beforeRetrieve();
        Optional<S> result = getBaseRepository().findOne(example);
        afterRetrieve();
        return result;
    }

//    @Override
//    public Optional<T> findOne(Specification<T> spec) {
//        return Optional.empty();
//    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        beforeRetrieve();
        Page<S> result = getBaseRepository().findAll(example, pageable);
        afterRetrieve();
        return result;
    }

//    @Override
//    public List<T> findAll(Specification<T> spec) {
//        return null;
//    }
//
//    @Override
//    public List<T> findAll(Specification<T> spec, Sort sort) {
//        return null;
//    }
//
//    @Override
//    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
//        return null;
//    }


    @Override
    public Optional<T> findById(Integer id) {
        beforeRetrieve();
        Optional<T> result = getBaseRepository().findById(id);
        afterRetrieve();
        return result;
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        beforeDelete();
        getBaseRepository().deleteAll(entities);
        afterDelete();
    }

    @Override
    public void deleteAll() {
        beforeDelete();
        getBaseRepository().deleteAll();
        afterDelete();
    }

    private void checkExists(Integer id) {
        if (!existsById(id)) {
            String message = String.format("ID: %s not found", id);
            getLogger().error(message);
            throw DataAccessException.notFound(message);
        }
    }

    private void checkExists(T t) {
        checkExists(t.getId());
    }

    private void checkExists(Iterable<T> entities) {
        List<String> errors = new ArrayList<>();
        for (T t : entities) {
            if (!existsById(t.getId())) errors.add(String.format("ID: %s not found", t.getId()));
        }
        if (!errors.isEmpty()) throw DataAccessException.notFound("One or more element not found", errors);
    }
}

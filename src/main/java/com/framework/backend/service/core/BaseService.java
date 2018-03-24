package com.framework.backend.service.core;

import com.framework.backend.entities.BaseEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity> {

    long count();

    <S extends T> long count(Example<S> example);

//    long count(Specification<T> spec);

    boolean existsById(Integer id);

    <S extends T> boolean exists(Example<S> example);

    T getOne(Integer id);

    Optional<T> findById(Integer id);

    <S extends T> Optional<S> findOne(Example<S> example);

//    Optional<T> findOne(Specification<T> spec);

    List<T> findAll();

    List<T> findAllById(Iterable<Integer> ids);

    List<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);

    <S extends T> List<S> findAll(Example<S> example);

    <S extends T> List<S> findAll(Example<S> example, Sort sort);

    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

//    List<T> findAll(Specification<T> spec);

//    List<T> findAll(Specification<T> spec, Sort sort);

//    Page<T> findAll(Specification<T> spec, Pageable pageable);

    <S extends T> S save(S entity);

    <S extends T> S saveAndFlush(S entity);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    T update(T t);

    T updateAndFlush(T t);

    List<T> updateAll(Iterable<T> entities);

    void deleteById(Integer id);

    void delete(T entity);

    void deleteAll(Iterable<? extends T> entities);

    void deleteInBatch(Iterable<T> entities);

    void deleteAll();

    void deleteAllInBatch();

    void flush();
}

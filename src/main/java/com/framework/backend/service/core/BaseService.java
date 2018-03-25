package com.framework.backend.service.core;

import com.framework.backend.dto.ExampleDto;
import com.framework.backend.dto.create_dto.BaseCreateDto;
import com.framework.backend.dto.simple_dto.BaseSimpleDto;
import com.framework.backend.entities.BaseEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity, SIMPLE_DTO extends BaseSimpleDto, DETAIL_DTO extends SIMPLE_DTO, CREATE_DTO extends BaseCreateDto> {

    long count();

    <S extends T> long count(ExampleDto<S> exampleDto);

//    long count(Specification<T> spec);

    boolean existsById(Integer id);

    <S extends T> boolean exists(ExampleDto<S> exampleDto);

    DETAIL_DTO getOne(Integer id);

    Optional<DETAIL_DTO> findById(Integer id);

    <S extends T> Optional<DETAIL_DTO> findOne(ExampleDto<S> exampleDto);

//    Optional<T> findOne(Specification<T> spec);

    List<SIMPLE_DTO> findAll();

    List<SIMPLE_DTO> findAllById(Iterable<Integer> ids);

    List<SIMPLE_DTO> findAll(Sort sort);

    Page<SIMPLE_DTO> findAll(Pageable pageable);

    <S extends T> List<SIMPLE_DTO> findAll(ExampleDto<S> exampleDto);

    <S extends T> List<SIMPLE_DTO> findAll(ExampleDto<S> exampleDto, Sort sort);

    <S extends T> Page<SIMPLE_DTO> findAll(ExampleDto<S> exampleDto, Pageable pageable);

//    List<T> findAll(Specification<T> spec);

//    List<T> findAll(Specification<T> spec, Sort sort);

//    Page<T> findAll(Specification<T> spec, Pageable pageable);

    SIMPLE_DTO save(CREATE_DTO create_dto);

    SIMPLE_DTO saveAndFlush(CREATE_DTO create_dto);

    List<SIMPLE_DTO> saveAll(Iterable<CREATE_DTO> dtos);

    SIMPLE_DTO update(CREATE_DTO create_dto);

    SIMPLE_DTO updateAndFlush(CREATE_DTO create_dto);

    List<SIMPLE_DTO> updateAll(Iterable<CREATE_DTO> entities);

    void deleteById(Integer id);

    void delete(SIMPLE_DTO simple_dto);

    void deleteAll(Iterable<SIMPLE_DTO> dtos);

    void deleteInBatch(Iterable<SIMPLE_DTO> dtos);

    void deleteAll();

    void deleteAllInBatch();

    void flush();
}

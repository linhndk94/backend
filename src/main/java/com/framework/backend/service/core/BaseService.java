package com.framework.backend.service.core;

import com.framework.backend.dto.create_dto.BaseCreateDto;
import com.framework.backend.dto.simple_dto.BaseSimpleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface BaseService<SIMPLE_DTO extends BaseSimpleDto, DETAIL_DTO extends SIMPLE_DTO, CREATE_DTO extends BaseCreateDto> {

    long count();

    boolean existsById(Long id);

    DETAIL_DTO getOne(Long id);

    Optional<DETAIL_DTO> findById(Long id);

    List<SIMPLE_DTO> findAll();

    List<SIMPLE_DTO> findAllById(Iterable<Long> ids);

    List<SIMPLE_DTO> findAll(Sort sort);

    Page<SIMPLE_DTO> findAll(Pageable pageable);

    SIMPLE_DTO save(CREATE_DTO create_dto);

    SIMPLE_DTO saveAndFlush(CREATE_DTO create_dto);

    List<SIMPLE_DTO> saveAll(Iterable<CREATE_DTO> dtos);

    SIMPLE_DTO update(CREATE_DTO create_dto);

    SIMPLE_DTO updateAndFlush(CREATE_DTO create_dto);

    List<SIMPLE_DTO> updateAll(Iterable<CREATE_DTO> entities);

    void deleteById(Long id);

    void deleteAll(Iterable<Long> ids);

    void deleteInBatch(Iterable<Long> ids);

    void deleteAll();

    void deleteAllInBatch();

    void flush();
}

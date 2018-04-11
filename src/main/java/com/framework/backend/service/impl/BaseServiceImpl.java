package com.framework.backend.service.impl;

import com.framework.backend.dto.create_dto.BaseCreateDto;
import com.framework.backend.dto.simple_dto.BaseSimpleDto;
import com.framework.backend.entities.BaseEntity;
import com.framework.backend.exception.DataAccessException;
import com.framework.backend.repository.core.BaseRepository;
import com.framework.backend.service.core.BaseService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class BaseServiceImpl<T extends BaseEntity, SIMPLE_DTO extends BaseSimpleDto, DETAIL_DTO extends SIMPLE_DTO, CREATE_DTO extends BaseCreateDto> implements BaseService<SIMPLE_DTO, DETAIL_DTO, CREATE_DTO> {

    private BaseRepository<T> baseRepository;

    BaseServiceImpl(BaseRepository<T> baseRepository) {
        this.baseRepository = baseRepository;
    }

    protected abstract Logger getLogger();

    protected abstract T createEntity(CREATE_DTO create_dto);

    protected abstract T createEntity(Long id);

    protected abstract SIMPLE_DTO createSimpleDto(T entity);

    protected abstract DETAIL_DTO createDetailDto(T entity);

    protected void beforeCreate(CREATE_DTO create_dto) {
        create_dto.setId(null);
    }

    protected void beforeCreate(Iterable<CREATE_DTO> dtos) {
        StreamSupport.stream(dtos.spliterator(), false).forEach(create_dto -> create_dto.setId(null));
    }

    protected void beforeUpdate() {
    }

    protected void beforeUpdate(CREATE_DTO create_dto) {
    }

    protected void beforeUpdate(Iterable<CREATE_DTO> dtos) {
    }

    protected void beforeRetrieve() {
    }

    protected void beforeRetrieve(Long id) {
    }

    protected void beforeRetrieve(Iterable<Long> ids) {
    }

    protected void beforeDelete() {
    }

    protected void beforeDelete(Long id) {
    }

    protected void beforeDelete(Iterable<Long> ids) {
    }

    protected void afterCreate(SIMPLE_DTO simple_dto) {
    }

    protected void afterCreate(List<SIMPLE_DTO> dtos) {
    }

    protected void afterRetrieve() {
    }

    protected void afterRetrieve(SIMPLE_DTO simple_dto) {
    }

    protected void afterRetrieve(List<SIMPLE_DTO> dtos) {
    }

    protected void afterRetrieve(Page<SIMPLE_DTO> dtos) {
    }

    protected void afterUpdate() {
    }

    protected void afterUpdate(SIMPLE_DTO simple_dto) {
    }

    protected void afterUpdate(List<SIMPLE_DTO> dtos) {
    }

    protected void afterDelete() {
    }

    protected void copyPojo(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }


    @Override
    public long count() {
        beforeRetrieve();
        long temp = baseRepository.count();
        afterRetrieve();
        return temp;
    }

    @Override
    public SIMPLE_DTO save(CREATE_DTO create_dto) {
        beforeCreate(create_dto);
        T entity = createEntity(create_dto);
        entity.setId(null);
        SIMPLE_DTO result = createSimpleDto(baseRepository.save(entity));
        afterCreate(result);
        return result;
    }

    @Override
    public SIMPLE_DTO update(CREATE_DTO create_dto) {
        beforeUpdate(create_dto);
        checkExists(create_dto.getId());
        SIMPLE_DTO result = createSimpleDto(baseRepository.save(createEntity(create_dto)));
        afterUpdate(result);
        return result;
    }

    @Override
    public List<SIMPLE_DTO> findAll() {
        beforeRetrieve();
        List<SIMPLE_DTO> result = baseRepository.findAll().stream().map(this::createSimpleDto).collect(Collectors.toList());
        afterRetrieve(result);
        return result;
    }

    @Override
    public List<SIMPLE_DTO> findAll(Sort sort) {
        beforeRetrieve();
        List<SIMPLE_DTO> result = baseRepository.findAll(sort).stream().map(this::createSimpleDto).collect(Collectors.toList());
        afterRetrieve(result);
        return result;
    }

    @Override
    public Page<SIMPLE_DTO> findAll(Pageable pageable) {
        beforeRetrieve();
        Page<SIMPLE_DTO> result = baseRepository.findAll(pageable).map(this::createSimpleDto);
        afterRetrieve(result);
        return result;
    }

    @Override
    public List<SIMPLE_DTO> findAllById(Iterable<Long> ids) {
        beforeRetrieve(ids);
        List<SIMPLE_DTO> result = baseRepository.findAllById(ids).stream().map(this::createSimpleDto).collect(Collectors.toList());
        afterRetrieve(result);
        return result;
    }

    @Override
    public List<SIMPLE_DTO> saveAll(Iterable<CREATE_DTO> dtos) {
        beforeCreate(dtos);
        List<SIMPLE_DTO> result = baseRepository.saveAll(StreamSupport.stream(dtos.spliterator(), false).map(this::createEntity).collect(Collectors.toList())).stream().map(this::createSimpleDto).collect(Collectors.toList());
        afterCreate(result);
        return result;
    }

    @Override
    public List<SIMPLE_DTO> updateAll(Iterable<CREATE_DTO> dtos) {
        beforeUpdate(dtos);
        checkExists(dtos);
        List<SIMPLE_DTO> result = baseRepository.saveAll(StreamSupport.stream(dtos.spliterator(), false).map(this::createEntity).collect(Collectors.toList())).stream().map(this::createSimpleDto).collect(Collectors.toList());
        afterUpdate(result);
        return result;
    }

    @Override
    public void flush() {
        beforeUpdate();
        baseRepository.flush();
        afterUpdate();
    }


    @Override
    public SIMPLE_DTO saveAndFlush(CREATE_DTO dto) {
        beforeCreate(dto);
        SIMPLE_DTO result = createSimpleDto(baseRepository.saveAndFlush(createEntity(dto)));
        afterCreate(result);
        return result;
    }

    @Override
    public SIMPLE_DTO updateAndFlush(CREATE_DTO dto) {
        beforeUpdate(dto);
        checkExists(dto);
        SIMPLE_DTO result = createSimpleDto(baseRepository.saveAndFlush(createEntity(dto)));
        afterUpdate(result);
        return result;
    }

    @Override
    public void deleteInBatch(Iterable<Long> ids) {
        beforeDelete(ids);
        baseRepository.deleteInBatch(StreamSupport.stream(ids.spliterator(), false).map(this::createEntity).collect(Collectors.toList()));
        afterDelete();
    }

    @Override
    public void deleteById(Long id) {
        beforeDelete(id);
        baseRepository.deleteById(id);
        afterDelete();
    }

    @Override
    public void deleteAllInBatch() {
        beforeDelete();
        baseRepository.deleteAllInBatch();
        afterDelete();
    }

    @Override
    public DETAIL_DTO getOne(Long id) {
        beforeRetrieve(id);
        checkExists(id);
        DETAIL_DTO result = createDetailDto(baseRepository.getOne(id));
        afterRetrieve(result);
        return result;
    }

    @Override
    public boolean existsById(Long id) {
        beforeRetrieve(id);
        boolean temp = baseRepository.existsById(id);
        afterRetrieve();
        return temp;
    }

    @Override
    public Optional<DETAIL_DTO> findById(Long id) {
        beforeRetrieve();
        Optional<DETAIL_DTO> result = baseRepository.findById(id).map(this::createDetailDto);
        afterRetrieve();
        return result;
    }

    @Override
    public void deleteAll(Iterable<Long> ids) {
        beforeDelete();
        baseRepository.deleteAll(StreamSupport.stream(ids.spliterator(), false).map(this::createEntity).collect(Collectors.toList()));
        afterDelete();
    }

    @Override
    public void deleteAll() {
        beforeDelete();
        baseRepository.deleteAll();
        afterDelete();
    }

    private void checkExists(Long id) {
        if (!existsById(id)) {
            String message = String.format("ID: %s not found", id);
            getLogger().error(message);
            throw DataAccessException.notFound(message);
        }
    }

    private void checkExists(CREATE_DTO create_dto) {
        checkExists(create_dto.getId());
    }

    private void checkExists(Iterable<CREATE_DTO> dtos) {
        List<String> errors = new ArrayList<>();
        StreamSupport.stream(dtos.spliterator(), false).forEach(create_dto -> {
            if (!existsById(create_dto.getId())) {
                errors.add(String.format("ID: %s not found", create_dto.getId()));
            }
        });
        if (!errors.isEmpty()) throw DataAccessException.notFound("One or more element not found", errors);
    }
}

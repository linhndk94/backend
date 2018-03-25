package com.framework.backend.service.impl;

import com.framework.backend.dto.ExampleDto;
import com.framework.backend.dto.create_dto.BaseCreateDto;
import com.framework.backend.dto.simple_dto.BaseSimpleDto;
import com.framework.backend.entities.BaseEntity;
import com.framework.backend.exception.DataAccessException;
import com.framework.backend.repository.core.BaseRepository;
import com.framework.backend.service.core.BaseService;
import com.framework.backend.support.ExampleFactory;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class BaseServiceImpl<T extends BaseEntity, SIMPLE_DTO extends BaseSimpleDto, DETAIL_DTO extends SIMPLE_DTO, CREATE_DTO extends BaseCreateDto> implements BaseService<T, SIMPLE_DTO, DETAIL_DTO, CREATE_DTO> {

    protected abstract Logger getLogger();

    protected abstract BaseRepository<T> getBaseRepository();

    protected abstract T createEntity(CREATE_DTO create_dto);

    protected abstract T createEntity(SIMPLE_DTO create_dto);

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

    protected void beforeRetrieve(Integer id) {
    }

    protected void beforeRetrieve(Iterable<Integer> ids) {
    }

    protected void beforeRetrieve(ExampleDto exampleDto) {
    }


    protected void beforeDelete() {
    }

    protected void beforeDelete(Integer id) {
    }

    protected void beforeDelete(SIMPLE_DTO simple_dto) {
    }

    protected void beforeDelete(Iterable<SIMPLE_DTO> dtos) {
    }

    protected void afterCreate() {
    }

    protected void afterRetrieve() {
    }

    protected void afterUpdate() {
    }

    protected void afterDelete() {
    }

    protected void copyPojo(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }


    @Override
    public long count() {
        beforeRetrieve();
        long temp = getBaseRepository().count();
        afterRetrieve();
        return temp;
    }

    @Override
    public SIMPLE_DTO save(CREATE_DTO create_dto) {
        beforeCreate(create_dto);
        T entity = createEntity(create_dto);
        entity.setId(null);
        SIMPLE_DTO result = createSimpleDto(getBaseRepository().save(entity));
        afterCreate();
        return result;
    }

    @Override
    public SIMPLE_DTO update(CREATE_DTO create_dto) {
        beforeUpdate(create_dto);
        checkExists(create_dto.getId());
        SIMPLE_DTO result = createSimpleDto(getBaseRepository().save(createEntity(create_dto)));
        afterUpdate();
        return result;
    }

    @Override
    public List<SIMPLE_DTO> findAll() {
        beforeRetrieve();
        List<SIMPLE_DTO> result = getBaseRepository().findAll().stream().map(this::createSimpleDto).collect(Collectors.toList());
        afterRetrieve();
        return result;
    }

    @Override
    public <S extends T> List<SIMPLE_DTO> findAll(ExampleDto<S> exampleDto) {
        beforeRetrieve(exampleDto);
        List<SIMPLE_DTO> result = getBaseRepository().findAll(ExampleFactory.createExample(exampleDto)).stream().map(this::createSimpleDto).collect(Collectors.toList());
        afterRetrieve();
        return result;
    }

    @Override
    public <S extends T> List<SIMPLE_DTO> findAll(ExampleDto<S> exampleDto, Sort sort) {
        beforeRetrieve(exampleDto);
        List<SIMPLE_DTO> result = getBaseRepository().findAll(ExampleFactory.createExample(exampleDto), sort).stream().map(this::createSimpleDto).collect(Collectors.toList());
        afterRetrieve();
        return result;
    }

    @Override
    public List<SIMPLE_DTO> findAll(Sort sort) {
        beforeRetrieve();
        List<SIMPLE_DTO> result = getBaseRepository().findAll(sort).stream().map(this::createSimpleDto).collect(Collectors.toList());
        afterRetrieve();
        return result;
    }

    @Override
    public Page<SIMPLE_DTO> findAll(Pageable pageable) {
        beforeRetrieve();
        Page<SIMPLE_DTO> result = getBaseRepository().findAll(pageable).map(this::createSimpleDto);
        afterRetrieve();
        return result;
    }

    @Override
    public List<SIMPLE_DTO> findAllById(Iterable<Integer> ids) {
        beforeRetrieve(ids);
        List<SIMPLE_DTO> result = getBaseRepository().findAllById(ids).stream().map(this::createSimpleDto).collect(Collectors.toList());
        afterRetrieve();
        return result;
    }

    @Override
    public List<SIMPLE_DTO> saveAll(Iterable<CREATE_DTO> dtos) {
        beforeCreate(dtos);
        List<SIMPLE_DTO> result = getBaseRepository().saveAll(StreamSupport.stream(dtos.spliterator(), false).map(this::createEntity).collect(Collectors.toList())).stream().map(this::createSimpleDto).collect(Collectors.toList());
        afterCreate();
        return result;
    }

    @Override
    public List<SIMPLE_DTO> updateAll(Iterable<CREATE_DTO> dtos) {
        beforeUpdate(dtos);
        checkExists(dtos);
        List<SIMPLE_DTO> result = getBaseRepository().saveAll(StreamSupport.stream(dtos.spliterator(), false).map(this::createEntity).collect(Collectors.toList())).stream().map(this::createSimpleDto).collect(Collectors.toList());
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
    public SIMPLE_DTO saveAndFlush(CREATE_DTO dto) {
        beforeCreate(dto);
        SIMPLE_DTO result = createSimpleDto(getBaseRepository().saveAndFlush(createEntity(dto)));
        afterCreate();
        return result;
    }

    @Override
    public SIMPLE_DTO updateAndFlush(CREATE_DTO dto) {
        beforeUpdate(dto);
        checkExists(dto);
        SIMPLE_DTO result = createSimpleDto(getBaseRepository().saveAndFlush(createEntity(dto)));
        afterUpdate();
        return result;
    }

    @Override
    public void deleteInBatch(Iterable<SIMPLE_DTO> dtos) {
        beforeDelete(dtos);
        getBaseRepository().deleteInBatch(StreamSupport.stream(dtos.spliterator(), false).map(this::createEntity).collect(Collectors.toList()));
        afterDelete();
    }

    @Override
    public void delete(SIMPLE_DTO simple_dto) {
        beforeDelete(simple_dto);
        getBaseRepository().delete(createEntity(simple_dto));
        afterDelete();
    }

    @Override
    public void deleteById(Integer id) {
        beforeDelete(id);
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
    public DETAIL_DTO getOne(Integer id) {
        beforeRetrieve(id);
        checkExists(id);
        DETAIL_DTO result = createDetailDto(getBaseRepository().getOne(id));
        afterRetrieve();
        return result;
    }

    @Override
    public <S extends T> boolean exists(ExampleDto<S> exampleDto) {
        beforeRetrieve(exampleDto);
        boolean temp = getBaseRepository().exists(ExampleFactory.createExample(exampleDto));
        afterRetrieve();
        return temp;
    }

    @Override
    public boolean existsById(Integer id) {
        beforeRetrieve(id);
        boolean temp = getBaseRepository().existsById(id);
        afterRetrieve();
        return temp;
    }

    @Override
    public <S extends T> long count(ExampleDto<S> exampleDto) {
        beforeRetrieve();
        long result = getBaseRepository().count(ExampleFactory.createExample(exampleDto));
        afterRetrieve();
        return result;
    }


//    @Override
//    public long count(Specification<T> spec) {
//        return 0;
//    }

    @Override
    public <S extends T> Optional<DETAIL_DTO> findOne(ExampleDto<S> exampleDto) {
        beforeRetrieve();
        Optional<DETAIL_DTO> result = Optional.of(createDetailDto(getBaseRepository().findOne(ExampleFactory.createExample(exampleDto)).get()));
        afterRetrieve();
        return result;
    }

//    @Override
//    public Optional<T> findOne(Specification<T> spec) {
//        return Optional.empty();
//    }

    @Override
    public <S extends T> Page<SIMPLE_DTO> findAll(ExampleDto<S> exampleDto, Pageable pageable) {
        beforeRetrieve();
        Page<SIMPLE_DTO> result = getBaseRepository().findAll(ExampleFactory.createExample(exampleDto), pageable).map(this::createSimpleDto);
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
    public Optional<DETAIL_DTO> findById(Integer id) {
        beforeRetrieve();
        Optional<DETAIL_DTO> result = Optional.of(createDetailDto(getBaseRepository().findById(id).get()));
        afterRetrieve();
        return result;
    }

    @Override
    public void deleteAll(Iterable<SIMPLE_DTO> dtos) {
        beforeDelete();
        getBaseRepository().deleteAll(StreamSupport.stream(dtos.spliterator(), false).map(this::createEntity).collect(Collectors.toList()));
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

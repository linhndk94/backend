package com.framework.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.backend.annotations.CustomRestController;
import com.framework.backend.dto.ExampleDto;
import com.framework.backend.dto.create_dto.BaseCreateDto;
import com.framework.backend.dto.simple_dto.BaseSimpleDto;
import com.framework.backend.entities.BaseEntity;
import com.framework.backend.service.core.BaseService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@CustomRestController
public abstract class BaseController<T extends BaseEntity, SIMPLE_DTO extends BaseSimpleDto, DETAIL_DTO extends SIMPLE_DTO, CREATE_DTO extends BaseCreateDto> {

    @Autowired
    ObjectMapper objectMapper;

    protected abstract BaseService<T, SIMPLE_DTO, DETAIL_DTO, CREATE_DTO> getService();

    protected abstract Logger getLogger();

    @GetMapping(value = "/count")
    public final ResponseEntity<?> count() throws JsonProcessingException {
        return _count(null);
    }

    @PostMapping(value = "/count")
    public final ResponseEntity<?> count(@RequestBody ExampleDto<T> exampleDto) throws JsonProcessingException {
        return _count(exampleDto);
    }

//    long count(Specification<T> spec);

    @GetMapping(value = "/existsById")
    public final ResponseEntity<?> existsById(@RequestParam(value = "id") Integer id) {
        return _existsById(id);
    }

//    <S extends T> boolean exists(Example<S> example);

    @GetMapping(value = "/getOne")
    public final ResponseEntity<?> getOne(@RequestParam(value = "id") Integer id) throws JsonProcessingException {
        return _getOne(id);
    }

    @GetMapping(value = "/findById")
    public final ResponseEntity<?> findById(@RequestParam(value = "id") Integer id) throws JsonProcessingException {
        return _findById(id);
    }

    @PostMapping(value = "/findOne")
    public final ResponseEntity<?> findOne(@RequestBody ExampleDto<T> exampleDto) throws JsonProcessingException {
        return _findOne(exampleDto);
    }

    //    Optional<T> findOne(Specification<T> spec);

    @GetMapping(value = "/findAll")
    public final ResponseEntity<?> findAll(@RequestParam(value = "p", required = false) Integer p,
                                           @RequestParam(value = "s", required = false) Integer s,
                                           @RequestParam(value = "d", required = false) String d,
                                           @RequestParam(value = "prop", required = false) String prop) throws JsonProcessingException {
        return _findAll(p, s, d, prop);
    }

    @PostMapping(value = "/findAllById")
    public final ResponseEntity<?> findAllById(@RequestBody List<Integer> ids) throws JsonProcessingException {
        return _findAllById(ids);
    }

    @PostMapping(value = "/findAll")
    public final ResponseEntity<?> findAll(@RequestBody ExampleDto<T> exampleDto,
                                           @RequestParam(value = "p", required = false) Integer p,
                                           @RequestParam(value = "s", required = false) Integer s,
                                           @RequestParam(value = "d", required = false) String d,
                                           @RequestParam(value = "prop", required = false) String prop) throws JsonProcessingException {
        return _findAll(exampleDto, p, s, d, prop);
    }

//    List<T> findAll(Specification<T> spec);

//    List<T> findAll(Specification<T> spec, Sort sort);

//    Page<T> findAll(Specification<T> spec, Pageable pageable);

    @PostMapping(value = "/save")
    public final ResponseEntity<?> save(@RequestBody CREATE_DTO create_dto, @RequestParam(value = "f", required = false) boolean f) throws JsonProcessingException {
        return _save(create_dto, f);
    }

    @PostMapping(value = "/saveAll")
    public final ResponseEntity<?> saveAll(@RequestBody List<CREATE_DTO> dtos) throws JsonProcessingException {
        return _saveAll(dtos);
    }


    @PostMapping(value = "/update")
    public final ResponseEntity<?> update(@RequestBody CREATE_DTO create_dto, @RequestParam(value = "f", required = false) boolean f) throws JsonProcessingException {
        return _update(create_dto, f);
    }

    @PostMapping(value = "/updateAll")
    public final ResponseEntity<?> updateAll(@RequestBody List<CREATE_DTO> dtos) throws JsonProcessingException {
        return _updateAll(dtos);
    }

    @GetMapping(value = "/delete")
    public final ResponseEntity<?> deleteById(@RequestParam(value = "id") Integer id) {
        return _deleteById(id);
    }

    @PostMapping(value = "/delete")
    public final ResponseEntity<?> delete(@RequestBody SIMPLE_DTO simple_dto) throws JsonProcessingException {
        return _delete(simple_dto);
    }

    @PostMapping(value = "/deleteAll")
    public final ResponseEntity<?> deleteAll(@RequestBody List<SIMPLE_DTO> dtos, @RequestParam(value = "f", required = false) boolean f) throws JsonProcessingException {
        return _deleteAll(dtos, f);
    }

    @GetMapping(value = "/deleteAll")
    public final ResponseEntity<?> deleteAll(@RequestParam(value = "f", required = false) boolean f) {
        return _deleteAll(f);
    }

    @GetMapping(value = "/flush")
    public final ResponseEntity<?> flush() {
        return _flush();
    }

    protected ResponseEntity<?> _count(ExampleDto<T> exampleDto) throws JsonProcessingException {
        getLogger().debug("Begin count");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(exampleDto));
        long result = exampleDto != null ? getService().count(exampleDto) : getService().count();
        getLogger().debug("Result: {}", result);
        return ResponseEntity.ok(result);
    }

    protected ResponseEntity<?> _save(CREATE_DTO create_dto, boolean f) throws JsonProcessingException {
        getLogger().debug("Begin saving");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(create_dto));
        getLogger().debug("Param: f={}", f);
        SIMPLE_DTO result = f ? getService().saveAndFlush(create_dto) : getService().save(create_dto);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    protected ResponseEntity<?> _saveAll(List<CREATE_DTO> dtos) throws JsonProcessingException {
        getLogger().debug("Begin saving");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(dtos));
        List<SIMPLE_DTO> result = getService().saveAll(dtos);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    protected ResponseEntity<?> _update(CREATE_DTO create_dto, boolean f) throws JsonProcessingException {
        getLogger().debug("Begin updating");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(create_dto));
        getLogger().debug("Param: f={}", f);
        SIMPLE_DTO result = f ? getService().updateAndFlush(create_dto) : getService().update(create_dto);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    protected ResponseEntity<?> _updateAll(List<CREATE_DTO> dtos) throws JsonProcessingException {
        getLogger().debug("Begin updating");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(dtos));
        List<SIMPLE_DTO> result = getService().updateAll(dtos);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    protected ResponseEntity<?> _findAll(Integer p, Integer s, String d, String prop) throws JsonProcessingException {
        getLogger().debug("Begin getting all");
        Sort sort = createSort(d, prop);
        PageRequest pageRequest = createPageRequest(p, s, sort);
        Iterable<SIMPLE_DTO> result;
        if (sort == null && pageRequest == null) {
            result = getService().findAll();
        } else if (pageRequest != null) {
            result = getService().findAll(pageRequest);
        } else {
            result = getService().findAll(sort);
        }
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseEntity.ok(result);
    }

    protected ResponseEntity<?> _findAll(ExampleDto<T> exampleDto, Integer p, Integer s, String d, String prop) throws JsonProcessingException {
        getLogger().debug("Begin getting all");
        Sort sort = createSort(d, prop);
        PageRequest pageRequest = createPageRequest(p, s, sort);
        Iterable<SIMPLE_DTO> result;
        if (sort == null && pageRequest == null) {
            result = getService().findAll(exampleDto);
        } else if (pageRequest != null) {
            result = getService().findAll(exampleDto, pageRequest);
        } else {
            result = getService().findAll(exampleDto, sort);
        }
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseEntity.ok(result);
    }

    protected ResponseEntity<?> _findAllById(List<Integer> ids) throws JsonProcessingException {
        getLogger().debug("Begin getting all");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(ids));
        List<SIMPLE_DTO> result = getService().findAllById(ids);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseEntity.ok(result);
    }

    protected ResponseEntity<?> _flush() {
        getLogger().debug("Begin flushing");
        getService().flush();
        return ResponseEntity.ok().build();
    }

    protected ResponseEntity<?> _deleteById(Integer id) {
        getLogger().debug("Begin deleting");
        getLogger().debug("Param id={}", id);
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> _delete(SIMPLE_DTO simple_dto) throws JsonProcessingException {
        getLogger().debug("Begin deleting");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(simple_dto));
        getService().delete(simple_dto);
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> _deleteAll(List<SIMPLE_DTO> dtos, boolean f) throws JsonProcessingException {
        getLogger().debug("Begin deleting");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(dtos));
        getLogger().debug("Param f={}", f);
        if (f) {
            getService().deleteInBatch(dtos);
        } else {
            getService().deleteAll(dtos);
        }
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> _deleteAll(boolean f) {
        getLogger().debug("Begin deleting all");
        getLogger().debug("Param f={}", f);
        if (f) {
            getService().deleteAllInBatch();
        } else {
            getService().deleteAll();
        }
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> _getOne(Integer id) throws JsonProcessingException {
        getLogger().debug("Begin getting one");
        getLogger().debug("Param id={}", id);
        DETAIL_DTO result = getService().getOne(id);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseEntity.ok(result);
    }

    protected ResponseEntity<?> _findById(Integer id) throws JsonProcessingException {
        getLogger().debug("Begin finding by id");
        getLogger().debug("Param id={}", id);
        Optional<DETAIL_DTO> temp = getService().findById(id);
        Object result = temp.isPresent() ? temp.get() : temp;
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseEntity.ok(result);
    }

    protected ResponseEntity<?> _findOne(ExampleDto<T> exampleDto) throws JsonProcessingException {
        getLogger().debug("Begin finding one");
        Optional<DETAIL_DTO> temp = getService().findOne(exampleDto);
        Object result = temp.isPresent() ? temp.get() : temp;
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseEntity.ok(result);
    }

    protected ResponseEntity<?> _existsById(Integer id) {
        getLogger().debug("Begin checking existed one");
        getLogger().debug("Param id={}", id);
        boolean result = getService().existsById(id);
        getLogger().debug("Result: {}", result);
        return ResponseEntity.ok(result);
    }

    private Sort createSort(String d, String prop) throws JsonProcessingException {
        getLogger().debug("Params: d={}, prop={}", d, prop);
        Sort sort = null;
        if (d != null && !d.isEmpty() && prop != null && !prop.isEmpty()) {
            if (d.equalsIgnoreCase("ASC")) {
                sort = new Sort(Sort.Direction.ASC, prop);
            } else if (d.equalsIgnoreCase("DESC")) {
                sort = new Sort(Sort.Direction.DESC, prop);
            }
            getLogger().debug("Sort: {}", objectMapper.writeValueAsString(sort));
        }
        return sort;
    }

    private PageRequest createPageRequest(Integer p, Integer s, Sort sort) throws JsonProcessingException {
        getLogger().debug("Params: d={}, prop={}", p, s);
        PageRequest pageRequest = null;
        if (p != null && s != null) {
            pageRequest = PageRequest.of(p, s, sort);
            getLogger().debug("PageRequest: {}", objectMapper.writeValueAsString(pageRequest));
        }
        return pageRequest;
    }

}

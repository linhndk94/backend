package com.framework.backend.controller.rest_controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.backend.dto.ResponseDto;
import com.framework.backend.dto.create_dto.BaseCreateDto;
import com.framework.backend.dto.simple_dto.BaseSimpleDto;
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

public abstract class BaseController<SIMPLE_DTO extends BaseSimpleDto, DETAIL_DTO extends SIMPLE_DTO, CREATE_DTO extends BaseCreateDto> {

    @Autowired
    private ObjectMapper objectMapper;

    protected abstract BaseService<SIMPLE_DTO, DETAIL_DTO, CREATE_DTO> getService();

    protected abstract Logger getLogger();

    @GetMapping(value = "/count")
    public final ResponseEntity<?> count() {
        return _count();
    }


    @GetMapping(value = "/existsById")
    public final ResponseEntity<?> existsById(@RequestParam(value = "id") Long id) {
        return _existsById(id);
    }


    @GetMapping(value = "/getOne")
    public final ResponseEntity<?> getOne(@RequestParam(value = "id") Long id) throws JsonProcessingException {
        return _getOne(id);
    }

    @GetMapping(value = "/findById")
    public final ResponseEntity<?> findById(@RequestParam(value = "id") Long id) throws JsonProcessingException {
        return _findById(id);
    }

    @GetMapping(value = "/findAll")
    public final ResponseEntity<?> findAll(@RequestParam(value = "p", required = false) Integer p,
                                           @RequestParam(value = "s", required = false) Integer s,
                                           @RequestParam(value = "d", required = false) String d,
                                           @RequestParam(value = "prop", required = false) String prop) throws JsonProcessingException {
        return _findAll(p, s, d, prop);
    }

    @PostMapping(value = "/findAllById")
    public final ResponseEntity<?> findAllById(@RequestBody List<Long> ids) throws JsonProcessingException {
        return _findAllById(ids);
    }

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

    @GetMapping(value = "/deleteById")
    public final ResponseEntity<?> deleteById(@RequestParam(value = "id") Long id) {
        return _deleteById(id);
    }

    @PostMapping(value = "/deleteAll")
    public final ResponseEntity<?> deleteAll(@RequestBody List<Long> ids, @RequestParam(value = "f", required = false) boolean f) throws JsonProcessingException {
        return _deleteAll(ids, f);
    }

    @GetMapping(value = "/deleteAll")
    public final ResponseEntity<?> deleteAll(@RequestParam(value = "f", required = false) boolean f) {
        return _deleteAll(f);
    }

    @GetMapping(value = "/flush")
    public final ResponseEntity<?> flush() {
        return _flush();
    }

    protected ResponseEntity<?> _count() {
        getLogger().debug("Begin count");
        long result = getService().count();
        getLogger().debug("Result: {}", result);
        return ResponseDto.build().withHttpStatus(HttpStatus.OK).withData(result).toResponseEntity();
    }

    protected ResponseEntity<?> _save(CREATE_DTO create_dto, boolean f) throws JsonProcessingException {
        getLogger().debug("Begin saving");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(create_dto));
        getLogger().debug("Param: f={}", f);
        SIMPLE_DTO result = f ? getService().saveAndFlush(create_dto) : getService().save(create_dto);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseDto.build().withHttpStatus(HttpStatus.CREATED).withData(result).toResponseEntity();
    }

    protected ResponseEntity<?> _saveAll(List<CREATE_DTO> dtos) throws JsonProcessingException {
        getLogger().debug("Begin saving");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(dtos));
        List<SIMPLE_DTO> result = getService().saveAll(dtos);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseDto.build().withHttpStatus(HttpStatus.CREATED).withData(result).toResponseEntity();
    }

    protected ResponseEntity<?> _update(CREATE_DTO create_dto, boolean f) throws JsonProcessingException {
        getLogger().debug("Begin updating");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(create_dto));
        getLogger().debug("Param: f={}", f);
        SIMPLE_DTO result = f ? getService().updateAndFlush(create_dto) : getService().update(create_dto);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseDto.build().withHttpStatus(HttpStatus.CREATED).withData(result).toResponseEntity();
    }

    protected ResponseEntity<?> _updateAll(List<CREATE_DTO> dtos) throws JsonProcessingException {
        getLogger().debug("Begin updating");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(dtos));
        List<SIMPLE_DTO> result = getService().updateAll(dtos);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseDto.build().withHttpStatus(HttpStatus.CREATED).withData(result).toResponseEntity();
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
        return ResponseDto.build().withHttpStatus(HttpStatus.OK).withData(result).toResponseEntity();
    }

    protected ResponseEntity<?> _findAllById(List<Long> ids) throws JsonProcessingException {
        getLogger().debug("Begin getting all");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(ids));
        List<SIMPLE_DTO> result = getService().findAllById(ids);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseDto.build().withHttpStatus(HttpStatus.OK).withData(result).toResponseEntity();
    }

    protected ResponseEntity<?> _flush() {
        getLogger().debug("Begin flushing");
        getService().flush();
        return ResponseDto.build().withHttpStatus(HttpStatus.OK).toResponseEntity();
    }

    protected ResponseEntity<?> _deleteById(Long id) {
        getLogger().debug("Begin deleting");
        getLogger().debug("Param id={}", id);
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> _deleteAll(List<Long> ids, boolean f) throws JsonProcessingException {
        getLogger().debug("Begin deleting");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(ids));
        getLogger().debug("Param f={}", f);
        if (f) {
            getService().deleteInBatch(ids);
        } else {
            getService().deleteAll(ids);
        }
        return ResponseDto.build().withHttpStatus(HttpStatus.NO_CONTENT).toResponseEntity();
    }

    protected ResponseEntity<?> _deleteAll(boolean f) {
        getLogger().debug("Begin deleting all");
        getLogger().debug("Param f={}", f);
        if (f) {
            getService().deleteAllInBatch();
        } else {
            getService().deleteAll();
        }
        return ResponseDto.build().withHttpStatus(HttpStatus.NO_CONTENT).toResponseEntity();
    }

    protected ResponseEntity<?> _getOne(Long id) throws JsonProcessingException {
        getLogger().debug("Begin getting one");
        getLogger().debug("Param id={}", id);
        DETAIL_DTO result = getService().getOne(id);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseDto.build().withHttpStatus(HttpStatus.OK).withData(result).toResponseEntity();
    }

    protected ResponseEntity<?> _findById(Long id) throws JsonProcessingException {
        getLogger().debug("Begin finding by id");
        getLogger().debug("Param id={}", id);
        Optional<DETAIL_DTO> temp = getService().findById(id);
        Object result = temp.isPresent() ? temp.get() : temp;
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseDto.build().withHttpStatus(HttpStatus.OK).withData(result).toResponseEntity();
    }

    protected ResponseEntity<?> _existsById(Long id) {
        getLogger().debug("Begin checking existed one");
        getLogger().debug("Param id={}", id);
        boolean result = getService().existsById(id);
        getLogger().debug("Result: {}", result);
        return ResponseDto.build().withHttpStatus(HttpStatus.OK).withData(result).toResponseEntity();
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

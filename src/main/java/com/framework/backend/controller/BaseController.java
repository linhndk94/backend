package com.framework.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.backend.dto.ExampleDto;
import com.framework.backend.entities.BaseEntity;
import com.framework.backend.service.core.BaseService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
public abstract class BaseController<T extends BaseEntity> {

    @Autowired
    ObjectMapper objectMapper;

    protected abstract BaseService<T> getService();

    protected abstract Logger getLogger();

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public final ResponseEntity<?> count() throws JsonProcessingException {
        return _count(null);
    }

    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public final ResponseEntity<?> count(@RequestBody ExampleDto<T> exampleDto) throws JsonProcessingException {
        return _count(exampleDto);
    }

//    long count(Specification<T> spec);

    @RequestMapping(value = "/existsById", method = RequestMethod.GET)
    public final ResponseEntity<?> existsById(@RequestParam(value = "id") Integer id) {
        return _existsById(id);
    }

//    <S extends T> boolean exists(Example<S> example);

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    public final ResponseEntity<?> getOne(@RequestParam(value = "id") Integer id) throws JsonProcessingException {
        return _getOne(id);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public final ResponseEntity<?> findById(@RequestParam(value = "id") Integer id) throws JsonProcessingException {
        return _findById(id);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.POST)
    public final ResponseEntity<?> findOne(@RequestBody ExampleDto<T> exampleDto) throws JsonProcessingException {
        return _findOne(exampleDto);
    }

    //    Optional<T> findOne(Specification<T> spec);

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public final ResponseEntity<?> findAll(@RequestParam(value = "p", required = false) Integer p,
                                           @RequestParam(value = "s", required = false) Integer s,
                                           @RequestParam(value = "d", required = false) String d,
                                           @RequestParam(value = "prop", required = false) String prop) throws JsonProcessingException {
        return _findAll(p, s, d, prop);
    }

    @RequestMapping(value = "/findAllById", method = RequestMethod.POST)
    public final ResponseEntity<?> findAllById(@RequestBody List<Integer> ids) throws JsonProcessingException {
        return _findAllById(ids);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public final ResponseEntity<?> save(@RequestBody T t, @RequestParam(value = "f", required = false) boolean f) throws JsonProcessingException {
        return _save(t, f);
    }

    @RequestMapping(value = "/saveAll", method = RequestMethod.POST)
    public final ResponseEntity<?> saveAll(@RequestBody List<T> entities) throws JsonProcessingException {
        return _saveAll(entities);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public final ResponseEntity<?> update(@RequestBody T t, @RequestParam(value = "f", required = false) boolean f) throws JsonProcessingException {
        return _update(t, f);
    }

    @RequestMapping(value = "/updateAll", method = RequestMethod.POST)
    public final ResponseEntity<?> updateAll(@RequestBody List<T> entities) throws JsonProcessingException {
        return _updateAll(entities);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public final ResponseEntity<?> deleteById(@RequestParam(value = "id") Integer id) {
        return _deleteById(id);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public final ResponseEntity<?> delete(@RequestBody T t) throws JsonProcessingException {
        return _delete(t);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
    public final ResponseEntity<?> deleteAll(@RequestBody List<T> entities, @RequestParam(value = "f", required = false) boolean f) throws JsonProcessingException {
        return _deleteAll(entities, f);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.GET)
    public final ResponseEntity<?> deleteAll(@RequestParam(value = "f", required = false) boolean f) {
        return _deleteAll(f);
    }

    @RequestMapping(value = "/flush", method = RequestMethod.GET)
    public final ResponseEntity<?> flush() {
        return _flush();
    }

    protected ResponseEntity<?> _count(ExampleDto<T> exampleDto) throws JsonProcessingException {
        getLogger().debug("Begin count");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(exampleDto));
        long result;
        if (exampleDto != null) {
            result = getService().count(exampleDto.toExample());
        } else {
            result = getService().count();
        }
        getLogger().debug("Result: {}", result);
        return ResponseEntity.ok(result);
    }

    protected ResponseEntity<?> _save(T t, boolean f) throws JsonProcessingException {
        getLogger().debug("Begin saving");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(t));
        getLogger().debug("Param: f={}", f);
        if (f) {
            t = getService().saveAndFlush(t);
        } else {
            t = getService().save(t);
        }
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(t));
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    protected ResponseEntity<?> _saveAll(List<T> entities) throws JsonProcessingException {
        getLogger().debug("Begin saving");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(entities));
        entities = getService().saveAll(entities);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(entities));
        return new ResponseEntity<>(entities, HttpStatus.CREATED);
    }

    protected ResponseEntity<?> _update(T t, boolean f) throws JsonProcessingException {
        getLogger().debug("Begin updating");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(t));
        getLogger().debug("Param: f={}", f);
        if (f) {
            t = getService().updateAndFlush(t);
        } else {
            t = getService().update(t);
        }
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(t));
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    protected ResponseEntity<?> _updateAll(List<T> entities) throws JsonProcessingException {
        getLogger().debug("Begin updating");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(entities));
        entities = getService().updateAll(entities);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(entities));
        return new ResponseEntity<>(entities, HttpStatus.CREATED);
    }

    protected ResponseEntity<?> _findAll(Integer p, Integer s, String d, String prop) throws JsonProcessingException {
        getLogger().debug("Begin getting all");
        Sort sort = createSort(d, prop);
        PageRequest pageRequest = createPageRequest(p, s, sort);
        Iterable<T> result;
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
        Iterable<T> result;
        if (sort == null && pageRequest == null) {
            result = getService().findAll(exampleDto.toExample());
        } else if (pageRequest != null) {
            result = getService().findAll(exampleDto.toExample(), pageRequest);
        } else {
            result = getService().findAll(exampleDto.toExample(), sort);
        }
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseEntity.ok(result);
    }

    protected ResponseEntity<?> _findAllById(List<Integer> ids) throws JsonProcessingException {
        getLogger().debug("Begin getting all");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(ids));
        List<T> result = getService().findAllById(ids);
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

    protected ResponseEntity<?> _delete(T t) throws JsonProcessingException {
        getLogger().debug("Begin deleting");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(t));
        getService().delete(t);
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> _deleteAll(List<T> entities, boolean f) throws JsonProcessingException {
        getLogger().debug("Begin deleting");
        getLogger().debug("Payload: {}", objectMapper.writeValueAsString(entities));
        getLogger().debug("Param f={}", f);
        if (f) {
            getService().deleteInBatch(entities);
        } else {
            getService().deleteAll(entities);
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
        T result = getService().getOne(id);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseEntity.ok(result);
    }

    protected ResponseEntity<?> _findById(Integer id) throws JsonProcessingException {
        getLogger().debug("Begin finding by id");
        getLogger().debug("Param id={}", id);
        Optional<T> result = getService().findById(id);
        getLogger().debug("Result: {}", objectMapper.writeValueAsString(result));
        return ResponseEntity.ok(result);
    }

    protected ResponseEntity<?> _findOne(ExampleDto<T> exampleDto) throws JsonProcessingException {
        getLogger().debug("Begin finding one");
        Optional<T> temp = getService().findOne(exampleDto.toExample());
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

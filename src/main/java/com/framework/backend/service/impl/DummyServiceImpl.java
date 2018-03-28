package com.framework.backend.service.impl;

import com.framework.backend.dto.create_dto.DummyCreateDto;
import com.framework.backend.dto.detail_dto.DummyDetailDto;
import com.framework.backend.dto.simple_dto.DummySimpleDto;
import com.framework.backend.entities.Dummy;
import com.framework.backend.exception.BusinessException;
import com.framework.backend.repository.core.BaseRepository;
import com.framework.backend.repository.core.DummyRepository;
import com.framework.backend.service.core.DummyService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Log4j2
@Service
public class DummyServiceImpl extends BaseServiceImpl<Dummy, DummySimpleDto, DummyDetailDto, DummyCreateDto> implements DummyService {

    @Autowired
    private DummyRepository dummyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected BaseRepository<Dummy> getBaseRepository() {
        return dummyRepository;
    }

    @Override
    protected Dummy createEntity(DummyCreateDto dummyCreateDto) {
        Dummy dummy = new Dummy();
        copyPojo(dummyCreateDto, dummy);
        return dummy;
    }

    @Override
    protected Dummy createEntity(DummySimpleDto dummySimpleDto) {
        Dummy dummy = new Dummy();
        copyPojo(dummySimpleDto, dummy);
        return dummy;
    }

    @Override
    protected DummySimpleDto createSimpleDto(Dummy entity) {
        DummySimpleDto dummySimpleDto = new DummySimpleDto();
        copyPojo(entity, dummySimpleDto);
        return dummySimpleDto;
    }

    @Override
    protected DummyDetailDto createDetailDto(Dummy entity) {
        DummyDetailDto dummyDetailDto = new DummyDetailDto();
        copyPojo(entity, dummyDetailDto);
        return dummyDetailDto;
    }

    @Override
    protected void beforeCreate(DummyCreateDto dummyCreateDto) {
        if (checkDuplicateUsername(dummyCreateDto.getUsername()))
            throw BusinessException.invalidParams(String.format("Username: %s is already existed", dummyCreateDto.getUsername()));
        dummyCreateDto.setPassword(passwordEncoder.encode(dummyCreateDto.getPassword()));
        super.beforeCreate(dummyCreateDto);
    }

    @Override
    protected void beforeCreate(Iterable<DummyCreateDto> dummyCreateDtos) {
        List<String> errors = checkDuplicateUsername(StreamSupport.stream(dummyCreateDtos.spliterator(), false).map(DummyCreateDto::getUsername).collect(Collectors.toList()));
        if (errors.size() > 0) {
            throw BusinessException.invalidParams("One or more usernames are already existed", errors);
        }
        dummyCreateDtos.forEach(dummyCreateDto -> dummyCreateDto.setPassword(passwordEncoder.encode(dummyCreateDto.getPassword())));
        super.beforeCreate(dummyCreateDtos);
    }

    @Override
    protected void beforeUpdate(DummyCreateDto dummyCreateDto) {
        super.beforeUpdate(dummyCreateDto);
    }

    @Override
    protected void beforeUpdate(Iterable<DummyCreateDto> dummyCreateDtos) {
        super.beforeUpdate(dummyCreateDtos);
    }

    private boolean checkDuplicateUsername(String username) {
        Dummy dummy = new Dummy();
        dummy.setUsername(username);
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase("username").withMatcher("username", exact());
        return dummyRepository.exists(Example.of(dummy, exampleMatcher));
    }

    private List<String> checkDuplicateUsername(List<String> usernames) {
        return usernames.stream().filter(this::checkDuplicateUsername).collect(Collectors.toList());
    }
}

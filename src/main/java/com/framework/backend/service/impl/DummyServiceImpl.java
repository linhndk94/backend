package com.framework.backend.service.impl;

import com.framework.backend.dto.create_dto.DummyCreateDto;
import com.framework.backend.dto.detail_dto.DummyDetailDto;
import com.framework.backend.dto.simple_dto.DummySimpleDto;
import com.framework.backend.entities.Dummy;
import com.framework.backend.repository.core.DummyRepository;
import com.framework.backend.service.core.DummyService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class DummyServiceImpl extends BaseServiceImpl<Dummy, DummySimpleDto, DummyDetailDto, DummyCreateDto> implements DummyService {

    @Autowired
    public DummyServiceImpl(DummyRepository dummyRepository) {
        super(dummyRepository);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected Dummy createEntity(DummyCreateDto dummyCreateDto) {
        Dummy dummy = new Dummy();
        copyPojo(dummyCreateDto, dummy);
        return dummy;
    }

    @Override
    protected Dummy createEntity(Long id) {
        Dummy dummy = new Dummy();
        dummy.setId(id);
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
}

package com.framework.backend.controller;

import com.framework.backend.dto.create_dto.DummyCreateDto;
import com.framework.backend.dto.detail_dto.DummyDetailDto;
import com.framework.backend.dto.simple_dto.DummySimpleDto;
import com.framework.backend.entities.Dummy;
import com.framework.backend.service.core.BaseService;
import com.framework.backend.service.core.DummyService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
@Log4j2
public class DummyController extends BaseController<Dummy, DummySimpleDto, DummyDetailDto, DummyCreateDto> {

    @Autowired
    DummyService dummyService;

    @Override
    protected BaseService<Dummy, DummySimpleDto, DummyDetailDto, DummyCreateDto> getService() {
        return dummyService;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}

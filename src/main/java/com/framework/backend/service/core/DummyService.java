package com.framework.backend.service.core;

import com.framework.backend.dto.create_dto.DummyCreateDto;
import com.framework.backend.dto.detail_dto.DummyDetailDto;
import com.framework.backend.dto.simple_dto.DummySimpleDto;
import com.framework.backend.entities.Dummy;

public interface DummyService extends BaseService<Dummy, DummySimpleDto, DummyDetailDto, DummyCreateDto> {
}

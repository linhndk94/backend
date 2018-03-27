package com.framework.backend.dto.detail_dto;

import com.framework.backend.dto.simple_dto.DummySimpleDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DummyDetailDto extends DummySimpleDto {
    private String username;
    private String password;
}
